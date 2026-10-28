package jp.kawaguchi_shingo.libs.escpos.command;

import java.util.Arrays;

import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;
import jp.kawaguchi_shingo.libs.escpos.exception.RangeException;

/**
 * プリンターの制御に関するコマンドを生成するクラス
 */
public class Printer {
	/**
	 * ページモードにおける印字データのキャンセル (CAN)
	 * 
	 * @return キャンセルコマンドのバイト配列
	 */
	public byte[] _CAN() {
		return new byte[] { (byte) 0x18 };
	}

	/**
	 * 初期改行量の設定
	 * 
	 * @return
	 */
	public byte[] _ESC_2() {
		return new byte[] { (byte) 0x1B, (byte) 0x32 };
	}

	/**
	 * 改行量の設定
	 * 
	 * @param value 0 ～ 255
	 * @return
	 */
	public byte[] _ESC_3(int value) throws RangeException {
		if (value < 0 || value > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x33, (byte) value };
	}

	/**
	 * スタンダードモードの選択 (ESC S)
	 * ページモードからスタンダードモードへ切り替えます。
	 * 
	 * @return スタンダードモード選択コマンドのバイト配列
	 */
	public byte[] _ESC_S() {
		return new byte[] { (byte) 0x1B, (byte) 0x53 };
	}

	/**
	 * ページモードの選択 (ESC L)
	 * スタンダードモードからページモードへ切り替えます。
	 * 
	 * @return ページモード選択コマンドのバイト配列
	 */
	public byte[] _ESC_L() {
		return new byte[] { (byte) 0x1B, (byte) 0x4C };
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
	 * プリンターの初期化 (ESC @)
	 * 
	 * @return 初期化コマンドのバイト配列
	 */
	public byte[] _ESC_at() {
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
	public byte[] _GS_P(int x, int y) throws RangeException {
		if (x < 0 || x > 255 || y < 0 || y > 255) {
			throw new RangeException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x50, (byte) x, (byte) y };
	}

	public static final int ID_DEVICE = 1;
	public static final int ID_TYPE = 2;
	public static final int ID_VERSION = 3;
	public static final int INFO_TYPE = 33;
	public static final int INFO_DEVICE = 35;

	/**
	 * プリンターIDの送信 (GS i)
	 * 
	 * @param id プリンターID
	 * @return コマンドのバイト配列
	 */
	public byte[] _GS_i(int id) {
		return new byte[] { (byte) 0x1D, (byte) 0x49, (byte) id };
	}

	/**
	 * 印字出力信号の選択 / 用紙センサーの有効化 (ESC c 3)
	 * 
	 * @param sensorMask センサー指定のビットマスク (0 ～ 255)
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 指定されたマスクが不正な場合
	 */
	public byte[] _ESC_c_3(int sensorMask) throws InvalidValueException {
		if (sensorMask < 0 || sensorMask > 255) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x63, (byte) 0x33, (byte) sensorMask };
	}

	/**
	 * 用紙ニアエンドによる印字停止の設定 (ESC c 4)
	 * 
	 * @param stopMask 停止条件を指定するビットマスク (0 ～ 255)
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 指定されたマスクが不正な場合
	 */
	public byte[] _ESC_c_4(int stopMask) throws InvalidValueException {
		if (stopMask < 0 || stopMask > 255) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x63, (byte) 0x34, (byte) stopMask };
	}

	/**
	 * バッファークリア (DLE DC4 fn8)
	 * 
	 * @return バッファクリアコマンドのバイト配列
	 */
	public byte[] _DLE_DC4_fn8() {
		return new byte[] { (byte) 0x10, (byte) 0x14, (byte) 0x08, (byte) 0x01, (byte) 0x03, (byte) 0x14, (byte) 0x01,
				(byte) 0x06, (byte) 0x02, (byte) 0x08 };
	}

	/**
	 * 電源オフ処理の実行 (DLE DC4 fn2)
	 * 
	 * @return 電源オフコマンドのバイト配列
	 */
	public byte[] _DLE_DC4_fn2() {
		return new byte[] { (byte) 0x10, (byte) 0x14, (byte) 0x02, (byte) 0x01, (byte) 0x08 };
	}

}