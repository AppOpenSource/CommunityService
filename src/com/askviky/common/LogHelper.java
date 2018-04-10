package com.askviky.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

/**
 * Java实现类似C/C++中的__FILE__、__FUNC__、__LINE__,主要用于日志等功能中
 */
public class LogHelper {
	/**
	 * 打印日志时获取当前的程序文件名,行号,方法名 输出格式为：[FileName | LineNumber | MethodName]
	 */
	public static boolean mDebug = true;

	public static void v(String tag, String info) {
		if (mDebug) {
			Log.v(tag, info);
		}
	}

	public static void i(String tag, String info) {
		if (mDebug) {
			Log.i(tag, info);
		}
	}

	public static void e(String tag, String info) {
		if (mDebug) {
			Log.e(tag, info);
		}
	}

	public static void w(String tag, String info) {
		if (mDebug) {
			Log.w(tag, info);
		}
	}

	public static String getFileLineMethod() {
		StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
		StringBuffer toStringBuffer = new StringBuffer("[").append(traceElement.getFileName()).append(" | ")
				.append(traceElement.getLineNumber()).append(" | ").append(traceElement.getMethodName()).append("]");
		return toStringBuffer.toString();
	}

	// 当前文件名
	public static String __FILE__() {
		StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
		return traceElement.getFileName();
	}

	// 当前方法名
	public static String __FUNC__() {
		StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
		return traceElement.getMethodName();
	}

	// 当前行号
	public static int __LINE__() {
		StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
		return traceElement.getLineNumber();
	}

	// 当前时间
	public static String __TIME__() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return sdf.format(now);
	}

	public static String __TAG__() {
		StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
		return "Func=" + traceElement.getMethodName() + ",File=" + traceElement.getFileName() + ",Line="
		+ traceElement.getLineNumber();
	}
}
