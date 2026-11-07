package jp.kawaguchi_shingo.libs.escpos.exception;

public class InvalidValueException extends Exception {

	public static final int WRONG_VALUE = 0;
	public static final int OUT_OF_RANGE = 1;
	public static final int UNDEFINED_VALUE = 2;

	public InvalidValueException() {
		super("Invalid value.");
	}

	public InvalidValueException(String mesg) {
		super(mesg);
	}

	public InvalidValueException(int type) {
		super(getMessageByType(type));
	}

	private static String getMessageByType(int type) {
		switch (type) {
			case WRONG_VALUE:
				return "Wrong value.";
			case OUT_OF_RANGE:
				return "Out of range.";
			case UNDEFINED_VALUE:
				return "Undefined value.";
			default:
				return "Invalid value.";
		}
	}

}
