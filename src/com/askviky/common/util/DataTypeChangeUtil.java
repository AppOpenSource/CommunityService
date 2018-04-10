package com.askviky.common.util;

public class DataTypeChangeUtil {
	
	public static String getDisplayUnit(String displayUnit) {
		switch (Integer.parseInt(displayUnit)) {
		case 1:
			return "℃";
		case 2:
			return "%";
		case 3:
			return "kg";
		case 4:
			return "V";
		case 5:
			return "A";
		case 6:
			return "Hz";
		case 7:
			return "kW";
		case 8:
			return "kW·h";
		case 9:
			return "h";
		case 10:
			return "min";
		case 11:
			return "s";
		case 12:
			return "μg/m³";
		case 13:
			return "mg/m³";
		case 14:
			return "km/h";
		case 15:
			return "m";
		case 16:
			return "mm";
		case 17:
			return "℉";
		case 18:
			return "°";
		}
		return null;
	}

	public static String getHexString(byte[] cmd) {
		String hex = "";
		for (int iTmp = 0; iTmp < cmd.length; iTmp++) {
			if (cmd[iTmp] < 0) {
				hex += Integer.toHexString((255 + cmd[iTmp]) + 1);
			} else {
				hex += Integer.toHexString(cmd[iTmp]);
			}
			hex += " ";
		}
		return hex;
	}

	public static int byteToSignedInt(byte[] bytes) {
		int test;
		int a1 = (bytes[0]&0xFF) << 24;
		int a2 = (bytes[1]&0xFF) << 16;
		int a3 = (bytes[2]&0xFF) << 8;
		int a4 = (bytes[3]&0xFF);

		test = a1 | a2 | a3 | a4;
		return test;
	}

	public static byte[] unsignedInteger4ToByte(long value) {
		String hexString = Long.toHexString(value);
		while(hexString.length() < 8) {
			hexString = "0" +hexString ;
		}
		byte[] bytes = hexstringToBytes(hexString);
		return bytes;
	}

	public static byte[] hexstringToBytes(String hexStr) {
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length()/2];
		for (int i= 0 ; i< hexStr.length()/2;i++) {
			int high = Integer.parseInt(hexStr.substring(i*2,i*2+1),16);
			int low = Integer.parseInt(hexStr.substring(i*2+1,i*2+2),16);
			result[i] = (byte)(high * 16 + low);
		}
		return result;
	}

	/**
	 * 将32位整数转换成长度为4的byte数组
	 * @return byte[]
	 * */
	public static byte[] intToByteArray(int s) {
		byte[] targets = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}
}
