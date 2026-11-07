package jp.kawaguchi_shingo.libs.escpos.function;

import jp.kawaguchi_shingo.libs.escpos.command._Layout;
import jp.kawaguchi_shingo.libs.escpos.exception.RangeException;

public class Layout {

	public static byte[] nl() {
		return _Layout._LF();
	}

	public static byte[] cr() {
		return _Layout._CR();
	}

	public static byte[] hrizontalTab() {
		return _Layout._HT();
	}

	public static byte[] horizontalTabPos(int[] positions) {
		return _Layout._ESC_D(positions);
	}

	public static byte[] absolutePos(int nL, int nH) throws RangeException {
		return _Layout._ESC_$(nL, nH);
	}

	public static byte[] relationPos(int nL, int nH) {
		return _Layout._ESC_backslash(nL, nH);
	}

	public static byte[] alignLeft() {
		return _Layout._ESC_a(_Layout.JUSTIFY_LEFT);
	}

	public static byte[] alignCenter() {
		return _Layout._ESC_a(_Layout.JUSTIFY_CENTER);
	}

	public static byte[] alignRight() {
		return _Layout._ESC_a(_Layout.JUSTIFY_RIGHT);
	}

	public static byte[] leftMargin0() {
		return _Layout._GS_L(0, 0);
	}

	public static byte[] leftMargin(int nL, int nH) {
		return _Layout._GS_L(nL, nH);
	}

	public static byte[] printArea(int nL, int nH) {
		return _Layout._GS_W(nL, nH);
	}

	public static byte[] printDirection(int direction) {
		return _Layout._ESC_T(direction);
	}

	public static byte[] pageModeArea(int xL, int xH, int yL, int yH, int dxL, int dxH, int dyL, int dyH) {
		return _Layout._ESC_W(xL, xH, yL, yH, dxL, dxH, dyL, dyH);
	}

	public static byte[] pageModeDirection(int direction) {
		return _Layout._GS_T(direction);
	}

	public static byte[] pageModePointZero() {
		return _Layout._GS_$(0, 0);
	}

	public static byte[] setPageModeZeroPoint(int nL, int nH) {
		return _Layout._GS_$(nL, nH);
	}

	public static byte[] pageModeRelationVerticalPos(int nL, int nH) {
		return _Layout._GS_backslash(nL, nH);
	}

}
