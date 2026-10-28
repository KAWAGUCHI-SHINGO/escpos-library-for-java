package jp.kawaguchi_shingo.libs.escpos;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import jp.kawaguchi_shingo.libs.escpos.communication.AbstractCommunication;
import jp.kawaguchi_shingo.libs.escpos.communication.Bluetooth;
import jp.kawaguchi_shingo.libs.escpos.communication.Lan;
import jp.kawaguchi_shingo.libs.escpos.communication.Usb;
import jp.kawaguchi_shingo.libs.escpos.vendor.AbstractVendor;

public class Printer {
	public static final int BLUETOOTH = 0;
	public static final int LAN = 1;
	public static final int USB = 2;

	public static final int VENDOR_EPSON = 0;
	public static final int VENDOR_STAR = 1;
	public static final int VENDOR_CITIZEN = 2;
	public static final int VENDOR_SEIKO = 3;
	public static final int VENDOR_PRIMEX = 4;
	public static final int VENDOR_HIT = 5;
	public static final int VENDOR_WASHIN = 6;

	private final int type;
	protected int vendorType;

	protected AbstractVendor vendor;

	// 各通信具象クラスの親である抽象クラスで一本化して管理します
	private final AbstractCommunication communication;

	// トランザクション（一括送信）用のメモリバッファ
	private ByteArrayOutputStream transactionBuffer = null;

	// --- コンストラクタ群 ---

	public Printer(Usb usb) {
		this.type = USB;
		this.communication = usb;
	}

	public Printer(Context context, UsbDevice device) {
		this.type = USB;
		this.communication = new Usb(context, device);
	}

	public Printer(Bluetooth bluetooth) {
		this.type = BLUETOOTH;
		this.communication = bluetooth;
	}

	public Printer(Context context, byte[] address) {
		this.type = BLUETOOTH;
		this.communication = new Bluetooth(context, address);
	}

	public Printer(Lan lan) {
		this.type = LAN;
		this.communication = lan;
	}

	public Printer(String address, int port) {
		this.type = LAN;
		this.communication = new Lan(address, port);
	}

	public Printer(String address) {
		this.type = LAN;
		this.communication = new Lan(address);
	}

	// --- 通信制御メソッド ---

	/**
	 * プリンターに接続します（非同期で開始されます）
	 * 
	 * @return 接続処理が受け付けられた場合は true
	 */
	public boolean connect() {
		if (communication == null)
			return false;
		try {
			communication.connect();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * プリンターとの接続を閉じ、リソースを解放します
	 */
	public boolean disconnect() {
		if (communication == null)
			return false;
		communication.close();
		synchronized (this) {
			transactionBuffer = null;
		}
		return true;
	}

	// --- データ送信（印刷）メソッド ---

	/**
	 * トランザクション（一括送信モード）を開始します。
	 * 印刷データの組み立てを開始する前に呼び出すことで、途中のブツ切れを防ぎます。
	 */
	public synchronized void beginTransaction() {
		transactionBuffer = new ByteArrayOutputStream();
	}

	/**
	 * 溜めた印刷データをプリンターへ一括送信（フラッシュ）します。
	 */
	public synchronized void commitTransaction() {
		if (communication == null || transactionBuffer == null || transactionBuffer.size() == 0) {
			transactionBuffer = null;
			return;
		}
		// 溜まった全てのデータを送信プールへ投げる
		communication.write(transactionBuffer.toByteArray());
		transactionBuffer = null;
	}

	/**
	 * 印刷データ（コマンドのバイト配列）を送信します。
	 * beginTransaction() 実行中の場合はメモリに蓄積され、そうでない場合は即時順序保証送信されます。
	 */
	public synchronized void write(byte[] data) {
		if (data == null || data.length == 0)
			return;

		// トランザクション中であればメモリバッファに書き込む
		if (transactionBuffer != null) {
			transactionBuffer.write(data, 0, data.length);
		} else {
			// 即時送信
			if (communication != null) {
				communication.write(data);
			}
		}
	}

	/**
	 * 同期的にステータス要求コマンドを送信し、プリンターからの応答を待ちます。
	 * 
	 * @param requestCommand 要求コマンド（例: GS r 1 など）
	 * @param timeoutMillis  応答を待つ最大時間（ミリ秒）
	 * @return プリンターから返ってきたステータスバイト配列（タイムアウト時等は null）
	 */
	public byte[] requestStatus(byte[] requestCommand, long timeoutMillis) {
		if (communication == null)
			return null;
		return communication.request(requestCommand, timeoutMillis);
	}

	/**
	 * 現在の接続種別（BLUETOOTH, LAN, USB）を取得します
	 */
	public int getType() {
		return this.type;
	}

	public AbstractCommunication getCommunication() {
		return this.communication;
	}

	public void setVendor(AbstractVendor vendor) {
		this.vendor = vendor;
	}

}
