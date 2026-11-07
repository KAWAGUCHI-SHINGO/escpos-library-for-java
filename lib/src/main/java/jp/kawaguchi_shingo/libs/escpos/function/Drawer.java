package jp.kawaguchi_shingo.libs.escpos.function;

import jp.kawaguchi_shingo.libs.escpos.command._Drawer;
import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;

public class Drawer {

	public static byte[] realtimeOpen(int pin) throws InvalidValueException {
		try {
			return _Drawer._DLE_DC4_fn1(pin, 1);
		} catch (InvalidValueException e) {
			throw e;
		}
	}

	public static byte[] open(int pin) throws InvalidValueException {
		try {
			return _Drawer._ESC_p(pin, 1, 2);
		} catch (InvalidValueException e) {
			throw e;
		}
	}
}
