package jp.kawaguchi_shingo.libs.escpos.function;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import jp.kawaguchi_shingo.libs.escpos.command._Font;
import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;
import jp.kawaguchi_shingo.libs.escpos.exception.RangeException;

/**
 * フォントに関する操作を行うクラス
 */
public class Font {

	public static byte[] text(String text, Charset encode) {
		byte[] textBytes = text.getBytes(encode);
		return textBytes;
	}

	public static byte[] text(String text) {
		return text(text, StandardCharsets.UTF_8);
	}

	/**
	 * 文字の右スペース量の設定 (ESC SP)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws RangeException 値が範囲外の場合
	 */
	public static byte[] space(int value) throws RangeException {
		return _Font._ESC_SP(value);
	}

	/**
	 * 印字モードの一括指定 (ESC !)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] setting(int value) throws InvalidValueException {
		return _Font._ESC_exclamention(value);
	}

	/**
	 * アンダーラインの付加 (ESC _)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] underline(int value) throws InvalidValueException {
		return _Font._ESC_hyphon(value);
	}

	/**
	 * 強調印字 (ESC E)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] emphasis(int value) throws InvalidValueException {
		return _Font._ESC_E(value);
	}

	/**
	 * 文字の幅の設定 (ESC G)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] width(int value) throws InvalidValueException {
		return _Font._ESC_G(value);
	}

	/**
	 * フォントの選択 (ESC M)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] font(int value) throws InvalidValueException {
		return _Font._ESC_M(value);
	}

	/**
	 * 国際フォントの選択 (ESC R)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] intlFont(int value) throws InvalidValueException {
		return _Font._ESC_R(value);
	}

	/**
	 * 文字の回転 (ESC V)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] rotate90(int value) throws InvalidValueException {
		return _Font._ESC_V(value);
	}

	/**
	 * 文字の色設定 (ESC r)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] color(int value) throws InvalidValueException {
		return _Font._ESC_r(value);
	}

	/**
	 * コードページの設定 (ESC t)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] codepage(int value) throws InvalidValueException {
		return _Font._ESC_t(value);
	}

	/**
	 * 反転印字 (ESC {)
	 * 
	 * @param value 0 ～ 255
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] inverted(int value) throws InvalidValueException {
		return _Font._ESC_curly_bracket(value);
	}

	public static byte[] encode(int value) throws InvalidValueException {
		return _Font._FS_parenthesis_C_fn48(value);
	}

	public static byte[] priority(int primary, int secondary) throws InvalidValueException {
		return _Font._FS_parenthesis_C_fn60(primary, secondary);
	}

	public static byte[] hanSpace(int left, int right) throws InvalidValueException {
		return _Font._FS_S(left, right);
	}

	public static byte[] hanQuadruple(int value) throws InvalidValueException {
		return _Font._GS_exclamention(value);
	}

	public static byte[] hanMode() throws InvalidValueException {
		return _Font._FS_ampersand();
	}

	public static byte[] hanFont(int value) throws InvalidValueException {
		return _Font._FS_parenthesis_A(value);
	}

	public static byte[] hanUnderline(int value) throws InvalidValueException {
		return _Font._FS_hyphon(value);
	}

	public static byte[] hanCancel() throws InvalidValueException {
		return _Font._FS_dot();
	}

	public static byte[] size(int value) throws InvalidValueException {
		return _Font._GS_parenthesis_N_fn48(value);
	}

	public static byte[] color2(int value) throws InvalidValueException {
		return _Font._GS_parenthesis_N_fn48(value);
	}

	public static byte[] bgColor(int value) throws InvalidValueException {
		return _Font._GS_parenthesis_N_fn49(value);
	}

	public static byte[] shadow(int value, int color) throws InvalidValueException {
		return _Font._GS_parenthesis_N_fn50(value, color);
	}

	public static byte[] invertedWhiteBlack(int value) throws InvalidValueException {
		return _Font._GS_B(value);
	}

	public static byte[] smooth(int value) throws InvalidValueException {
		return _Font._GS_b(value);
	}
}
