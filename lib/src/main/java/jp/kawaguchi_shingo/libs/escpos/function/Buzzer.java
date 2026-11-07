package jp.kawaguchi_shingo.libs.escpos.function;

import jp.kawaguchi_shingo.libs.escpos.command._Buzzer;

public class Buzzer {

	/**
	 * リアルタイム鳴動
	 * 
	 * @param count
	 * @return
	 */
	public static byte[] rringing(int count) {
		return _Buzzer._DLE_DC4_fn3(_Buzzer.RING, 0, count, 0, 0);
	}

	/**
	 * リアルタイム鳴動の停止
	 * 
	 * @return
	 */
	public static byte[] stop() {
		return _Buzzer._DLE_DC4_fn3(_Buzzer.STOP, 0, 0, 0, 0);
	}

}
