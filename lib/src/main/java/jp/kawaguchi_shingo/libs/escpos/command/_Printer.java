package jp.kawaguchi_shingo.libs.escpos.command;

import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;
import jp.kawaguchi_shingo.libs.escpos.exception.RangeException;

/**
 * プリンターの制御に関するコマンドを生成するクラス
 */
public class _Printer {
	/**
	 * 初期改行量の設定 (ESC 2)
	 * 
	 * @return コマンドのバイト配列
	 */
	public static byte[] _ESC_2() {
		return new byte[] { (byte) 0x1B, (byte) 0x32 };
	}

	/**
	 * 改行量の設定 (ESC 3)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] _ESC_3(int value) throws InvalidValueException {
		if (value < 0 || value > 255) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x33, (byte) value };
	}

	/**
	 * スタンダードモードの選択 (ESC S)
	 * ページモードからスタンダードモードへ切り替えます。
	 * 
	 * @return スタンダードモード選択コマンドのバイト配列
	 */
	public static byte[] _ESC_S() {
		return new byte[] { (byte) 0x1B, (byte) 0x53 };
	}

	/**
	 * ページモードの選択 (ESC L)
	 * スタンダードモードからページモードへ切り替えます。
	 * 
	 * @return ページモード選択コマンドのバイト配列
	 */
	public static byte[] _ESC_L() {
		return new byte[] { (byte) 0x1B, (byte) 0x4C };
	}

	/**
	 * プリンターの初期化 (ESC @)
	 * 
	 * @return 初期化コマンドのバイト配列
	 */
	public static byte[] _ESC_at() {
		return new byte[] { (byte) 0x1B, (byte) 0x40 };
	}

	/**
	 * 基本計算ピッチの設定 (GS P)
	 * 
	 * @param x 横方向の起点 (0 ～ 255)
	 * @param y 縦方向の起点 (0 ～ 255)
	 * @return 設定コマンドのバイト配列
	 * @throws RangeException 値が範囲外の場合
	 */
	public static byte[] _GS_P(int x, int y) throws RangeException {
		if (x < 0 || x > 255 || y < 0 || y > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x50, (byte) x, (byte) y };
	}

	/**
	 * バッファークリア (DLE DC4 fn8)
	 * 
	 * @return バッファクリアコマンドのバイト配列
	 */
	public static byte[] _DLE_DC4_fn8() {
		return new byte[] { (byte) 0x10, (byte) 0x14, (byte) 0x08, (byte) 0x01, (byte) 0x03, (byte) 0x14, (byte) 0x01,
				(byte) 0x06, (byte) 0x02, (byte) 0x08 };
	}

	/**
	 * 電源オフ処理の実行 (DLE DC4 fn2)
	 * 
	 * @return 電源オフコマンドのバイト配列
	 */
	public static byte[] _DLE_DC4_fn2() {
		return new byte[] { (byte) 0x10, (byte) 0x14, (byte) 0x02, (byte) 0x01, (byte) 0x08 };
	}

}