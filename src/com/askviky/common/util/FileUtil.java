package com.askviky.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

	public FileUtil() { }

	public static DataOutputStream getOutputStream(String saveDir, String outputFile) {
		if ((saveDir == null) || (outputFile == null)) {
			return null;
		}
		BufferedOutputStream bufferedStreamInstance = null;
		try {
			File directory = new File(saveDir);
			if (!directory.exists()) 
				if( !directory.mkdirs()) {
					return null;
				}
			File file = new File(saveDir + "/" + outputFile);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();

			FileOutputStream fos = new FileOutputStream(file);
			bufferedStreamInstance = new BufferedOutputStream(fos);
		} catch (Exception e) {
			return null;
		}
		return new DataOutputStream(bufferedStreamInstance);
	}

	public static DataInputStream getInputStream(String saveDir, String inputFile) {
		if ((saveDir == null) || (inputFile == null)) {
			return null;
		}

		BufferedInputStream bufferedStreamInstance = null;
		try {
			File directory = new File(saveDir);
			if (!directory.exists()) 
				return null;

			File file = new File(saveDir + "/" + inputFile);
			if (!file.exists()) {
				return null;
			}

			FileInputStream fis = new FileInputStream(file);
			bufferedStreamInstance = new BufferedInputStream(fis);
		} catch (Exception e) {
			return null;
		}
		return new DataInputStream(bufferedStreamInstance);
	}

	public static boolean writeAudioData(DataOutputStream dos, short[] data) {
		try {
			for (short s : data) {
				dos.write((byte) (s & 0xFF));
				dos.write((byte) (s >>> 8 & 0xFF));
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static boolean writeAudioData(DataOutputStream dos, char[] data) {
		try {
			for (char s : data) {
				dos.write((byte) (s & 0xFF));
				dos.write((byte) (s >>> 8 & 0xFF));
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static boolean writeAudioData(DataOutputStream dos, byte[] data) {
		try {
			dos.write(data);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static int readAudioData(DataInputStream dis, char[] data, int index, int len) {
		int num = 0;
		if (dis == null || len <= 0) return -1;
		byte[] ba = new byte[len * 2];
		byte[] kk = new byte[len * 2];

		try {	
			num = dis.read(ba, 0, len * 2);
			if (num == -1) return -1;
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < num / 2; i++) {
				char blow, bhigh;
				blow = (char) ((char) ((char) ba[i * 2 + 1 ] & 0xFF) << 8);
				bhigh = (char) ((char) ba[i * 2] & 0xFF);

				data[i] = (char) (bhigh | blow);
				if (i < num - 1)
					sb.append(":");
			}

		} catch (IOException e) {
			return num / 2;
		}
		return num / 2;
	}

	public static int readAudioData(DataInputStream dis, short[] data, int index, int len) {
		int num = 0;
		if (dis == null || len <= 0) return -1;
		byte[] ba = new byte[len * 2];
		byte[] kk = new byte[len * 2];

		try {			
			num = dis.read(ba, 0, len * 2);
			if (num == -1) return -1;
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < num / 2; i++) {
				short blow, bhigh;
				blow = (short) ((short) ((short) ba[i * 2 + 1 ] & 0xFF) << 8);
				bhigh = (short) ((short) ba[i * 2] & 0xFF);

				data[i] = (short) (bhigh | blow);
				if (i < num - 1)
					sb.append(":");
			}
		} catch (IOException e) {
			return num / 2;
		}
		return num / 2;
	}
}
