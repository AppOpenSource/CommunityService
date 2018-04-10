package com.askviky.common.preference;

import java.io.Serializable;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 界面调用：<br>
 * Preferences preferences = Preferences.Factory.getInstance(this); <br>
 * String accountId = preferences.getAccountId();
 */
public interface Preferences {

	public SharedPreferences getSharedPreferences();

	/**
	 * 获取保存的数据
	 */
	public <T> T get(Context context, String key, Class<T> c);

	/**
	 * 保存数据，持久化
	 */
	public boolean save(Context context, String key, Serializable obj);

	public void setAccountId(String accountId);

	public String getAccountId();

	public String getToken();

	public void setToken(String token);

	public static final class Factory {
		public static final Preferences getInstance(Context context) {
			return MemoryPreferences.getInstance(context);
		}
	}
}
