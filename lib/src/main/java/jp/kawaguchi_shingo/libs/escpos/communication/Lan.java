package jp.kawaguchi_shingo.libs.escpos.communication;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Lan extends AbstractCommunication {

	public static final String TAG = "Lan";

	protected InetSocketAddress address = null;
	protected Socket socket;

	public Lan(String address) {
		this(address, 9100);
	}

	public Lan(String address, int port) {
		this.address = new InetSocketAddress(address, port);
	}

	@Override
	public void connect() throws IOException {
		if (this.address == null) {
			throw new IOException("接続先アドレスが指定されていません。");
		}
		this.connect(address);
	}

	public void connect(String address) {
		this.connect(address, 9100);
	}

	public void connect(String address, int port) {
		this.connect(new InetSocketAddress(address, port));
	}

	/**
	 * 指定されたアドレスに接続します。
	 * 
	 * @param address 接続先のInetSocketAddress
	 * @return 接続に使用するSocket
	 */
	public void connect(InetSocketAddress address) {
		new Thread(() -> {
			try {
				this.socket = new Socket();
				// 特定のローカルIPアドレスとバインドする
				// this.socket.bind(address);
				// タイムアウト付きで接続 (5秒)
				this.socket.connect(address, 5000);
				// ストリームの初期化
				is = socket.getInputStream();
				os = new BufferedOutputStream(socket.getOutputStream());

				System.out.println("プリンターに接続しました: " + address);

				isRunning = true;
				super.startListen();
			} catch (IOException e) {
				System.err.println("接続に失敗しました: " + e.getMessage());
				close();
			}
		}, "LanConnectThread").start();
	}

	@Override
	public void write(byte[] data) {
		// 送信の実行可否判断はisRunningをフラグに親クラスに委ねる、
		// または物理的なソケット接続をここで最低限チェックする
		if (socket == null || !socket.isConnected() || socket.isClosed()) {
			System.err.println("サーバーに接続されていないため、送信できません。");
			return;
		}
		super.write(data);
	}

	@Override
	public synchronized void close() {
		// 先に親クラスのストリームクローズとExecutorのシャットダウンを行う
		super.close();

		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		socket = null;
	}
}
