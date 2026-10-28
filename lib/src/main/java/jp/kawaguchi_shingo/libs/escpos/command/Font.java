package jp.kawaguchi_shingo.libs.escpos.command;

import java.util.Arrays;

import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;
import jp.kawaguchi_shingo.libs.escpos.exception.RangeException;

public class Font {

	/**
	 * 文字の右スペース量の設定
	 * 
	 * @param value 0 ～ 255
	 * @return
	 */
	public byte[] _ESC_SP(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x20, (byte) value };
	}

	// フォント 1 を選択する
	public static final int FONT_1 = 0;
	// フォント 2 を選択する
	public static final int FONT_2 = 1;
	// 強調印字を指定する
	public static final int FONT_EM = 8;
	// 縦倍拡大を指定する
	public static final int FONT_2WIDTH_Y = 16;
	// 横倍拡大を指定する
	public static final int FONT_2WIDTH_X = 32;
	// アンダーラインの付加を指定する
	public static final int FONT_UNDERLINE = 128;

	/**
	 * 印字モードの一括指定
	 * 
	 * @param value 0 ～ 255
	 * @return
	 */
	public byte[] _ESC_exclamention(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x21, (byte) value };
	}

	/**
	 * ダウンロード文字セットの指定・解除
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws RangeException 値が範囲外の場合
	 */
	public byte[] _ESC_percent(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x74, (byte) value };
	}

	/**
	 * ダウンロード文字の定義
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws RangeException 値が範囲外の場合
	 */
	public byte[] _ESC_ampersand(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x74, (byte) value };
	}

	/**
	 * ダウンロード文字の抹消
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws RangeException 値が範囲外の場合
	 */
	public byte[] _ESC_question(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x74, (byte) value };
	}

	// アンダーラインの付加を解除する
	public static final int UNDERLINE_OFF = 0;
	public static final int UNDERLINE_OFF2 = 48;
	// 1 ドット幅のアンダーラインの設定とアンダーラインの付加を指定する
	public static final int UNDERLINE_1DOT = 1;
	public static final int UNDERLINE_1DOT2 = 49;
	// 2 ドット幅のアンダーラインの設定とアンダーラインの付加を指定する
	public static final int UNDERLINE_2DOT = 2;
	public static final int UNDERLINE_2DOT2 = 50;

	/**
	 * アンダーラインの指定・解除
	 * 
	 * @param value
	 * @return
	 */
	public byte[] _ESC_hyphon(int value) throws InvalidValueException {
		int[] array = {
				UNDERLINE_OFF, UNDERLINE_OFF2,
				UNDERLINE_1DOT, UNDERLINE_1DOT2,
				UNDERLINE_2DOT, UNDERLINE_2DOT2 };
		if (!Arrays.stream(array).anyMatch(num -> num == value)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x2D, (byte) value };
	}

	// 強調印字を解除する
	public static final int EM_OFF = 0;
	// 強調印字を指定する
	public static final int EM = 1;

	/**
	 * 強調印字の指定・解除
	 * 
	 * @param value
	 * @return
	 */
	public byte[] _ESC_E(int value) throws InvalidValueException {
		int[] array = { EM_OFF, EM };
		if (!Arrays.stream(array).anyMatch(num -> num == value)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x45, (byte) value };
	}

	// 二重印字を解除する
	public static final int STRIKE2_OFF = 0;
	// 二重印字を指定する
	public static final int STRIKE2 = 1;

	/**
	 * 二重印字の指定・解除
	 * 
	 * @param value
	 * @return
	 */
	public byte[] _ESC_G(int value) throws InvalidValueException {
		int[] array = { STRIKE2_OFF, STRIKE2 };
		if (!Arrays.stream(array).anyMatch(num -> num == value)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x47, (byte) value };
	}

	// 文字フォントを選択
	public static final int FONT_A = 0;
	public static final int FONT_B = 1;
	public static final int FONT_C = 2;
	public static final int FONT_D = 3;
	public static final int FONT_E = 4;
	public static final int FONT_A2 = 48;
	public static final int FONT_B2 = 49;
	public static final int FONT_C2 = 50;
	public static final int FONT_D2 = 51;
	public static final int FONT_E2 = 52;
	public static final int FONT_SP1 = 97;
	public static final int FONT_SP2 = 98;

	/**
	 * 文字フォントの選択
	 * 
	 * @param value
	 * @return
	 */
	public byte[] _ESC_M(int value) throws InvalidValueException {
		int[] array = {
				FONT_A, FONT_A2,
				FONT_B, FONT_B2,
				FONT_C, FONT_C2,
				FONT_D, FONT_D2,
				FONT_E, FONT_E2,
				FONT_SP1, FONT_SP2 };
		if (!Arrays.stream(array).anyMatch(num -> num == value)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x4D, (byte) value };
	}

	public static final int LANG_US = 0;
	public static final int LANG_FRANCE = 1;
	public static final int LANG_GERMANY = 2;
	public static final int LANG_UK = 3;
	public static final int LANG_DENMARK_1 = 4;
	public static final int LANG_SWEDEN = 5;
	public static final int LANG_ITALY = 6;
	public static final int LANG_SPAIN_1 = 7;
	public static final int LANG_JAPAN = 8;
	public static final int LANG_NORWAY = 9;
	public static final int LANG_DENMARK_2 = 10;
	public static final int LANG_SPAIN_2 = 11;
	public static final int LANG_LATIN_AMERICA = 12;
	public static final int LANG_KOREA = 13;
	public static final int LANG_SLOVENIA_CROATIA = 14;
	public static final int LANG_CHINA = 15;
	public static final int LANG_VIETNAM = 16;
	public static final int LANG_ARABIA = 17;
	public static final int LANG_INDIA_DEVANAGARI = 66;
	public static final int LANG_INDIA_BENGALI = 67;
	public static final int LANG_INDIA_TAMIL = 68;
	public static final int LANG_INDIA_TELUGU = 69;
	public static final int LANG_INDIA_ASSAMESE = 70;
	public static final int LANG_INDIA_ORIYA = 71;
	public static final int LANG_INDIA_KANNADA = 72;
	public static final int LANG_INDIA_MALAYALAM = 73;
	public static final int LANG_INDIA_GUJARATI = 74;
	public static final int LANG_INDIA_PUNJABI = 75;
	public static final int LANG_INDIA_MARATHI = 82;

	/**
	 * 国際文字の選択
	 * 
	 * @param value
	 * @return
	 */
	public byte[] _ESC_R(int value) throws InvalidValueException {
		if (value < LANG_US || (value > LANG_ARABIA && value < LANG_INDIA_DEVANAGARI) || value > LANG_INDIA_MARATHI) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x52, (byte) value };
	}

	public static final int ROTATE_OFF = 0;
	public static final int ROTATE_OFF2 = 48;
	public static final int ROTATE_90 = 1;
	public static final int ROTATE_90_2 = 49;
	public static final int ROTATE_90B = 2;
	public static final int ROTATE_90B_2 = 50;

	/**
	 * 文字の90度右回転の指定・解除
	 * 
	 * @param value
	 * @return
	 */
	public byte[] _ESC_V(int value) throws InvalidValueException {
		int[] array = {
				ROTATE_OFF, ROTATE_OFF2,
				ROTATE_90, ROTATE_90_2,
				ROTATE_90B, ROTATE_90B_2 };
		if (!Arrays.stream(array).anyMatch(num -> num == value)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x56, (byte) value };
	}

	// 印字色の選択 (ESC r)
	public static final int COLOR_BLACK = 0;
	public static final int COLOR_BLACK2 = 48;
	public static final int COLOR_RED = 1;
	public static final int COLOR_RED2 = 49;

	/**
	 * 印字色の選択
	 * 
	 * @param value
	 * @return
	 */
	public byte[] _ESC_r(int value) throws InvalidValueException {
		int[] array = { COLOR_BLACK, COLOR_BLACK2, COLOR_RED, COLOR_RED2 };
		if (!Arrays.stream(array).anyMatch(num -> num == value)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x72, (byte) value };
	}

	// コードページ（文字コード表）の選択設定 (ESC t)
	public static final int CODEPAGE_PC437 = 0;
	public static final int CODEPAGE_KATAKANA = 1;
	public static final int CODEPAGE_PC850 = 2;
	public static final int CODEPAGE_PC860 = 3;
	public static final int CODEPAGE_PC863 = 4;
	public static final int CODEPAGE_PC865 = 5;
	public static final int CODEPAGE_KANJI_JAPANESE = 6;
	public static final int CODEPAGE_CP851 = 11;
	public static final int CODEPAGE_CP853 = 12;
	public static final int CODEPAGE_CP857 = 13;
	public static final int CODEPAGE_CP737 = 14;
	public static final int CODEPAGE_ISO8859_7 = 15;
	public static final int CODEPAGE_WPC1252 = 16;
	public static final int CODEPAGE_PC866 = 17;
	public static final int CODEPAGE_PC852 = 18;
	public static final int CODEPAGE_PC858 = 19;
	public static final int CODEPAGE_THAI11 = 20;
	public static final int CODEPAGE_THAI18 = 21;
	public static final int CODEPAGE_TCVN3_1 = 22;
	public static final int CODEPAGE_TCVN3_2 = 23;
	public static final int CODEPAGE_PC720 = 24;
	public static final int CODEPAGE_PC855 = 25;
	public static final int CODEPAGE_PC861 = 26;
	public static final int CODEPAGE_PC862 = 27;
	public static final int CODEPAGE_PC864 = 28;
	public static final int CODEPAGE_PC869 = 29;
	public static final int CODEPAGE_ISO8859_2 = 30;
	public static final int CODEPAGE_ISO8859_15 = 31;
	public static final int CODEPAGE_PC1098 = 32;
	public static final int CODEPAGE_PC1118 = 33;
	public static final int CODEPAGE_PC1119 = 34;
	public static final int CODEPAGE_PC1125 = 35;
	public static final int CODEPAGE_WPC1250 = 36;
	public static final int CODEPAGE_WPC1251 = 37;
	public static final int CODEPAGE_WPC1253 = 38;
	public static final int CODEPAGE_WPC1254 = 39;
	public static final int CODEPAGE_WPC1255 = 40;
	public static final int CODEPAGE_WPC1256 = 41;
	public static final int CODEPAGE_WPC1257 = 42;
	public static final int CODEPAGE_WPC1258 = 43;
	public static final int CODEPAGE_KZ1048 = 44;
	// public static final int CODEPAGE_RESERVE1 = 254;
	// public static final int CODEPAGE_RESERVE2 = 255;

	/**
	 * コードページ（文字コード表）の選択
	 * 
	 * @param value 0 ～ 255
	 * @return
	 */
	public byte[] _ESC_t(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x74, (byte) value };
	}

	// 上下反転印字の指定・解除 (ESC {)
	public static final int UPSIDE_DOWN_OFF = 0;
	public static final int UPSIDE_DOWN_OFF2 = 48;
	public static final int UPSIDE_DOWN_ON = 1;
	public static final int UPSIDE_DOWN_ON2 = 49;

	/**
	 * 倒立印字の指定・解除
	 * 上下反転印字の指定・解除
	 * 
	 * @param value
	 * @return
	 */
	public byte[] _ESC_curly_bracket(int value) throws InvalidValueException {
		int[] array = { UPSIDE_DOWN_OFF, UPSIDE_DOWN_OFF2, UPSIDE_DOWN_ON, UPSIDE_DOWN_ON2 };
		if (!Arrays.stream(array).anyMatch(num -> num == value)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x7B, (byte) value };
	}

	public static final int ENCODE_1 = 1;
	public static final int ENCODE_2 = 2;
	public static final int ENCODE_3 = 49;
	public static final int ENCODE_4 = 50;

	public byte[] _FS_parenthesis_C_fn48(int m) throws InvalidValueException {
		int[] array = { ENCODE_1, ENCODE_2, ENCODE_3, ENCODE_4 };
		if (!Arrays.stream(array).anyMatch(num -> num == m)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1C, (byte) 0x28, (byte) 0x43, (byte) 0x02, (byte) 0x00, (byte) 0x30, (byte) m };
	}

	public static final int TYPE_PRIMARY = 0;
	public static final int TYPE_SECONDARY = 1;

	public static final int TYPE_ANK = 0;
	public static final int TYPE_JAPANESE = 11;
	public static final int TYPE_CHINESE = 20;
	public static final int TYPE_TAIWANESE = 30;
	public static final int TYPE_KOREAN = 41;

	public byte[] _FS_parenthesis_C_fn60(int m, int a) throws InvalidValueException {
		if (m != TYPE_PRIMARY && m != TYPE_SECONDARY) {
			throw new InvalidValueException();
		}
		int[] array = { TYPE_ANK, TYPE_JAPANESE, TYPE_CHINESE, TYPE_TAIWANESE, TYPE_KOREAN };
		if (!Arrays.stream(array).anyMatch(num -> num == a)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1C, (byte) 0x28, (byte) 0x43, (byte) 0x03, (byte) 0x00, (byte) 0x3C, (byte) m,
				(byte) a };
	}

	public static final int HAN_MODE_CANCEL = 0;
	public static final int HAN_WIDTH_2X = 4;
	public static final int HAN_HEIGHT_2X = 8;
	public static final int HAN_UNDERLINE = 128;

	public byte[] _FS_exclamention(int n) throws InvalidValueException {
		int[] array = { HAN_MODE_CANCEL, HAN_WIDTH_2X, HAN_HEIGHT_2X, HAN_UNDERLINE };
		if (!Arrays.stream(array).anyMatch(num -> num == n)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x21, (byte) n };
	}

	public byte[] _FS_ampersand() {
		return new byte[] { (byte) 0x1C, (byte) 0x26 };
	}

	public static final int HAN_FONT_A = 0;
	public static final int HAN_FONT_B = 1;
	public static final int HAN_FONT_C = 2;

	public byte[] _FS_parenthesis_A(int m) throws InvalidValueException {
		if (m != HAN_FONT_A && m != HAN_FONT_B && m != HAN_FONT_C) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1C, (byte) 0x28, (byte) 0x41, (byte) 0x02, (byte) 0x00, (byte) 0x30, (byte) m };
	}

	public static final int HAN_UNDERLINE_1DOT_WIDTH = 1;
	public static final int HAN_UNDERLINE_2DOT_WIDTH = 2;

	public byte[] _FS_hyphon(int n) throws InvalidValueException {
		if (n != HAN_UNDERLINE_1DOT_WIDTH && n != HAN_UNDERLINE_2DOT_WIDTH) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1C, (byte) 0x2D, (byte) n };
	}

	public byte[] _FS_dot() {
		return new byte[] { (byte) 0x1C, (byte) 0x2E };
	}

	/**
	 * 外字の定義 <<<<<<<<<<<<<<
	 * この処理の優先度は高くないので後回し
	 * 
	 * @return
	 */
	public byte[] _FS_2() {
		return new byte[] { (byte) 0x1C, (byte) 0x32 };
	}

	public static final int CODE_JIS = 0;
	public static final int CODE_SHIFT_JIS = 1;
	public static final int CODE_SHIFT_JIS_2004 = 2;

	public byte[] _FS_C(int n) throws InvalidValueException {
		if (n != CODE_JIS && n != CODE_SHIFT_JIS && n != CODE_SHIFT_JIS_2004) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1C, (byte) 0x43, (byte) n };
	}

	public byte[] _FS_S(int n1, int n2) throws InvalidValueException {
		if (n1 < 0 || n2 < 0) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1C, (byte) 0x53, (byte) n1, (byte) n2 };
	}

	public byte[] _FS_W(int n) throws InvalidValueException {
		if (n < 0 && n > 255) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1C, (byte) 0x57, (byte) n };
	}

	// 文字サイズの指定（GS !）用マスク・定数
	public static final int SIZE_WIDTH_1X = 0x00;
	public static final int SIZE_WIDTH_2X = 0x10;
	public static final int SIZE_WIDTH_3X = 0x20;
	public static final int SIZE_WIDTH_4X = 0x30;
	public static final int SIZE_WIDTH_5X = 0x40;
	public static final int SIZE_WIDTH_6X = 0x50;
	public static final int SIZE_WIDTH_7X = 0x60;
	public static final int SIZE_WIDTH_8X = 0x70;
	public static final int SIZE_HEIGHT_1X = 0x00;
	public static final int SIZE_HEIGHT_2X = 0x01;
	public static final int SIZE_HEIGHT_3X = 0x02;
	public static final int SIZE_HEIGHT_4X = 0x03;
	public static final int SIZE_HEIGHT_5X = 0x04;
	public static final int SIZE_HEIGHT_6X = 0x05;
	public static final int SIZE_HEIGHT_7X = 0x06;
	public static final int SIZE_HEIGHT_8X = 0x07;

	/**
	 * 文字サイズの指定（縦倍率・横倍率の一括指定）
	 *
	 * @param value 0 ～ 255
	 * @return
	 */
	public byte[] _GS_exclamention(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x21, (byte) value };
	}

	public byte[] _GS_parenthesis_N_fn48(int m) throws InvalidValueException {
		int[] array = { ENCODE_1, ENCODE_2, ENCODE_3, ENCODE_4 };
		if (!Arrays.stream(array).anyMatch(num -> num == m)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x28, (byte) 0x4E, (byte) 0x02, (byte) 0x00, (byte) 0x30, (byte) m };
	}

	public static final int BG_COLOR_NONE = 48;
	public static final int BG_COLOR_1 = 49;
	public static final int BG_COLOR_2 = 50;
	public static final int BG_COLOR_3 = 51;

	public byte[] _GS_parenthesis_N_fn49(int m) throws InvalidValueException {
		int[] array = { BG_COLOR_NONE, BG_COLOR_1, BG_COLOR_2, BG_COLOR_3 };
		if (!Arrays.stream(array).anyMatch(num -> num == m)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x28, (byte) 0x4E, (byte) 0x02, (byte) 0x00, (byte) 0x31, (byte) m };
	}

	public static final int SHADOW_NONE = 0;
	public static final int SHADOW = 1;

	public static final int SHADOW_COLOR_NONE = 48;
	public static final int SHADOW_COLOR_1 = 49;
	public static final int SHADOW_COLOR_2 = 50;
	public static final int SHADOW_COLOR_3 = 51;

	public byte[] _GS_parenthesis_N_fn50(int m, int a) throws InvalidValueException {
		if (m != SHADOW_NONE && m != SHADOW) {
			throw new InvalidValueException();
		}
		int[] array = { ENCODE_1, ENCODE_2, ENCODE_3, ENCODE_4 };
		if (!Arrays.stream(array).anyMatch(num -> num == a)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x28, (byte) 0x4E, (byte) 0x03, (byte) 0x00, (byte) 0x32, (byte) m,
				(byte) a };
	}

	// 白黒反転印字の指定・解除 (GS B)
	public static final int INVERT_OFF = 0;
	public static final int INVERT_OFF2 = 48;
	public static final int INVERT_ON = 1;
	public static final int INVERT_ON2 = 49;

	/**
	 * 白黒反転印字の指定・解除
	 *
	 * @param value 指定する反転状態
	 * @return 印字コマンドのバイト配列
	 * @throws InvalidValueException 指定された値が無効な場合
	 */
	public byte[] _GS_B(int value) throws InvalidValueException {
		int[] array = { INVERT_OFF, INVERT_OFF2, INVERT_ON, INVERT_ON2 };
		if (!Arrays.stream(array).anyMatch(num -> num == value)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x42, (byte) value };
	}

	// スムージングの指定・解除 (GS b)
	public static final int SMOOTHING_OFF = 0;
	public static final int SMOOTHING_OFF2 = 48;
	public static final int SMOOTHING_ON = 1;
	public static final int SMOOTHING_ON2 = 49;

	/**
	 * スムージングの指定・解除
	 *
	 * @param value 指定するスムージング状態
	 * @return 印字コマンドのバイト配列
	 * @throws InvalidValueException 指定された値が無効な場合
	 */
	public byte[] _GS_b(int value) throws InvalidValueException {
		int[] array = { SMOOTHING_OFF, SMOOTHING_OFF2, SMOOTHING_ON, SMOOTHING_ON2 };
		if (!Arrays.stream(array).anyMatch(num -> num == value)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x62, (byte) value };
	}

}
