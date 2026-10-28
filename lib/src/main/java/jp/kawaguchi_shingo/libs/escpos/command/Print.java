package jp.kawaguchi_shingo.libs.escpos.command;

import java.util.Arrays;

import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;
import jp.kawaguchi_shingo.libs.escpos.exception.RangeException;

/**
 * 印字に関するコマンドを生成するクラス
 */
public class Print {

	/**
	 * 印字改行 (LF)
	 * 
	 * @return 改行コマンドのバイト配列
	 */
	public byte[] _LF() {
		return new byte[] { (byte) 0x0A };
	}

	/**
	 * ページモードの印字と復帰 (FF)
	 * 
	 * ページモード選択時において、ページモードで展開したデータを一括印字し、スタンダードモードに切り替えます。
	 * スタンダードモード選択時において、“印刷が完了した状態”とします。
	 * 
	 * @return ページモード印字・復帰コマンドのバイト配列
	 */
	public byte[] _FF() {
		return new byte[] { (byte) 0x0C };
	}

	/**
	 * 印字復帰 (CR)
	 * 
	 * @return 印字復帰コマンドのバイト配列
	 */
	public byte[] _CR() {
		return new byte[] { (byte) 0x0D };
	}

	/**
	 * ページモードのデータ印字 (ESC FF)
	 * 
	 * @return ページモードデータ印字コマンドのバイト配列
	 */
	public byte[] _ESC_FF() {
		return new byte[] { (byte) 0x1B, (byte) 0x0C };
	}

	/**
	 * ページモードのデータ印字 (ESC J)
	 * 
	 * @param value 0 ～ 255
	 * @return 印字コマンドのバイト配列
	 * @throws RangeException 値が範囲外の場合
	 */
	public byte[] _ESC_J(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x4A, (byte) value };
	}

	/**
	 * 印字および逆方向紙送り (ESC K)
	 * 
	 * @param value 0 ～ 255
	 * @return 印字コマンドのバイト配列
	 * @throws RangeException 値が範囲外の場合
	 */
	public byte[] _ESC_K(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x4B, (byte) value };
	}

	public static final int PINTER_UNI_DIRECTIONAL = 0;
	public static final int PINTER_BI_DIRECTIONAL = 1;

	/**
	 * 単方向印字の指定・解除 (ESC U)
	 * 
	 * @param value
	 * @return 印字コマンドのバイト配列
	 * @throws InvalidValueException
	 */
	public byte[] _ESC_U(int value) throws InvalidValueException {
		if (value != PINTER_UNI_DIRECTIONAL && value != PINTER_BI_DIRECTIONAL) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x4B, (byte) value };
	}

	/**
	 * 印字およびn行の紙送り (ESC d)
	 * 
	 * @param value 0 ～ 255
	 * @return 印字コマンドのバイト配列
	 * @throws RangeException 値が範囲外の場合
	 */
	public byte[] _ESC_d(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x64, (byte) value };
	}

	/**
	 * 印字およびn行の逆方向紙送り (ESC e)
	 * 
	 * @param value 0 ～ 255
	 * @return 印字コマンドのバイト配列
	 * @throws RangeException 値が範囲外の場合
	 */
	public byte[] _ESC_e(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x65, (byte) value };
	}

	public static final int TESTPRINT_PAPER_BASIC = 0;
	public static final int TESTPRINT_PAPER_BASIC2 = 48;
	public static final int TESTPRINT_PAPER_ROLE = 1;
	public static final int TESTPRINT_PAPER_ROLE2 = 2;
	public static final int TESTPRINT_PAPER_ROLE3 = 49;
	public static final int TESTPRINT_PAPER_ROLE4 = 50;

	public static final int TESTPRINT_DUMP = 1;
	public static final int TESTPRINT_STATUS = 2;
	public static final int TESTPRINT_PATTERN = 3;
	public static final int TESTPRINT_LAYOUT = 64;
	public static final int TESTPRINT_DUMP2 = 49;
	public static final int TESTPRINT_STATUS2 = 50;
	public static final int TESTPRINT_PATTERN2 = 51;

	/**
	 * テスト印字の実行 (GS ( A)
	 * 
	 * @param paper 用紙タイプ
	 * @param type  テストパターンタイプ
	 * @return 印字コマンドのバイト配列
	 * @throws InvalidValueException 指定された値が無効な場合
	 */
	public byte[] _GS_parenthesis_A(int paper, int type) throws InvalidValueException {
		return new byte[] { (byte) 0x1D, (byte) 0x28, (byte) 0x41, (byte) 0x02, (byte) 0x00, (byte) paper,
				(byte) type };
	}

	/**
	 * リターンホーム
	 * 2026年10月現在、EPSONで生産されている対応機種は「TM-U220II」のみ
	 * 
	 * @return 印字ヘッドを待機位置移動コマンドのバイト配列
	 */
	public byte[] _ESC_lessthan() {
		return new byte[] { (byte) 0x1B, (byte) 0x3C };
	}

}