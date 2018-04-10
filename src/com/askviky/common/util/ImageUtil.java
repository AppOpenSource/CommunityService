package com.askviky.common.util;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageUtil {

	private static final String TAG = ImageUtil.class.getSimpleName();
	
	public static Bitmap getImage(String filePath) {
		Bitmap bmp = null;
		String realPath = getSDCardRoot() + filePath;
		File file = new File(realPath);
		if (file.exists()) {
			bmp = BitmapFactory.decodeFile(realPath);
		}
		return bmp;
	}
	
	public static String getSDCardRoot() {
		String sdRoot = "";
		boolean isSdCardExist = Environment.getExternalStorageState()
				.equals(Environment.MEDIA_MOUNTED);
		if (isSdCardExist) { // 判断SDCard是否存在
			sdRoot = Environment.getExternalStorageDirectory().getAbsolutePath(); // 获取sdcard的根路径
		} else {
			Log.d(TAG, "SD卡不存在");
		}
		return sdRoot;
	}
}
