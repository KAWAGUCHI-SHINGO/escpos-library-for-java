package jp.kawaguchi_shingo.libs.escpos.function;

import jp.kawaguchi_shingo.libs.escpos.command._Print;
import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;
import jp.kawaguchi_shingo.libs.escpos.exception.RangeException;

public class Print {

	public static byte[] cancel() {
		return _Print._CAN();
	}

	public static byte[] printAndEnd() {
		return _Print._ESC_FF();
	}

	public static byte[] printAndFeed(int value) throws RangeException {
		return _Print._ESC_J(value);
	}

	public static byte[] printBufferAndFeed(int value) throws RangeException {
		return _Print._ESC_d(value);
	}

	public static byte[] testDump(int paper) throws InvalidValueException {
		return _Print._GS_parenthesis_A(paper, _Print.TESTPRINT_DUMP);
	}

	public static byte[] testStatus(int paper) throws InvalidValueException {
		return _Print._GS_parenthesis_A(paper, _Print.TESTPRINT_STATUS);
	}

	public static byte[] testPattern(int paper) throws InvalidValueException {
		return _Print._GS_parenthesis_A(paper, _Print.TESTPRINT_PATTERN);
	}

	public static byte[] testLayout(int paper) throws InvalidValueException {
		return _Print._GS_parenthesis_A(paper, _Print.TESTPRINT_LAYOUT);
	}
}
