package jp.kawaguchi_shingo.libs.escpos.command.scancodegen;

import java.nio.charset.StandardCharsets;

import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;

public class _Qrcode extends Abstract2DCodeGen {
	// fn=165 用のパラメータ定数（QRコード: モデルの選択）
	public static final int QR_MODEL_1 = 49;
	public static final int QR_MODEL_2 = 50;
	public static final int QR_MICRO_QR = 52;

	// fn=167 用のパラメータ定数（QRコード: セルサイズの選択）
	public static final int QR_MODULE_SIZE_MIN = 1;
	public static final int QR_MODULE_SIZE_MAX = 16;

	// fn=169 用のパラメータ定数（QRコード: エラー訂正レベルの選択）
	public static final int QR_EC_LEVEL_L = 48; // 約7%
	public static final int QR_EC_LEVEL_M = 49; // 約15%
	public static final int QR_EC_LEVEL_Q = 50; // 約25%
	public static final int QR_EC_LEVEL_H = 51; // 約30%

	/**
	 * QR Code: モデルの選択
	 * 
	 * @param n モデル選択 (49, 50, 52)
	 * @return
	 */
	public byte[] _GS_parenthesis_K_fn165(int n) throws InvalidValueException {
		if (n != QR_MODEL_1 && n != QR_MODEL_2 && n != QR_MICRO_QR) {
			throw new InvalidValueException();
		}
		return baseCommand(49, 65, new byte[] { (byte) n, 0x00 });
	}

	/**
	 * QR Code: モジュールのサイズの設定
	 * 
	 * @param n モジュールサイズ (1 ~ 16)
	 * @return
	 */
	public byte[] _GS_parenthesis_K_fn167(int n) throws InvalidValueException {
		if (n < QR_MODEL_1 || n > QR_MODULE_SIZE_MAX) {
			throw new InvalidValueException();
		}
		return baseCommand(49, 67, new byte[] { (byte) n, (byte) 0x00 });
	}

	/**
	 * GS ( k <fn=169>
	 * QRコード: モデルの選択
	 * 
	 * @param n エラー訂正レベル (48 〜 51)
	 * @param n モデル選択 (49, 50, 52)
	 * @return
	 */
	public byte[] _GS_parenthesis_K_fn169(int n) throws InvalidValueException {
		if (n != QR_EC_LEVEL_L && n != QR_EC_LEVEL_M && n != QR_EC_LEVEL_Q && n != QR_EC_LEVEL_H) {
			throw new InvalidValueException();
		}
		return baseCommand(49, 69, new byte[] { (byte) n, (byte) 0x00 });
	}

	public static final int QR_SYMBOL_STORAGE_MIN = 0;
	public static final int QR_SYMBOL_STORAGE_MAX = 255;

	/**
	 * QR Code: シンボル保存領域へのデータの格納
	 * 
	 * @param data 印刷するデータ
	 * @return
	 */
	public byte[] _GS_parenthesis_K_fn180(String data) throws InvalidValueException {
		/*
		 * if (n < QR_SYMBOL_STORAGE_MIN || n > QR_SYMBOL_STORAGE_MAX) {
		 * throw new InvalidValueException();
		 * }
		 */
		byte[] dataByte = data.getBytes(StandardCharsets.UTF_8);
		int dataLen = dataByte.length;
		byte[] payload = new byte[1 + dataLen];
		payload[0] = (byte) 0x30; // cn (通常 48)
		System.arraycopy(data, 0, payload, 2, dataLen);

		return baseCommand(49, 80, payload);
	}

	/**
	 * QR Code: シンボル保存領域のシンボルデータの印字
	 * 
	 * @return
	 */
	public byte[] _GS_parenthesis_k_fn181() {
		return baseCommand(49, 81, new byte[] { (byte) 48 });
	}

	/**
	 * QR Code: シンボル保存領域のシンボルデータのサイズ情報の送信
	 * 
	 * @return
	 */
	public byte[] _GS_parenthesis_k_fn182() {
		return baseCommand(49, 82, new byte[] { (byte) 48 });
	}
}
