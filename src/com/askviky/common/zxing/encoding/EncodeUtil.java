package com.askviky.common.zxing.encoding;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import android.graphics.Bitmap;

public class EncodeUtil {

	private static final int PADDING_SIZE_MIN = 20;
	public static Bitmap createQRImage(String url,int QR_WIDTH , int QR_HEIGHT ) throws UnsupportedEncodingException {  

		Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
		Bitmap bitmapQR = null;
		try {
			//判断URL合法性
			if (url == null || "".equals(url) || url.length() < 1) {
				return bitmap;
			}
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

			//图像数据转换，使用了矩阵转换
			BitMatrix bitMatrix = new MultiFormatWriter()
			.encode(new String(url.getBytes(), "ISO-8859-1"),
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			int[] pixels = new int[width * height];

			boolean isFirstBlackPoint = false;
			int startX = 0;
			int startY = 0;
			//下面这里按照二维码的算法，逐个生成二维码的图片，
			//两个for循环是图片横列扫描的结果
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (bitMatrix.get(x, y)) {
						if (isFirstBlackPoint == false) {
							isFirstBlackPoint = true; 
							startX = x;
							startY = y;
						}
						pixels[y * width + x] = 0xff000000;
					} else {
						pixels[y * width + x] = 0xffffffff;
					}

				}
			}
			//生成二维码图片的格式，使用ARGB_8888
			bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, height, 0, 0, width, height);
			// 剪切中间的二维码区域，减少padding区域
			if (startX <= PADDING_SIZE_MIN) return bitmap;
			int x1 = startX - PADDING_SIZE_MIN;
			int y1 = startY - PADDING_SIZE_MIN;
			if (x1 < 0 || y1 < 0) return bitmap;
			int w1 = width - x1 * 2;
			int h1 = height - y1 * 2;
			bitmapQR = Bitmap.createBitmap(bitmap, x1, y1, w1, h1); 
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return bitmapQR;
	}			
}