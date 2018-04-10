package com.askviky.common.util;

import android.text.Html;

public class StringUtil {

	public CharSequence htmlParse(float txt) {
		return Html.fromHtml("￥" + "<font color='#333333'>" + txt + "</font>"+"x" );
	}

	public static void clearBuffer(int[] data, int len) {
		for(int i = 0; i < len; i++) {
			data[i] = 0;
		}		
	}

	public static void clearBuffer(byte[] data, int len) {
		for(int i = 0; i < len; i++) {
			data[i] = 0;
		}		
	}

	public static void clearBuffer(short[] data, int len) {
		for(int i = 0; i < len; i++) {
			data[i] = 0;
		}		
	}

	public static String byteArray2String(byte[] arr, int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			String hex = Integer.toHexString(arr[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toLowerCase() + " ");
		}
		return sb.toString();
	}

	public static String byteArrayToString(byte[] input, int start, int len) {
		byte[] temp = new byte[1000];

		System.arraycopy(input, start, temp, 0, len);
		String s = byteArray2String(temp, len);
		return s;
	}

	public static void clearBuffer(char[] data, int len) {
		for(int i = 0; i < len; i++) {
			data[i] = 0;
		}		
	}

	public static String intArray2String(int[] arr) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++) { 
			sb.append(Integer.toHexString(arr[i]));
			if (i < arr.length - 1)
				sb.append(":");
		}
		return sb.toString(); 
	}

	public static String charArray2String(char[] arr) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++) { 
			sb.append(Integer.toHexString(arr[i]));
			if (i < arr.length - 1)
				sb.append(":");
		}
		return sb.toString(); 
	}
}
