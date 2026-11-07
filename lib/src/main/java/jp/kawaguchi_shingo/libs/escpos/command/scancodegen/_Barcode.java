package jp.kawaguchi_shingo.libs.escpos.command.scancodegen;

import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;

public class _Barcode {
	// GS H 用のパラメータ定数（HRI文字の印刷位置の選択）
	public static final int HRI_POSITION_NONE = 0;
	public static final int HRI_POSITION_ABOVE = 1;
	public static final int HRI_POSITION_BELOW = 2;
	public static final int HRI_POSITION_BOTH = 3;
	public static final int HRI_POSITION_NONE2 = 48;
	public static final int HRI_POSITION_ABOVE2 = 49;
	public static final int HRI_POSITION_BELOW2 = 50;
	public static final int HRI_POSITION_BOTH2 = 51;

	/**
	 * HRI文字の印刷位置の選択
	 * 
	 * @param value
	 * @return
	 * @throws InvalidValueException
	 */
	public byte[] _GS_H(int value) throws InvalidValueException {
		if (value < HRI_POSITION_NONE || value > HRI_POSITION_BOTH) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x48, (byte) value };
	}

	// GS f 用のパラメータ定数（HRI文字のフォント選択）
	public static final int HRI_FONT_A = 0;
	public static final int HRI_FONT_B = 1;
	public static final int HRI_FONT_C = 2;
	public static final int HRI_FONT_D = 3;
	public static final int HRI_FONT_E = 4;
	public static final int HRI_FONT_SP_A = 97;
	public static final int HRI_FONT_SP_B = 98;

	/**
	 * HRI文字のフォント選択
	 * 
	 * @param value HRI_FONT_A(0), HRI_FONT_B(1)
	 * @return
	 */
	public byte[] _GS_f(int value) throws InvalidValueException {
		if (value < HRI_FONT_A || (value > HRI_FONT_E && value != HRI_FONT_SP_A && value != HRI_FONT_SP_B)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x66, (byte) value };
	}

	public static final int HEIGHT_MIN = 1;
	public static final int HEIGHT_MAX = 255;

	/**
	 * バーコードの高さの選択
	 * 
	 * @param height バーコードの高さ (1 〜 255)
	 * @return
	 */
	public byte[] _GS_h(int height) throws InvalidValueException {
		if (height < HEIGHT_MIN || height > HEIGHT_MAX) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x68, (byte) height };
	}

	public static final int WIDTH_MIN = 1;

	/**
	 * バーコードの横サイズの設定
	 * 
	 * @param width
	 * @return
	 * @throws InvalidValueException
	 */
	public byte[] _GS_w(int width) throws InvalidValueException {
		if (width < WIDTH_MIN) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x77, (byte) width };
	}

	// GS k 用のバーコードシステム定数（方式1: m = 0〜6）
	public static final int BARCODE_UPC_A = 0;
	public static final int BARCODE_UPC_E = 1; // 実際にはint
	public static final int BARCODE_JAN13 = 2;
	public static final int BARCODE_JAN8 = 3;
	public static final int BARCODE_CODE39 = 4;
	public static final int BARCODE_ITF = 5;
	public static final int BARCODE_CODABAR = 6;

	/**
	 * GS k (方式1: NUL終端形式)
	 * バーコードの印刷 (m = 0 〜 6)
	 * 
	 * @param m    バーコードシステム
	 * @param data バーコードデータ
	 * @return
	 */
	public byte[] _GS_k_type1(int m, byte[] data) throws InvalidValueException {
		if (m < BARCODE_UPC_A || m > BARCODE_CODABAR) {
			throw new InvalidValueException();
		}
		if (data == null || data.length == 0) {
			throw new InvalidValueException();
		}

		byte[] result = new byte[3 + data.length + 1];
		result[0] = (byte) 0x1D; // GS
		result[1] = (byte) 0x6B; // k
		result[2] = (byte) m;
		System.arraycopy(data, 0, result, 3, data.length);
		result[result.length - 1] = (byte) 0x00; // NUL終端
		return result;
	}

	// GS k 用のバーコードシステム定数（方式2: m = 65〜73）
	public static final int BARCODE_UPC_A_65 = 65;
	public static final int BARCODE_UPC_E_66 = 66;
	public static final int BARCODE_JAN13_67 = 67;
	public static final int BARCODE_JAN8_68 = 68;
	public static final int BARCODE_CODE39_69 = 69;
	public static final int BARCODE_ITF_70 = 70;
	public static final int BARCODE_CODABAR_71 = 71;
	public static final int BARCODE_CODE93_72 = 72;
	public static final int BARCODE_CODE128_73 = 73;

	/**
	 * GS k (方式2: データ長指定形式)
	 * バーコードの印刷 (m = 65 〜 73)
	 * 
	 * @param m    バーコードシステム
	 * @param data バーコードデータ
	 * @return
	 */
	public byte[] _GS_k_type2(int m, byte[] data) throws InvalidValueException {
		if (m < BARCODE_UPC_A_65 || m > BARCODE_CODE128_73) {
			throw new InvalidValueException();
		}
		if (data == null || data.length == 0 || data.length > 255) {
			throw new InvalidValueException();
		}

		int dataLen = data.length;
		byte[] result = new byte[3 + 1 + dataLen];
		result[0] = (byte) 0x1D; // GS
		result[1] = (byte) 0x6B; // k
		result[2] = (byte) m;
		result[3] = (byte) dataLen;
		System.arraycopy(data, 0, result, 4, dataLen);
		return result;
	}
}
