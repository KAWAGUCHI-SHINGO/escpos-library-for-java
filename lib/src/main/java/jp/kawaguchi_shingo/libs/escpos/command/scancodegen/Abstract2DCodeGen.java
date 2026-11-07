package jp.kawaguchi_shingo.libs.escpos.command.scancodegen;

public abstract class Abstract2DCodeGen {
	protected byte[] baseCommand(int cn, int fn, byte[] data) {
		// pL, pH は cn(1バイト) + fn(1バイト) + データ長の合計
		int totalLen = 2 + (data != null ? data.length : 0);
		byte pL = (byte) (totalLen & 0xFF);
		byte pH = (byte) ((totalLen >> 8) & 0xFF);

		int dataLen = data != null ? data.length : 0;
		byte[] result = new byte[4 + totalLen];
		result[0] = (byte) 0x1D; // GS
		result[1] = (byte) 0x28; // (
		result[2] = (byte) 0x6B; // k
		result[3] = pL;
		result[4] = pH;
		result[5] = (byte) cn;
		result[6] = (byte) fn;

		if (dataLen > 0) {
			System.arraycopy(data, 0, result, 7, dataLen);
		}
		return result;
	}

}
