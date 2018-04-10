package com.askviky.common.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class CallUtil {

	/**
	 * 拨号打电话
	 */
	public static void dial(Activity activity, String number) {
		if (number == null || number.length() < 1) return;
		
		Uri uri = Uri.parse("tel:"+number);
		Intent intent = new Intent(Intent.ACTION_CALL, uri);
		activity.startActivity(intent);
	}
}
