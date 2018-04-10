package com.askviky.common.crash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类 来接管程序,并记录 发送错误报告.
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {
	
	/** Debug Log tag */
	public static final String TAG = CrashHandler.class.getSimpleName();
	/** CrashHandler实例 */
	private static CrashHandler INSTANCE;
	/** 程序的Context对象 */
	private Context mContext;
	private boolean mIsAppPath = true;
	private String mAppName = "CommunityService";
	private String mPath = "/mnt/sdcard/";
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	public static final String SDCARD_PATH = Environment.getExternalStorageDirectory() + "";
	public static final String LOG_PATH = SDCARD_PATH + "/CommunityService/log";

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {
	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CrashHandler();
		}
		return INSTANCE;
	}

	/**
	 * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
	 * 
	 * @param ctx
	 */
	public void init(Context ctx, boolean isAppPath, String appName, String Path) {
		mContext = ctx;
		mIsAppPath = isAppPath;
		mAppName = appName;
		mPath = Path;

		if (!mIsAppPath) {
			File destDir = new File(Path);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			destDir = null;
		}
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e(TAG, "uncaughtException, threadName:" + thread.getName()
				+ ", threadId:" + thread.getId());
		handleException(ex);
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mDefaultHandler.uncaughtException(thread, ex);

		/*
		 * android.os.Process.killProcess(android.os.Process.myPid());
		 * System.exit(10);
		 */
	}

	private String getDataTime() {
		String ret = "";
		// Time time = new Time("GMT+8");
		Time time = new Time();
		time.setToNow();
		int year = time.year;
		int month = time.month + 1;
		int day = time.monthDay;
		int minute = time.minute;
		int hour = time.hour;
		int sec = time.second;

		ret = String.format("%04d", year) + String.format("%02d", month)
				+ String.format("%02d", day) + String.format("%02d", hour)
				+ String.format("%02d", minute) + String.format("%02d", sec);
		return ret;
	}

	private String getFileName() {
		String fileName = mAppName + "_crash_";
		// long time = System.currentTimeMillis();
		// fileName += String.valueOf(time) + ".txt";
		fileName += getDataTime() + ".txt";
		return fileName;
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		ex.printStackTrace();
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw, true));
		final String msg = sw.toString();
		
		new Thread() {
			@Override
			public void run() {
				List<String> to = new ArrayList<String>();
				//to.add("askviky2010@gmail.com");
				/*try {
					EmailUtils.sendEmail(to, "Community Service Exception", msg);
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}*/
			}
		}.start();

		new Thread() {
			@Override
			public void run() {
				String fileName = getFileName();
				String fullPath = "";
				if (mIsAppPath) {
					writeFileData(mContext, fileName, msg);
					Log.e(TAG, "log save to App data directory.");
				} else {
					fullPath = mPath + "/" + fileName;
					writeFile2Sdcard(fullPath, msg);
					Log.e(TAG, "log save to App data directory:" + fullPath);
				}
				// Looper.prepare();
				// Looper.loop();
				// Log.e(TAG, msg);
			}
		}.start();
		return true;
	}

	// /data/data/包名/files/test.txt
	public static void writeFileData(Context ctx, String fileName,
			String message) {
		try {
			FileOutputStream fout = ctx.openFileOutput(fileName,
					Context.MODE_WORLD_READABLE);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// /mnt/sdcard/
	public static void writeFile2Sdcard(String fileName, String message) {
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
