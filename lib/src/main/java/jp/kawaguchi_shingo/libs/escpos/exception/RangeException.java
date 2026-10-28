package jp.kawaguchi_shingo.libs.escpos.exception;

public class RangeException extends Exception {
	public RangeException() {
		super("Value outside the range.");
	}

	public RangeException(String mesg) {
		super(mesg);
	}

}
