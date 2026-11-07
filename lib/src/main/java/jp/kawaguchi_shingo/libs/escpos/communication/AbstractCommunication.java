package jp.kawaguchi_shingo.libs.escpos.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractCommunication {

	public static final String TAG = "AbstractCommunication";

	// 揮発性変数（複数スレッドからの可視性を保証）
	protected volatile boolean isRunning = false;

	// 下位クラスに提供する基本ストリーム（バッファリングは下位クラスの接続時に行うか、ここで一本化）
	protected InputStream is = null;
	protected OutputStream os = null;

	// 送信用スレッドプール（順序を保証するため単一スレッドのExecutor）
	private ExecutorService writeExecutor;

	// リクエスト応答待ち同期用の排他ロックオブジェクト
	private final Object responseLock = new Object();
	private CountDownLatch responseLatch;
	protected byte[] lastResponseData;

	// サブクラスで必ず実装する接続メソッド
	public abstract void connect() throws IOException;

	/**
	 * 接続完了後に、受信・送信用の環境を初期化してリスナーを開始する
	 */
	protected void startListen() {
		isRunning = true;
		writeExecutor = Executors.newSingleThreadExecutor(); // 送信順序を保証

		// 受信スレッドの開始
		Thread listenThread = new Thread(() -> {
			try {
				listen();
			} catch (ConnectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}, "PrinterListenThread");
		listenThread.start();
	}

	protected void listen() throws ConnectException {
		byte[] buffer = new byte[1024];
		int bytesRead;

		try {
			// is.read はデータが届くまでブロックします
			while (isRunning && (is != null) && (bytesRead = is.read(buffer)) != -1) {

				// 【重要】ESC/POSのデータ混線を防ぐため、まずは届いたデータをパースする
				// パース結果が「要求したステータスの応答」である場合のみ、Latchを解除する
				incomming(buffer, bytesRead);
			}
		} catch (IOException e) {
			if (isRunning) {
				System.err.println("受信エラーまたは切断されました: " + e.getMessage());
				close();
			}
		}
	}

	/**
	 * 受信したデータを解析し、自動通知（ASB）かリクエストへの応答かを判別する
	 */
	protected void incomming(byte[] buffer, int bytesRead) {
		byte[] data = new byte[bytesRead];
		System.arraycopy(buffer, 0, data, 0, bytesRead);

		// デバッグ出力
		parseListenData(data, bytesRead);

		synchronized (responseLock) {
			// もしrequestメソッドが待機中、かつ受信データが「ASB（自動通知）ではなく、リクエスト応答」だと判定できた場合
			// ※簡易的に「待機中なら何でも入れる」とする場合でも、NPEを防ぐため同期ブロック内で処理
			if (responseLatch != null) {
				// TODO: 本来はここで data がASB形式（1バイト目が0xx1xx00など）か、
				// 返却ステータス（1バイト目が0xx0xx10など）かを仕様書に基づいてチェックするのが安全です。

				lastResponseData = data;
				responseLatch.countDown(); // ロック解除
			}
		}
	}

	/**
	 * 同期的にステータスを要求し、応答を待ちます（スレッドセーフ）
	 */
	public byte[] request(byte[] command, long timeoutMillis) {
		// ロックオブジェクトでメソッド全体を保護し、複数スレッドからの同時 request を防ぐ
		synchronized (responseLock) {
			if (os == null || !isRunning) {
				System.err.println("プリンターに接続されていません。");
				return null;
			}

			// 応答待ちの準備（1回のカウントでロック解除）
			responseLatch = new CountDownLatch(1);
			lastResponseData = null;

			try {
				// リクエストコマンドを送信（即時性を高めるため、同期送信またはflush）
				os.write(command);
				os.flush();
				System.out.println("ステータス要求コマンドを送信しました。");

				// 受信スレッドがデータをキャッチするまで待機
				boolean success = responseLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);

				if (!success) {
					System.err.println("[警告] プリンターからの応答がタイムアウトしました。");
				}

				return lastResponseData;

			} catch (IOException | InterruptedException e) {
				System.err.println("リクエスト処理中にエラー: " + e.getMessage());
				Thread.currentThread().interrupt(); // 割り込みステータスの復旧
				return null;
			} finally {
				responseLatch = null; // 次回のために確実にリセット
			}
		}
	}

	/**
	 * 印刷データ等の送信（シングルスレッドプールで順序を完全保証）
	 */
	public void write(final byte[] data) {
		if (!isRunning || writeExecutor == null || writeExecutor.isShutdown()) {
			System.err.println("送信できません。接続が閉じているか初期化されていません。");
			return;
		}

		writeExecutor.submit(() -> {
			try {
				physicalWrite(data);
			} catch (IOException e) {
				System.err.println("データ送信中にエラーが発生しました: " + e.getMessage());
				close();
			}
		});
	}

	/**
	 * 物理的な送信処理（ストリーム用のデフォルト実装）
	 * USBなどの独自転送を行う場合は下位クラスでオーバーライドします。
	 */
	protected void physicalWrite(byte[] data) throws IOException {
		if (os != null) {
			os.write(data);
			os.flush();
		} else {
			throw new IOException("出力ストリームが未初期化です。");
		}
	}

	/**
	 * 通信リソースの解放
	 */
	public synchronized void close() {
		if (!isRunning)
			return;

		isRunning = false;

		// 送信タスクの停止
		if (writeExecutor != null) {
			writeExecutor.shutdownNow();
		}

		// ストリームのクローズ（一方が閉じれば他方も閉じるため、基本的なものを確実に行う）
		try {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		} catch (IOException e) {
			System.err.println("ストリームのクローズ中にエラー: " + e.getMessage());
		} finally {
			is = null;
			os = null;
			synchronized (responseLock) {
				if (responseLatch != null) {
					responseLatch.countDown(); // 待機中のスレッドがいれば強制解除
				}
			}
		}
	}

	protected void parseListenData(byte[] data, int length) {
		// （既存のデバッグ・パース処理をそのまま維持）
		// デバッグ用：届いたバイトを16進数で表示
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(String.format("0x%02X ", data[i]));
		}
		System.out.println("プリンターからデータ受信 (" + length + " bytes): " + sb.toString());

		// リアルタイムステータスのビット判定（以下略）
		// ESC/POSのリアルタイムステータス（ASB等）の1バイト目の判定例
		if (length > 0) {
			byte firstByte = data[0];

			// 1バイト目の特定のビットをチェックして状態を判定（仕様書に基づく標準的なマスク例）
			boolean isCoverOpen = (firstByte & 0x20) != 0; // ビット5: カバー開
			boolean isPaperFeed = (firstByte & 0x40) != 0; // ビット6: フィードスイッチ押下中

			if (isCoverOpen) {
				System.out.println("[警告] プリンターのカバーが開いています！");
			}

			// 2バイト目以降がある場合（用紙切れ等の詳細ステータス）
			if (length >= 2) {
				byte secondByte = data[1];
				boolean isPaperNearEnd = (secondByte & 0x03) != 0; // ビット0-1: 用紙少なめ
				boolean isPaperEnd = (secondByte & 0x0C) != 0; // ビット2-3: 用紙なし

				if (isPaperEnd) {
					System.out.println("[エラー] 用紙がなくなりました！印刷を中断してください。");
				} else if (isPaperNearEnd) {
					System.out.println("[通知] 用紙が残りわずかです。");
				}
			}
		}
	}
}
