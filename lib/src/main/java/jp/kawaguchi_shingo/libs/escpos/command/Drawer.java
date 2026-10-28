package jp.kawaguchi_shingo.libs.escpos.command;

import java.util.Arrays;

import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;

public class Drawer {
	public static final int DRAWER_PIN_2 = 0;
	public static final int DRAWER_PIN_5 = 1;

	public static final int PULSE_TIME_MIN = 1;
	public static final int PULSE_TIME_MAX = 8;

	/**
	 * 指定パルスのリアルタイム出力(キャッシュドロワー開放)
	 * 
	 * @param pin  0,1
	 * @param time 1~8
	 * @return
	 * @throws InvalidValueException
	 */
	public byte[] _DLE_DC4_fn1(int pin, int time) throws InvalidValueException {
		int[] allowedFn = { DRAWER_PIN_2, DRAWER_PIN_5 };
		if (!Arrays.stream(allowedFn).anyMatch(num -> num == pin) || time < PULSE_TIME_MIN || time > PULSE_TIME_MAX) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x10, (byte) 0x14, (byte) 0x01, (byte) pin, (byte) time };
	}

	public static final int DRAWER_PIN_2b = 48;
	public static final int DRAWER_PIN_5b = 49;

	/**
	 * 指定パルスの発生
	 * 
	 * @param pin
	 * @param on
	 * @param off
	 * @return
	 * @throws InvalidValueException
	 */
	public byte[] _ESC_p(int pin, int on, int off) throws InvalidValueException {
		int[] allowedFn = { DRAWER_PIN_2, DRAWER_PIN_2b, DRAWER_PIN_5, DRAWER_PIN_5b };
		if (!Arrays.stream(allowedFn).anyMatch(num -> num == pin) ||
				(on < 0 || on > 255) | (off < 0 || off > 255) || on >= off) {
			throw new InvalidValueException();
		}
		return new byte[] { (byte) 0x1B, (byte) 0x70, (byte) pin, (byte) on, (byte) off };
	}
}
