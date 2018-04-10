package com.askviky.common.preference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 文件存储
 */
public class FilePreferences implements Preferences {

	private static final String PREFERENCE_NAME = "CS_Preferences";
	private static final String PARAM_USER_ACCOUNT = "account_name";
	private static final String PARAM_USER_TOKEN = "user_token";

	private static final String PARAM_USER_PHONE = "user_phone";
	private static final String PARAM_USER_NICKNAME = "user_nickname";
	private static final String PARAM_USER_EMAIL = "user_email";
	private static final String PARAM_USER_ICON = "user_icon";

	private static Preferences me;
	private static SharedPreferences sharedPreferences;

	protected FilePreferences(Context context) {
		sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	public static synchronized Preferences getInstance(Context context) {
		if (me == null) {
			me = new FilePreferences(context.getApplicationContext());
		}
		return me;
	}

	@Override
	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Context context, String key, Class<T> c) {
		synchronized (key) {
			ObjectInputStream in = null;
			try {
				File file = context.getFileStreamPath(key);
				in = new ObjectInputStream(new FileInputStream(file));
				Object o = in.readObject();
				if (o != null && c.isInstance(o)) {
					return (T) o;
				}
			} catch (Exception e) {
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException e) {
				}
			}
			return null;
		}
	}

	@Override
	public boolean save(Context context, String key, Serializable obj) {
		synchronized (key) {
			ObjectOutputStream out = null;
			try {
				File file = context.getFileStreamPath(key);
				out = new ObjectOutputStream(new FileOutputStream(file));
				out.writeObject(obj);
				out.flush();
				return true;
			} catch (Exception e) {
				return false;
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
				}
			}
		}
	}

	@Override
	public void setAccountId(String accountId) {
		getSharedPreferences().edit().putString(PARAM_USER_ACCOUNT, accountId).commit();
	}

	@Override
	public String getAccountId() {
		return getSharedPreferences().getString(PARAM_USER_ACCOUNT, null);
	}

	@Override
	public String getToken() {
		return getSharedPreferences().getString(PARAM_USER_TOKEN, null);
	}

	@Override
	public void setToken(String token) {
		getSharedPreferences().edit().putString(PARAM_USER_TOKEN, token).commit();
	}

	public void setPhoneNum(String phoneNum) {
		getSharedPreferences().edit().putString(PARAM_USER_PHONE, phoneNum).commit();
	}

	public String getPhoneNum() {
		return getSharedPreferences().getString(PARAM_USER_PHONE, null);
	}

	public void setEmail(String email) {
		getSharedPreferences().edit().putString(PARAM_USER_EMAIL, email).commit();
	}

	public String getEmail() {
		return getSharedPreferences().getString(PARAM_USER_EMAIL, null);
	}

	public void setHeadIcon(String iconUrl) {
		getSharedPreferences().edit().putString(PARAM_USER_ICON, iconUrl).commit();
	}

	public String getHeadIcon() {
		return getSharedPreferences().getString(PARAM_USER_ICON, null);
	}

	public void setNickname(String nickname) {
		getSharedPreferences().edit().putString(PARAM_USER_NICKNAME, nickname).commit();
	}

	public String getNickname() {
		return getSharedPreferences().getString(PARAM_USER_NICKNAME, null);
	}

	public void clearInfo() {
		getSharedPreferences().edit().clear().commit();
	}
}
