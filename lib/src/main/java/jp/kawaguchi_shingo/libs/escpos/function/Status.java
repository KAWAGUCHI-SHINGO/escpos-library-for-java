package jp.kawaguchi_shingo.libs.escpos.function;

import jp.kawaguchi_shingo.libs.escpos.command._Status;
import jp.kawaguchi_shingo.libs.escpos.exception.InvalidValueException;

public class Status {

	public static byte[] realtimeRequest() throws InvalidValueException {
		int r = _Status.REALTIME_REQ_PAPERFEED &
				_Status.REALTIME_REQ_RESUME_PRINT &
				_Status.REALTIME_REQ_RETURN_ERROR;
		return _Status._DLE_ENQ(r);
	}

	public static byte[] autoStatusOn(int asbMask) throws InvalidValueException {
		return _Status._GS_j(asbMask);
	}

	public static byte[] autoStatusOn() throws InvalidValueException {
		int s = _Status.ASB_DRAWER_KICK &
				_Status.ASB_ERROR &
				_Status.ASB_ONLINE_OFFLINE &
				_Status.ASB_PAPER_ROLL;
		return _Status._GS_a(s);
	}

	public static byte[] autoStatusOff(int asbMask) throws InvalidValueException {
		return _Status._GS_a(asbMask);
	}

	public static byte[] autoStatusOff() throws InvalidValueException {
		int s = _Status.ASB_DRAWER_KICK &
				_Status.ASB_ERROR &
				_Status.ASB_ONLINE_OFFLINE &
				_Status.ASB_PAPER_ROLL;
		return _Status._GS_a(s);
	}

	public static byte[] counter() throws InvalidValueException {
		int t = _Status.ADDITION_COUNTER_IMPACTHEAD &
				_Status.ADDITION_COUNTER_TERMALHEAD &
				_Status.ADDITION_COUNTER_INKJETHEAD &
				_Status.ADDITION_COUNTER_SHUTTLEHEAD &
				_Status.ADDITION_COUNTER_DEVICE &
				_Status.ADDITION_COUNTER_OPTION &
				_Status.ADDITION_COUNTER_TIIME;
		return _Status._GS_g_2(t);
	}

	public static byte[] exAutoStatus() throws InvalidValueException {
		int s = _Status.ASB_DRAWER_KICK &
				_Status.ASB_ERROR &
				_Status.ASB_ONLINE_OFFLINE &
				_Status.ASB_PAPER_ROLL;
		return _Status._GS_j(s);
	}

	public static byte[] getInkStatus() throws InvalidValueException {
		return _Status._GS_r(_Status.STATUS_INK);
	}

	public static byte[] getPaperStatus() throws InvalidValueException {
		return _Status._GS_r(_Status.STATUS_PAPER);
	}

	public static byte[] getDrawerStatus() throws InvalidValueException {
		return _Status._GS_r(_Status.STATUS_DRAWER);
	}
}
