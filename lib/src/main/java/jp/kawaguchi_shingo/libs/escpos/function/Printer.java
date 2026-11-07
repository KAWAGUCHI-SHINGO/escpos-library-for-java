package jp.kawaguchi_shingo.libs.escpos.function;

import jp.kawaguchi_shingo.libs.escpos.command._Printer;
import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;
import jp.kawaguchi_shingo.libs.escpos.exception.RangeException;

/**
 * プリンターの基本操作を行うクラス
 */
public class Printer {

	/**
	 * プリンターの初期化 (ESC @)
	 * 
	 * @return 初期化コマンドのバイト配列
	 */
	public static byte[] init() {
		return _Printer._ESC_at();
	}

	/**
	 * 改行量の初期化 (ESC 2)
	 * 
	 * @return 初期改行量設定コマンドのバイト配列
	 */
	public static byte[] initNl() {
		return _Printer._ESC_2();
	}

	/**
	 * 改行量の設定 (ESC 3)
	 * 
	 * @param value 0 ～ 255
	 * @return 改行量設定コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public static byte[] setNl(int value) throws InvalidValueException {
		return _Printer._ESC_3(value);
	}

	/**
	 * スタンダードモードへの切り替え (ESC S)
	 * 
	 * @return スタンダードモード選択コマンドのバイト配列
	 */
	public static byte[] standard() {
		return _Printer._ESC_S();
	}

	/**
	 * ページモードへの切り替え (ESC L)
	 * 
	 * @return ページモード選択コマンドのバイト配列
	 */
	public static byte[] pagemode() {
		return _Printer._ESC_L();
	}

	/**
	 * 文字ピッチの設定 (GS P)
	 * 
	 * @param x 水平方向のピッチ
	 * @param y 垂直方向のピッチ
	 * @return 文字ピッチ設定コマンドのバイト配列
	 * @throws RangeException 値が範囲外の場合
	 */
	public static byte[] setPitch(int x, int y) throws RangeException {
		return _Printer._GS_P(x, y);
	}

	public static byte[] set1mmPitch() throws RangeException {
		return _Printer._GS_P(24, 24);
	}

	/**
	 * バッファのクリア (DLE DC4 fn8)
	 * 
	 * @return バッファクリアコマンドのバイト配列
	 */
	public static byte[] clearBuffer() {
		return _Printer._DLE_DC4_fn8();
	}

	/**
	 * プリンターのオフ状態への設定 (DLE DC4 fn2)
	 * 
	 * @return オフ状態設定コマンドのバイト配列
	 */
	public static byte[] off() {
		return _Printer._DLE_DC4_fn2();
	}

}
