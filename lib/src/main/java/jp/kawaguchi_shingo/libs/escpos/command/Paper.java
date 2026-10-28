package jp.kawaguchi_shingo.libs.escpos.command;

import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;

public class Paper {

	/**
	 * 汎用的な FS ( L コマンドのヘッダーおよび長さ・fnを組み立てるヘルパーメソッド
	 * 
	 * @param fn  機能番号 (fn)
	 * @param data データ
	 * @return コマンドのバイト配列
	 */
	private byte[] createFsInLCommand(int fn, byte[] data) {
		// pL, pH は fn(1バイト) + データ長の合計
		int totalLen = 1 + (data != null ? data.length : 0);
		byte pL = (byte) (totalLen & 0xFF);
		byte pH = (byte) ((totalLen >> 8) & 0xFF);

		int dataLen = data != null ? data.length : 0;
		byte[] result = new byte[4 + totalLen];
		result[0] = (byte) 0x1C; // FS
		result[1] = (byte) 0x28; // (
		result[2] = (byte) 0x4C; // L
		result[3] = pL;
		result[4] = pH;
		result[5] = (byte) fn;

		if (dataLen > 0) {
			System.arraycopy(data, 0, result, 6, dataLen);
		}
		return result;
	}

	/**
	 * FS ( L <fn=33>
	 * 用紙レイアウト情報の保存
	 * 
	 * @return コマンドのバイト配列
	 */
	public byte[] _FS_parenthesis_L_fn33() {
		return createFsInLCommand(33, null);
	}

	/**
	 * FS ( L <fn=34>
	 * 用紙レイアウト情報の送信
	 * 
	 * @return コマンドのバイト配列
	 */
	public byte[] _FS_parenthesis_L_fn34() {
		return createFsInLCommand(34, null);
	}

	// fn=48 用のパラメータ定数（用紙レイアウト情報の選択）
	public static final int LAYOUT_PAPER_SETTING = 48;
	public static final int LAYOUT_LABEL_POSITION = 49;
	public static final int LAYOUT_BLACK_MARK_POSITION = 50;

	/**
	 * FS ( L <fn=48>
	 * 用紙レイアウト情報の選択
	 * 
	 * @param value LAYOUT_PAPER_SETTING(48), LAYOUT_LABEL_POSITION(49),
	 *              LAYOUT_BLACK_MARK_POSITION(50)
	 * @return
	 */
	public byte[] _FS_parenthesis_L_fn48(int value) throws InvalidValueException {
		if (value != LAYOUT_PAPER_SETTING && value != LAYOUT_LABEL_POSITION && value != LAYOUT_BLACK_MARK_POSITION) {
			throw new InvalidValueException();
		}
		return createFsInLCommand(48, new byte[] { (byte) value });
	}

	// fn=65 用のパラメータ定数（用紙の基本カット位置への位置合わせ）
	public static final int CUT_POSITION_MANUAL = 48;
	public static final int CUT_POSITION_AUTO_CUT = 49;

	/**
	 * FS ( L <fn=65>
	 * 用紙の基本カット位置への位置合わせ
	 * 
	 * @param value CUT_POSITION_MANUAL(48), CUT_POSITION_AUTO_CUT(49)
	 * @return
	 */
	public byte[] _FS_parenthesis_L_fn65(int value) throws InvalidValueException {
		if (value != CUT_POSITION_MANUAL && value != CUT_POSITION_AUTO_CUT) {
			throw new InvalidValueException();
		}
		return createFsInLCommand(65, new byte[] { (byte) value });
	}

	/**
	 * FS ( L <fn=66>
	 * 用紙を頭出し位置まで順方向へフィード
	 * 
	 * @return
	 */
	public byte[] _FS_parenthesis_L_fn66() {
		return createFsInLCommand(66, null);
	}

	/**
	 * FS ( L <fn=67>
	 * 用紙を頭出し位置まで逆方向へフィード
	 * 
	 * @return
	 */
	public byte[] _FS_parenthesis_L_fn67() {
		return createFsInLCommand(67, null);
	}

	// fn=80 用のパラメータ定数（用紙種類の選択）
	public static final int PAPER_TYPE_ROLL = 48;
	public static final int PAPER_TYPE_BLACK_MARK = 49;
	public static final int PAPER_TYPE_LABEL = 50;

	/**
	 * FS ( L <fn=80>
	 * 用紙種類の選択
	 * 
	 * @param value PAPER_TYPE_ROLL(48), PAPER_TYPE_BLACK_MARK(49),
	 *              PAPER_TYPE_LABEL(50)
	 * @return
	 */
	public byte[] _FS_parenthesis_L_fn80(int value) throws InvalidValueException {
		if (value != PAPER_TYPE_ROLL && value != PAPER_TYPE_BLACK_MARK && value != PAPER_TYPE_LABEL) {
			throw new InvalidValueException();
		}
		return createFsInLCommand(80, new byte[] { (byte) value });
	}

	public static final int PAPER_CUT_FULL = 0;
	public static final int PAPER_CUT_PARTIAL = 1;

	/**
	 * 用紙のカット
	 */
	public byte[] _GS_V(int m) throws InvalidValueException {
		if (m != PAPER_CUT_FULL && m != PAPER_CUT_PARTIAL) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x56, (byte) m };
	}

	public static final int PAPER_FEED_CUT_FULL = 65;
	public static final int PAPER_FEED_CUT_PARTIAL = 66;

	public byte[] _GS_V_feed(int m, int n) throws InvalidValueException {
		if (m != PAPER_FEED_CUT_FULL && m != PAPER_FEED_CUT_PARTIAL) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x56, (byte) m, (byte) n };
	}

	public static final int PAPER_RESERVE_CUT_FULL = 97;
	public static final int PAPER_RESERVE_CUT_PARTIAL = 98;

	public byte[] _GS_V_reserve(int m, int n) throws InvalidValueException {
		if (m != PAPER_RESERVE_CUT_FULL && m != PAPER_RESERVE_CUT_PARTIAL) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x56, (byte) m, (byte) n };
	}

	public static final int PAPER_QUEING_CUT_FULL = 103;
	public static final int PAPER_QUEING_CUT_PARTIAL = 104;

	public byte[] _GS_V_cueing(int m, int n) throws InvalidValueException {
		if (m != PAPER_QUEING_CUT_FULL && m != PAPER_QUEING_CUT_PARTIAL) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x56, (byte) m, (byte) n };
	}
}
