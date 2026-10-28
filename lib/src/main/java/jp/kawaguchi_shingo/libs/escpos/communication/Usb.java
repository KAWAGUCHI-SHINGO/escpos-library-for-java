package jp.kawaguchi_shingo.libs.escpos.communication;

import java.io.IOException;
import java.net.ConnectException;

import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;

/**
 * Usb
 * 
 * 必要な権限 (AndroidManifest.xml):
 * <uses-feature android:name="android.hardware.usb.host" />
 * ※実行時に usbManager.requestPermission(device, permissionIntent);
 * を呼び出してユーザーから許可を得る必要があります。
 */
public class Usb extends AbstractCommunication {
	public static final String TAG = "Usb";

	private Context context = null;

	private UsbDevice device = null;
	private UsbDeviceConnection connection;
	private UsbInterface usbInterface;
	private UsbEndpoint endpointOut;
	private UsbEndpoint endpointIn;

	public Usb(Context context, UsbDevice device) {
		this.context = context;
		this.device = device;
	}

	@Override
	public void connect() throws IOException {
		if (this.context == null || this.device == null) {
			throw new IOException("Context または UsbDevice が null のため接続できません。");
		}

		UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
		if (usbManager == null) {
			throw new IOException("UsbManager の取得に失敗しました。");
		}

		// 通常、プリンタークラスのデバイスはインターフェース0にエンドポイントを持ちます
		this.usbInterface = device.getInterface(0);

		// バルク転送用のIN/OUTエンドポイントを検索
		for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
			UsbEndpoint ep = usbInterface.getEndpoint(i);
			if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
				if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
					endpointOut = ep;
				} else if (ep.getDirection() == UsbConstants.USB_DIR_IN) {
					endpointIn = ep;
				}
			}
		}

		if (endpointOut == null) {
			throw new IOException("必要なバルク出力（OUT）エンドポイントが見つかりません。");
		}

		// 接続処理および初期化を非同期スレッドで実行
		new Thread(() -> {
			try {
				// デバイスのオープン (呼び出し元で事前に要パーミッション取得)
				connection = usbManager.openDevice(device);
				if (connection == null) {
					throw new IOException("USBデバイスのオープンに失敗しました。(許可がない、または引き抜かれた可能性があります)");
				}

				// インターフェースを排他的にクレイム（確保）
				if (!connection.claimInterface(usbInterface, true)) {
					throw new IOException("USBインターフェースの確保(claimInterface)に失敗しました。");
				}

				Log.d(TAG, "USBプリンターとの接続、およびインターフェース確保に成功しました。");

				// 【重要】親クラスの共通インフラ（受信スレッド、送信Executor）を起動
				super.startListen();

			} catch (IOException e) {
				Log.e(TAG, "USBオープンエラー: " + e.getMessage());
				close();
			}
		}, "UsbConnectThread").start();
	}

	/*
	 * public void connect(Context context, UsbDevice device) {
	 * UsbManager usbManager = (UsbManager)
	 * context.getSystemService(Context.USB_SERVICE);
	 * if (usbManager == null || device == null)
	 * return;
	 * 
	 * // インターフェースの取得 (通常はインデックス0)
	 * UsbInterface usbInterface = device.getInterface(0);
	 * 
	 * // 送信用（OUT）のエンドポイントを探索
	 * for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
	 * UsbEndpoint ep = usbInterface.getEndpoint(i);
	 * if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK &&
	 * ep.getDirection() == UsbConstants.USB_DIR_OUT) {
	 * endpointOut = ep;
	 * break;
	 * }
	 * }
	 * new Thread(() -> {
	 * // デバイスを開く (事前にパーミッション取得が必要)
	 * connection = usbManager.openDevice(device);
	 * if (connection != null && endpointOut != null) {
	 * // インターフェースを排他的に確保
	 * connection.claimInterface(usbInterface, true);
	 * 
	 * // データの送信例 (Bulk転送)
	 * byte[] buffer = "Hello USB".getBytes();
	 * int timeout = 1000;
	 * connection.bulkTransfer(endpointOut, buffer, buffer.length, timeout);
	 * 
	 * System.out.println("プリンターに接続しました: " + device);
	 * 
	 * isRunning = true;
	 * super.startListen();
	 * }
	 * }).start();
	 * }
	 */
	@Override
	public void listen() throws ConnectException {
		if (connection == null || endpointIn == null) {
			throw new ConnectException();
		}
		byte[] buffer = new byte[endpointIn.getMaxPacketSize()];
		int timeout = 1000; // 1秒ごとにポーリング（isRunningフラグのチェックを可能にするため）

		while (isRunning) {
			// プリンターからの応答データを取得（タイムアウト付きブロック）
			int length = connection.bulkTransfer(endpointIn, buffer, buffer.length, timeout);

			if (length > 0) {
				// 取得できた生バイナリをそのまま親クラスの解析ロジック（incomming）へ受け渡す
				super.incomming(buffer, length);
			} else if (length < 0 && !isRunning) {
				// 切断指示が出ている場合のバルクエラーは無視してループを抜ける
				break;
			}
		}
	}

	public void listen2() throws ConnectException {
		if (connection == null || endpointIn == null) {
			throw new ConnectException();
		}
		new Thread(() -> {
			// エンドポイントの最大パケットサイズをバッファサイズにする
			byte[] buffer = new byte[endpointIn.getMaxPacketSize()];
			int timeout = 1000; // 1秒ごとにタイムアウトしてループを継続（ブロック回避）

			while (isRunning) {
				// データを受信するまで待機（タイムアウトあり）
				int length = connection.bulkTransfer(endpointIn, buffer, buffer.length, timeout);

				if (length > 0) {
					byte[] receivedData = new byte[length];
					System.arraycopy(buffer, 0, receivedData, 0, length);

					// TODO: 受信データの処理（UIへの通知など）
					Log.d(TAG, "受信成功: " + new String(receivedData) + " (" + length + " bytes)");
					lastResponseData = receivedData;
				}
			}
		}).start();
	}

	/**
	 * 【親クラスの物理送信をUSB用に差し替え】
	 * 親クラスのSingleThreadExecutorから順序が100%保証された状態で呼び出されます。
	 */
	@Override
	protected void physicalWrite(byte[] data) throws IOException {
		if (connection == null || endpointOut == null) {
			throw new IOException("USBが切断されているか、初期化されていません。");
		}

		int timeout = 2000; // 送信タイムアウト（2秒）
		int result = connection.bulkTransfer(endpointOut, data, data.length, timeout);

		if (result < 0) {
			throw new IOException("USBバルク転送（送信）に失敗しました。エラーコード: " + result);
		}

		Log.d(TAG, "USB物理送信成功: " + result + " bytes");
	}

	public synchronized void close() {
		// 先に親クラスの監視ループ終了、Executorサービス停止を安全に行う
		super.close();

		if (connection != null) {
			try {
				if (usbInterface != null) {
					connection.releaseInterface(usbInterface);
				}
				connection.close();
				Log.d(TAG, "USB接続およびリソースを完全に解放しました。");
			} catch (Exception e) {
				Log.e(TAG, "USBリソース解放中にエラーが発生しました: " + e.getMessage());
			} finally {
				connection = null;
				usbInterface = null;
				endpointOut = null;
				endpointIn = null;
			}
		}
	}
}
