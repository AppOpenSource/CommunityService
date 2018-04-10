package com.askviky.common.zxing;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.askviky.common.GlobalConstant;
import com.askviky.common.view.CustomToast;
import com.askviky.common.zxing.camera.CameraManager;
import com.askviky.common.zxing.decoding.CaptureActivityHandler;
import com.askviky.common.zxing.decoding.InactivityTimer;
import com.askviky.common.zxing.view.ViewfinderView;
import com.askviky.communityservice.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class CaptureActivity extends Activity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private Context context;
	String codetype = "";
	private String result;
	//	private View codeOk;
	private View back;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		//		 /*set it to be no title*/
		//        requestWindowFeature(Window.FEATURE_NO_TITLE);          
		//        /*set it to be full screen*/
		//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
		//        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_capture);
		context = this;
		setScanWidthHeight();
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		//		codeOk = (View) findViewById(R.id.codeOk);
		back = (View) findViewById(R.id.codereturn);
		back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}

	private void setScanWidthHeight() {
		// 设置扫描的大小
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;
		int width = widthPixels < heightPixels ? widthPixels : heightPixels;
		if (width <= 0)
			width = 320;
		CameraManager.MIN_FRAME_WIDTH = (int) (width * 3 / 5);
		CameraManager.MIN_FRAME_HEIGHT = (int) (width * 3 / 5);
		CameraManager.MAX_FRAME_WIDTH = (int) (width * 3 / 5);
		CameraManager.MAX_FRAME_HEIGHT = (int) (width * 3 / 5);	
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			Toast mtoast = Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.camera_open_failed), Toast.LENGTH_LONG);
			mtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
			mtoast.show();
			onBackPressed();
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(CaptureActivity.this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		codetype = obj.getBarcodeFormat().getName();
		playBeepSoundAndVibrate();
		result = obj.getText();
		if (TextUtils.isEmpty(result)) {
			CustomToast.builder.display(getString(R.string.scan_failed));
		} else {
			CustomToast.builder.display(result);
			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString(GlobalConstant.QR_CODE_RESULT_KEY, result);
			resultIntent.putExtras(bundle);
			setResult(RESULT_OK, resultIntent);
		}
		finish();
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	};

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}