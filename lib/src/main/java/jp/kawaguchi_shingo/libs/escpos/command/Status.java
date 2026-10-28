package jp.kawaguchi_shingo.libs.escpos.command;

import java.util.Arrays;
import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;

public class Status {

	// =========================================================================
	// DLE EOT / DLE DC4 定数
	// =========================================================================
	// ステータスタイプ (DLE EOT n)
	public static final int REALTIME_STATUS_PRINTER = 1; // プリンターステータス
	public static final int REALTIME_STATUS_OFFLINE = 2; // オフラインステータス
	public static final int REALTIME_STATUS_ERROR = 3; // エラーステータス
	public static final int REALTIME_STATUS_PAPER = 4; // 用紙センサーシステータス

	// リアルタイム特定送信パラメーター (DLE EOT n m / 機種依存拡張)
	public static final int REALTIME_PARAM_DEFAULT = 1; // 通常の送信パラメーター

	// リアルタイムプリンター復帰・特定要求 (DLE DC4 fn)
	public static final int REALTIME_REQ_RECOVER_PRINTER = 1; // リアルタイムプリンター復帰要求 (エラーからの復帰)
	public static final int REALTIME_REQ_CLEAR_BUFFERS = 2; // リアルタイムバッファクリア要求
	public static final int REALTIME_REQ_CANCEL_PRINT = 8; // ページモードにおける印字データキャンセル要求

	// =========================================================================
	// ESC c 3 / ESC c 4 (用紙センサー制御) 定数
	// =========================================================================
	// 用紙エンドセンサーの選択 (ESC c 3) 用のビットフラグ定数
	public static final int SENSOR_PAPER_NEAR_END_ROLL = 1; // ロール紙ニアエンドセンサー（紙送り側）
	public static final int SENSOR_PAPER_NEAR_END_RIBBON = 2; // ロール紙ニアエンドセンサー（リボン側/機種依存）
	public static final int SENSOR_PAPER_END_ROLL = 4; // ロール紙エンドセンサー
	public static final int SENSOR_PAPER_END_RIBBON = 8; // ロール紙エンドセンサー（リボン側/機種依存）

	// 用紙ニアエンドによる印字停止の選択 (ESC c 4) 用のビットフラグ定数
	public static final int STOP_NEAR_END_ROLL = 1; // ロール紙ニアエンドセンサー（紙送り側）
	public static final int STOP_NEAR_END_RIBBON = 2; // ロール紙ニアエンドセンサー（リボン側/機種依存）

	// =========================================================================
	// GS a / GS j / GS r (ステータス制御) 定数
	// =========================================================================
	// GS a (ASB: 自動ステータス送信) 用のビットフラグ定数
	public static final int ASB_DRAWER_KICK = 1; // ドロアーキックコネクタピン3のステータス
	public static final int ASB_ONLINE_OFFLINE = 2; // オンライン/オフラインステータス
	public static final int ASB_ERROR = 4; // エラーステータス
	public static final int ASB_PAPER_ROLL = 8; // ロール紙センサー（ニアエンド・エンド）ステータス

	// GS r (ステータス送信) 用のパラメータ定数
	public static final int STATUS_DRAWER = 1; // ドロアーキックコネクタピン3のステータスを要求
	public static final int STATUS_DRAWER_2 = 49;
	public static final int STATUS_PAPER = 2; // 用紙センサーのステータスを要求
	public static final int STATUS_PAPER_2 = 50;

	public static final int ADDITION_COUNTER_IMPACTHEAD = 138;
	public static final int ADDITION_COUNTER_TERMALHEAD = 148;
	public static final int ADDITION_COUNTER_INKJETHEAD = 158;
	public static final int ADDITION_COUNTER_SHUTTLEHEAD = 168;
	public static final int ADDITION_COUNTER_DEVICE = 178;
	public static final int ADDITION_COUNTER_OPTION = 188;
	public static final int ADDITION_COUNTER_TIIME = 207;

	public static final int REALTIME_REQ_PAPERFEED = 0;
	public static final int REALTIME_REQ_RESUME_PRINT = 1;
	public static final int REALTIME_REQ_RETURN_ERROR = 2;

	/**
	 * プリンターへのリアルタイム要求 (DLE ENQ)
	 * 
	 * @param req 要求内容
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 指定された要求が不正な場合
	 */
	public byte[] _DLE_ENQ(int req) throws InvalidValueException {
		if (req != REALTIME_REQ_PAPERFEED && req != REALTIME_REQ_RESUME_PRINT && req != REALTIME_REQ_RETURN_ERROR) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x10, (byte) 0x05, (byte) req };
	}

	/**
	 * ステータスのリアルタイム送信
	 * 
	 * @param status REALTIME_STATUS_PRINTER(1) ～ REALTIME_STATUS_PAPER(4)
	 * @return
	 */
	public byte[] _DLE_EOT(int status) throws InvalidValueException {
		if (status < REALTIME_STATUS_PRINTER || status > REALTIME_STATUS_PAPER) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x10, (byte) 0x04, (byte) status };
	}

	/**
	 * ステータスのリアルタイム送信(パラメーター付き)
	 * 
	 * @param status REALTIME_STATUS_PRINTER(1) ～ REALTIME_STATUS_PAPER(4)
	 * @param param  機種ごとのパラメータ値 (0 ～ 255)
	 * @return
	 */
	public byte[] _DLE_EOT(int status, int param) throws InvalidValueException {
		if (status < REALTIME_STATUS_PRINTER || status > REALTIME_STATUS_PAPER) {
			throw new InvalidValueException();
		}
		if (param < 0 || param > 255) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x10, (byte) 0x04, (byte) status, (byte) param };
	}

	/**
	 * 特定ステータスのリアルタイム送信
	 * 
	 * @param fn 要求コマンドのファンクションコード (1, 2, 8 など)
	 * @return
	 */
	public byte[] _DLE_DC4_fn7(int status) throws InvalidValueException {
		int[] allowedFn = { REALTIME_REQ_RECOVER_PRINTER, REALTIME_REQ_CLEAR_BUFFERS, REALTIME_REQ_CANCEL_PRINT };
		if (!Arrays.stream(allowedFn).anyMatch(num -> num == status)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x10, (byte) 0x14, (byte) 0x07, (byte) status };
	}

	/**
	 * 拡張制御マルチコマンド (FS ( e)
	 * 💡 fn（ファンクション）に応じた可変引数を安全に組み立てます
	 * 
	 * @param fn   ファンクションコード (0 ～ 255)
	 * @param data 追加のパラメータ（不要な場合は null）
	 * @return
	 */
	public byte[] _FS_parenthesis_e(int fn, byte[] data) throws InvalidValueException {
		if (fn < 0 || fn > 255) {
			throw new InvalidValueException();
		}
		int dataLen = (data != null) ? data.length : 0;
		int totalLen = 1 + dataLen; // fn(1バイト) + データ長

		byte pL = (byte) (totalLen & 0xFF);
		byte pH = (byte) ((totalLen >> 8) & 0xFF);

		byte[] result = new byte[4 + totalLen];
		result[0] = (byte) 0x1C; // FS
		result[1] = (byte) 0x28; // (
		result[2] = (byte) 0x65; // e
		result[3] = pL;
		result[4] = pH;
		result[5] = (byte) fn;

		if (dataLen > 0) {
			System.arraycopy(data, 0, result, 6, dataLen);
		}
		return result;
	}

	/**
	 * 自動ステータス送信(ASB)の有効・無効化 (GS a)
	 * 
	 * @param asbMask 有効にするステータスのビットマスク (0 ～ 255)
	 * @return
	 */
	public byte[] _GS_a(int asbMask) throws InvalidValueException {
		if (asbMask < 0 || asbMask > 255) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x61, (byte) asbMask };
	}

	/**
	 * メンテナンスカウンターの送信
	 * 
	 * @return
	 */
	public byte[] _GS_g_2(int type) throws InvalidValueException {
		if (type < ADDITION_COUNTER_IMPACTHEAD || type > ADDITION_COUNTER_TIIME) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x67, (byte) 0x32, (byte) 0x00, (byte) type, (byte) 0x00 };
	}

	/**
	 * 自動ステータス通知(ASB)の拡張設定 (GS j)
	 * 
	 * @param value 機種ごとの設定値 (0 ～ 255)
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 値が範囲外の場合
	 */
	public byte[] _GS_j(int value) throws InvalidValueException {
		if (value < 0 || value > 255) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x6A, (byte) value };
	}

	/**
	 * ステータスの送信要求 (GS r)
	 * 
	 * @param type STATUS_DRAWER(1, 49), STATUS_PAPER(2, 50)
	 * @return コマンドのバイト配列
	 * @throws InvalidValueException 指定された値が無効な場合
	 */
	public byte[] _GS_r(int type) throws InvalidValueException {
		int[] allowedTypes = { STATUS_DRAWER, STATUS_DRAWER_2, STATUS_PAPER, STATUS_PAPER_2 };
		if (!Arrays.stream(allowedTypes).anyMatch(num -> num == type)) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1D, (byte) 0x72, (byte) type };
	}

}
