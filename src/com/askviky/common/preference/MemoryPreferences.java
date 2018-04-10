package com.askviky.common.preference;

import android.content.Context;

/**
 * 内存存储，如果只需存内存，直接重写具体方法即可
 */
public class MemoryPreferences extends FilePreferences {

	private static Preferences me;

	protected MemoryPreferences(Context context) {
		super(context);		
	}

	public static synchronized final Preferences getInstance(Context context) {
		if (me == null) {
			me = new MemoryPreferences(context.getApplicationContext());
		}
		return me;
	}

}
