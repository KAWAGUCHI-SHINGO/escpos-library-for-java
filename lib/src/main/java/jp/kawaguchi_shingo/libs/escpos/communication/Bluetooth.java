package jp.kawaguchi_shingo.libs.escpos.communication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.UUID;

public class Bluetooth extends AbstractCommunication {
	public static final String TAG = "Bluetooth";

	// SPP (Serial Port Profile) の標準UUID
	private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	protected byte[] address = null;
	protected BluetoothSocket socket = null;
	private final Context context;

	public Bluetooth(Context context, byte[] address) {
		this.context = context;
		this.address = address;
	}

	/**
	 * byte[] 型のRAWアドレスを、Android APIが認識できるMACアドレス文字列に変換します
	 * 例: {0x00, 0x11, 0x22, 0x33, 0x44, 0x55} -> "00:11:22:33:44:55"
	 */
	public String getMacAddressString() {
		if (this.address == null || this.address.length != 6) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.address.length; i++) {
			sb.append(String.format("%02X", this.address[i]));
			if (i < this.address.length - 1) {
				sb.append(":");
			}
		}
		return sb.toString();
	}

	public BluetoothAdapter getAdapter(Context context) {
		BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
		if (bluetoothManager != null) {
			return bluetoothManager.getAdapter();
		}
		return null;
	}

	@Override
	public void connect() throws IOException {
		if (this.address == null) {
			throw new IOException("Bluetoothアドレスが設定されていません。");
		}
		this.connect(this.address);
	}

	/**
	 * 指定されたアドレスに対して非同期で接続を確立します。
	 */
	public void connect(byte[] address) throws IOException {
		this.address = address;

		BluetoothAdapter bluetoothAdapter = getAdapter(this.context);
		if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
			throw new IOException("Bluetoothアダプターが無効、または取得できません。");
		}

		String macAddress = getMacAddressString();
		BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddress);

		// 接続処理はブロッキングするため、別スレッドで実行
		new Thread(() -> {
			try {
				// RFCOMMソケットの生成
				this.socket = device.createRfcommSocketToServiceRecord(SPP_UUID);

				// 接続実行 (最大12秒程度ブロックされます)
				this.socket.connect();

				// 親クラスの共通ストリーム変数（is, os）にバインド
				is = socket.getInputStream();
				// 出力側はバッファリングを行い書き込み効率を最大化
				os = new BufferedOutputStream(socket.getOutputStream());

				Log.d(TAG, "Bluetoothプリンターに正常に接続しました: " + macAddress);

				// 【最重要】親クラスの共通受信監視ループおよび送信スレッドプールを起動
				// これにより LAN と全く同じストリーム処理として稼働します
				super.startListen();

			} catch (IOException e) {
				Log.e(TAG, "Bluetooth接続エラー: " + e.getMessage());
				close();
			}
		}, "BluetoothConnectThread").start();
	}

	@Override
	public synchronized void close() {
		// 先に親クラスの受信ループ終了、Executorサービス停止を安全に行う
		super.close();

		if (socket != null) {
			try {
				socket.close();
				Log.d(TAG, "Bluetoothソケットを閉じました。");
			} catch (IOException e) {
				Log.e(TAG, "Bluetoothソケットクローズ中にエラー: " + e.getMessage());
			} finally {
				socket = null;
			}
		}
	}
}
