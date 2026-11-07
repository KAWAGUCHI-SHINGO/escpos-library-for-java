package jp.kawaguchi_shingo.libs.escpos.function;

import jp.kawaguchi_shingo.libs.escpos.command._Paper;
import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;

public class Paper {

	public static byte[] panelSwitch(int s) throws InvalidValueException {
		return _Paper._ESC_c_5(s);
	}

	public static byte[] sensor(int sensorMask) throws InvalidValueException {
		return _Paper._ESC_c_3(sensorMask);
	}

	public static byte[] nearEnd(int sensorMask) throws InvalidValueException {
		return _Paper._ESC_c_4(sensorMask);
	}

	public static byte[] fullCut() throws InvalidValueException {
		return _Paper._GS_V(_Paper.PAPER_CUT_FULL);
	}

	public static byte[] partialCut() throws InvalidValueException {
		return _Paper._GS_V(_Paper.PAPER_CUT_PARTIAL);
	}

	public static byte[] feedFullCut(int n) throws InvalidValueException {
		return _Paper._GS_V_feed(_Paper.PAPER_FEED_CUT_FULL, n);
	}

	public static byte[] feedPartialCut(int n) throws InvalidValueException {
		return _Paper._GS_V_feed(_Paper.PAPER_FEED_CUT_PARTIAL, n);
	}

}
