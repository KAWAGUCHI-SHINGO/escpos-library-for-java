package jp.kawaguchi_shingo.libs.escpos.command;

import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;

/**
 * ブザーに関するコマンドを生成するクラス
 */
public class Buzzer {

	public static final int BUZZER_STOP = 0;
	public static final int BUZZER_RING = 1;

	/**
	 * ブザーのリアルタイム鳴動 (DLE DC4 fn3)
	 * 
	 * @param a  鳴動パターン
	 * @param n  (未使用)
	 * @param r  鳴動回数
	 * @param t1 (未使用)
	 * @param t2 (未使用)
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException
	 */
	public byte[] _DLE_DC4_fn3(int a, int n, int r, int t1, int t2) {
		return new byte[] { (byte) 0x10, (byte) 0x14, (byte) 0x03, (byte) a, (byte) n, (byte) r, (byte) t1, (byte) t2 };
	}

	/**
	 * 汎用的な ESC ( A コマンドのヘッダーおよび長さ・fnを組み立てるヘルパーメソッド
	 * 
	 * @param fn   機能番号 (fn)
	 * @param data データ
	 * @return コマンドのバイト配列
	 */
	private byte[] createEscInACommand(int fn, byte[] data) {
		int dataLen = data != null ? data.length : 0;
		// pL, pH は fn(1バイト) + データ長の合計
		int totalLen = 1 + dataLen;
		byte pL = (byte) (totalLen & 0xFF);
		byte pH = (byte) ((totalLen >> 8) & 0xFF);

		byte[] result = new byte[5 + totalLen];
		result[0] = (byte) 0x1B; // ESC
		result[1] = (byte) 0x28; // (
		result[2] = (byte) 0x41; // A
		result[3] = pL;
		result[4] = pH;
		result[5] = (byte) fn;

		if (dataLen > 0) {
			System.arraycopy(data, 0, result, 6, dataLen);
		}
		return result;
	}

	/**
	 * ブザーの鳴動
	 * 
	 * @param n 鳴らす音の種類を指定
	 * @param c 鳴らす回数を指定
	 * @param t 音を鳴らす周期を (t × 100 ms) に指定
	 * @return コマンドのバイト配列
	 */
	public byte[] _ESC_parenthesis_A_fn48(int n, int c, int t) {
		return createEscInACommand(0x30, new byte[] { (byte) n, (byte) c, (byte) t });
	}

	/**
	 * 以下の関数は機種毎にパラメータを変更しなければならないと思われる。
	 * ESC ( A <fn=97>
	 * 
	 * @param n 鳴動パターン
	 * @param c 鳴動回数
	 * @param t 音を鳴らす周期 (t × 100 ms)
	 * @return コマンドのバイト配列
	 */
	public byte[] _ESC_parenthesis_A_fn97(int n, int c, int t) {
		return createEscInACommand(97, new byte[] { (byte) n, (byte) c, (byte) t });
	}

	/**
	 * ESC ( A <fn=98>
	 * 
	 * @param n 鳴動パターン
	 * @param c 鳴動回数
	 * @param t 音を鳴らす周期 (t × 100 ms)
	 * @return コマンドのバイト配列
	 */
	public byte[] _ESC_parenthesis_A_fn98(int n, int c, int t) {
		return createEscInACommand(98, new byte[] { (byte) n, (byte) c, (byte) t });
	}

	/**
	 * ESC ( A <fn=99>
	 * 
	 * @param n 鳴動パターン
	 * @param c 鳴動回数
	 * @param t 音を鳴らす周期 (t × 100 ms)
	 * @return コマンドのバイト配列
	 */
	public byte[] _ESC_parenthesis_A_fn99(int n, int c, int t) {
		return createEscInACommand(99, new byte[] { (byte) n, (byte) c, (byte) t });
	}

}