package jp.kawaguchi_shingo.libs.escpos.command;

import jp.kawaguchi_shingo.libs.escpos.exception.RangeException;

public class _Layout {

	/**
	 * 印字改行 (LF)
	 * 
	 * @return コマンドのバイト配列
	 */
	public static byte[] _LF() {
		return new byte[] { (byte) 0x0A };
	}

	/**
	 * 印字復帰 (CR)
	 * 
	 * @return コマンドのバイト配列
	 */
	public static byte[] _CR() {
		return new byte[] { (byte) 0x0D };
	}

	/**
	 * 水平タブ (HT)
	 * 
	 * @return コマンドのバイト配列
	 */
	public static byte[] _HT() {
		return new byte[] { (byte) 0x09 };
	}

	/**
	 * 絶対位置の指定 (ESC $)
	 * 
	 * @param nL 下位バイト
	 * @param nH 上位バイト
	 * @return コマンドのバイト配列
	 * @throws RangeException 位置が範囲外の場合
	 */
	public static byte[] _ESC_$(int nL, int nH) throws RangeException {
		// (nL + nH × 256) = 0 ～ 65535
		int position = nL + (nH * 256);
		if (position < 0 || position > 65535) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x24, (byte) nL, (byte) nH };
	}

	/**
	 * 水平タブ位置の設定
	 * 
	 * @param positions タブ位置の配列（1～255、昇順）。最後に自動で NUL (0) が付加されます。
	 * @return
	 */
	public static byte[] _ESC_D(int[] positions) {
		int len = (positions == null) ? 0 : positions.length;
		byte[] result = new byte[3 + len];
		result[0] = (byte) 0x1B;
		result[1] = (byte) 0x44; // 'D'
		for (int i = 0; i < len; i++) {
			result[2 + i] = (byte) positions[i];
		}
		result[result.length - 1] = (byte) 0x00; // 終端コード NUL
		return result;
	}

	// 印刷領域の印字方向指定 (ESC T) 用の定数
	public static final int DIRECTION_LEFT_TO_RIGHT = 0;
	public static final int DIRECTION_LEFT_TO_RIGHT_2 = 48;
	public static final int DIRECTION_BOTTOM_TO_TOP = 1;
	public static final int DIRECTION_BOTTOM_TO_TOP_2 = 49;
	public static final int DIRECTION_RIGHT_TO_LEFT = 2;
	public static final int DIRECTION_RIGHT_TO_LEFT_2 = 50;
	public static final int DIRECTION_TOP_TO_BOTTOM = 3;
	public static final int DIRECTION_TOP_TO_BOTTOM_2 = 51;

	/**
	 * ページモードにおける印字方向の選択
	 * 
	 * @param direction
	 * @return
	 */
	public static byte[] _ESC_T(int direction) {
		return new byte[] { (byte) 0x1B, (byte) 0x54, (byte) direction };
	}

	/**
	 * ページモードにおける印字領域の設定
	 * 
	 * @param xL  横方向の起点 (xL + xH × 256)
	 * @param xH
	 * @param yL  縦方向の起点 (yL + yH × 256)
	 * @param yH
	 * @param dxL 横幅 (dxL + dxH × 256)
	 * @param dxH
	 * @param dyL 縦幅 (dyL + dyH × 256)
	 * @param dyH
	 * @return
	 */
	public static byte[] _ESC_W(int xL, int xH, int yL, int yH, int dxL, int dxH, int dyL, int dyH) {
		return new byte[] {
				(byte) 0x1B, (byte) 0x57, // 'W'
				(byte) xL, (byte) xH,
				(byte) yL, (byte) yH,
				(byte) dxL, (byte) dxH,
				(byte) dyL, (byte) dyH
		};
	}

	/**
	 * 相対位置の指定
	 * 
	 * @param nL
	 * @param nH
	 * @return
	 */
	public static byte[] _ESC_backslash(int nL, int nH) {
		return new byte[] { (byte) 0x1B, (byte) 0x5C, (byte) nL, (byte) nH };
	}

	// 位置揃え (ESC a) 用の定数
	public static final int JUSTIFY_LEFT = 0;
	public static final int JUSTIFY_LEFT_2 = 48;
	public static final int JUSTIFY_CENTER = 1;
	public static final int JUSTIFY_CENTER_2 = 49;
	public static final int JUSTIFY_RIGHT = 2;
	public static final int JUSTIFY_RIGHT_2 = 50;

	/**
	 * 位置揃え（左揃え・中央揃え・右揃え）の指定
	 * 
	 * @param alignment
	 * @return
	 */
	public static byte[] _ESC_a(int alignment) {
		return new byte[] { (byte) 0x1B, (byte) 0x61, (byte) alignment };
	}

	/**
	 * ページモードにおける縦方向絶対位置の指定
	 * 
	 * @param nL
	 * @param nH
	 * @return
	 */
	public static byte[] _GS_$(int nL, int nH) {
		return new byte[] { (byte) 0x1D, (byte) 0x24, (byte) nL, (byte) nH };
	}

	/**
	 * 左マージンの設定
	 * 
	 * @param nL
	 * @param nH
	 * @return
	 */
	public static byte[] _GS_L(int nL, int nH) {
		return new byte[] { (byte) 0x1D, (byte) 0x4C, (byte) nL, (byte) nH };
	}

	/**
	 * ページモードにおけるデータ向きの設定
	 * 
	 * @param direction 0, 48: 左から右 / 1, 49: 下から上
	 * @return
	 */
	public static byte[] _GS_T(int direction) {
		return new byte[] { (byte) 0x1D, (byte) 0x54, (byte) direction };
	}

	/**
	 * 印字領域幅の設定
	 * 
	 * @param nL
	 * @param nH
	 * @return
	 */
	public static byte[] _GS_W(int nL, int nH) {
		return new byte[] { (byte) 0x1D, (byte) 0x57, (byte) nL, (byte) nH };
	}

	/**
	 * ページモードにおける縦方向相対位置の指定
	 * 
	 * @param nL
	 * @param nH
	 * @return
	 */
	public static byte[] _GS_backslash(int nL, int nH) {
		return new byte[] { (byte) 0x1D, (byte) 0x5C, (byte) nL, (byte) nH };
	}
}
