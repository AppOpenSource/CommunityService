/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.askviky.common.zxing.view;

import java.util.Collection;
import java.util.HashSet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.askviky.common.zxing.camera.CameraManager;
import com.askviky.communityservice.R;
import com.google.zxing.ResultPoint;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

	private static final int[] SCANNER_ALPHA = { 0, 64, 128, 192, 255, 192, 128, 64 };
	private static final long ANIMATION_DELAY = 100L;
	private static final int OPAQUE = 0xFF;
	private static final int MIDDLE_LINE_PADDING = 5;
	private int MIDDLE_LINE_WIDTH = 3;
	private String scanTip = "将扫描条码放入框内,即可自动扫描";
	private final Paint paint;
	private Bitmap resultBitmap;
	private final int maskColor;
	private final int resultColor;
	private final int frameColor;
	private final int laserColor;
	private final int resultPointColor;
	private int scannerAlpha;
	private Collection<ResultPoint> possibleResultPoints;
	private Collection<ResultPoint> lastPossibleResultPoints;
	private Context context;
	private float density;
	private int ScreenRate;
	private int slideTop;
	private int slideBottom;
	private int speedDistacne=5;
	private String lineColorStr;
	private boolean isFirst=false;


	// This constructor is used when the class is built from an XML resource.
	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		density = context.getResources().getDisplayMetrics().density;
		this.ScreenRate = (int) (20.0F * density);
		// Initialize these once for performance rather than calling them every
		// time in onDraw().
		paint = new Paint();
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.viewfinder_mask);
		resultColor = resources.getColor(R.color.result_view);
		frameColor = resources.getColor(R.color.viewfinder_frame);
		laserColor = resources.getColor(R.color.result_view);// viewfinder_laser);
		resultPointColor = resources.getColor(R.color.possible_result_points);
		scannerAlpha = 0;
		possibleResultPoints = new HashSet<ResultPoint>(5);
	}

	public static int dip2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	@Override
	public void onDraw(Canvas canvas) {
		Rect frame = CameraManager.get().getFramingRect();
		if (frame == null) {
			return;
		}
		if(!this.isFirst) {
			this.isFirst = true;
			this.slideTop = frame.top;
			this.slideBottom = frame.bottom;
		}
		int width = canvas.getWidth();
		int height = canvas.getHeight();

		// Draw the exterior (i.e. outside the framing rect) darkened
		paint.setColor(resultBitmap != null ? resultColor : maskColor);
		canvas.drawRect(0, 0, width, frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
		canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
		canvas.drawRect(0, frame.bottom + 1, width, height, paint);

		if (resultBitmap != null) {
			// Draw the opaque result bitmap over the scanning rectangle
			paint.setAlpha(OPAQUE);
			canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
		} else {

			// Draw a two pixel solid black border inside the framing rect
			paint.setColor(frameColor);
			// canvas.drawRect(frame.left, frame.top, frame.right + 1,
			// frame.top + 2, paint);
			// canvas.drawRect(frame.left, frame.top + 2, frame.left + 2,
			// frame.bottom - 1, paint);
			// canvas.drawRect(frame.right - 1, frame.top, frame.right + 1,
			// frame.bottom - 1, paint);
			// canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1,
			// frame.bottom + 1, paint);
			//
			// // Draw a red "laser scanner" line through the middle to show
			// // decoding is active
			// paint.setColor(laserColor);
			// paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
			// scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
			// int middle = frame.height() / 2 + frame.top;
			// canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1,
			// middle + 2, paint);
			canvas.drawRect((float) frame.left, (float) frame.top, (float) (frame.left + this.ScreenRate),
					(float) (frame.top + 8), this.paint);
			canvas.drawRect((float) frame.left, (float) frame.top, (float) (frame.left + 8),
					(float) (frame.top + this.ScreenRate), this.paint);
			canvas.drawRect((float) (frame.right - this.ScreenRate), (float) frame.top, (float) frame.right,
					(float) (frame.top + 8), this.paint);
			canvas.drawRect((float) (frame.right - 8), (float) frame.top, (float) frame.right,
					(float) (frame.top + this.ScreenRate), this.paint);
			canvas.drawRect((float) frame.left, (float) (frame.bottom - 8), (float) (frame.left + this.ScreenRate),
					(float) frame.bottom, this.paint);
			canvas.drawRect((float) frame.left, (float) (frame.bottom - this.ScreenRate), (float) (frame.left + 8),
					(float) frame.bottom, this.paint);
			canvas.drawRect((float) (frame.right - this.ScreenRate), (float) (frame.bottom - 8), (float) frame.right,
					(float) frame.bottom, this.paint);
			canvas.drawRect((float) (frame.right - 8), (float) (frame.bottom - this.ScreenRate), (float) frame.right,
					(float) frame.bottom, this.paint);

			// 绘制中间的线,每次刷新界面，中间的线往下移动SPEEN_DISTANCE
			slideTop += this.speedDistacne;
			if (slideTop >= frame.bottom) {
				slideTop = frame.top;
			}
			canvas.drawRect((float) (frame.left + 5), (float) (this.slideTop - this.MIDDLE_LINE_WIDTH / 2),
					(float) (frame.right - 5), (float) (this.slideTop + this.MIDDLE_LINE_WIDTH / 2), this.paint);
			// 画扫描框下面的字
			this.paint.setColor(-1);
			this.paint.setTextSize(16.0F * density);
			this.paint.setAlpha(64);
			this.paint.setTypeface(Typeface.create("System", 1));
			this.paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(this.scanTip, (float) ((frame.left + frame.right) / 2.0),
					(float) frame.bottom + 30.0F * density, this.paint);

			Collection<ResultPoint> currentPossible = possibleResultPoints;
			Collection<ResultPoint> currentLast = lastPossibleResultPoints;
			if (currentPossible.isEmpty()) {
				lastPossibleResultPoints = null;
			} else {
				possibleResultPoints = new HashSet<ResultPoint>(5);
				lastPossibleResultPoints = currentPossible;
				paint.setAlpha(OPAQUE);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentPossible) {
					canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 6.0f, paint);
				}
			}
			if (currentLast != null) {
				paint.setAlpha(OPAQUE / 2);
				paint.setColor(resultPointColor);
				for (ResultPoint point : currentLast) {
					canvas.drawCircle(frame.left + point.getX(), frame.top + point.getY(), 3.0f, paint);
				}
			}

			// Request another update at the animation interval, but only
			// repaint the laser line,
			// not the entire viewfinder mask.
			postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);
		}
		//		if (textSize == 0) {
		//			textSize = dip2px(context, 14);
		//		}
		//		paint.setAntiAlias(true);
		//		paint.setTextSize(textSize);
		//		paint.setColor(textColor);
		//		if (distence == 0) {
		//			String ss = "请将一维码或二维码放入框内,即可自动扫描";
		//			float[] array = new float[ss.length()];
		//			paint.getTextWidths(ss, array);
		//			float f = 0f;
		//			for (int i = 0; i < array.length; i++) {
		//				f += array[i];
		//			}
		//			distence = (int) ((width - f) / 2);
		//		}
		//		canvas.drawText("请将一维码或二维码放入框内,即可自动扫描", distence, frame.top - 20, paint);
	}

	int distence = 0;
	int textSize = 0;

	public void drawViewfinder() {
		resultBitmap = null;
		invalidate();
	}

	private int textColor = Color.parseColor("#c5c5c5");

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 * 
	 * @param barcode
	 *            An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		possibleResultPoints.add(point);
	}

	public void setLineColorStr(String lineColorStr) {
		this.lineColorStr = lineColorStr;
	}

	public void setScanTip(String scanTip) {
		this.scanTip = scanTip;
	}

	public void setSpeedDistacne(int speedDistacne) {
		this.speedDistacne = speedDistacne;
	}	
}
