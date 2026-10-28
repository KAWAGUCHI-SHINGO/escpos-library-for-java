package jp.kawaguchi_shingo.libs.escpos.exception;

public class InvalidValueException extends Exception {
	public InvalidValueException() {
		super("Invalid value.");
	}

	public InvalidValueException(String mesg) {
		super(mesg);
	}

}
