package com.askviky.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.media.session.MediaSessionCompat.Token;

import com.askviky.common.preference.MemoryPreferences;
import com.askviky.common.preference.Preferences;

public class UserUtil {
	
	static MemoryPreferences preferences;
	
	public UserUtil(Context context) {
		preferences = (MemoryPreferences) Preferences.Factory.getInstance(context);
	}
	
	// start edit by yubai @2016-1-28
	public static void saveUserMsg(Context context, String userid, String token) {		
		preferences = (MemoryPreferences) Preferences.Factory.getInstance(context);
		preferences.setAccountId(userid);
		preferences.setToken(token);
	}
	
	public static Token updateToken(Context context) {
		//TODO
		/*Token token = getCurrentToken(context.getApplicationContext());
		if (null != token) {
			if (null!= token.getUser() && null != token.getToken()) {
				preferences.setToken(token.getToken());
			} else {
				preferences.clearInfo();
				preferences = null;
				return null;
			}
		}*/
		return null;
	}
	
	public static void updateUserInfo(Context context) {
		Token token = updateToken(context);
		if (null != token) {
			/*TODO
			 * HttpMethodUtil.getUserInfo(getInfoHandler, "ssoValidateAuth",
					token.getUser().accountName, token.getToken());*/
		}
	}
	
	private static Handler getInfoHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bundle bun = msg.getData();
				String result = bun.getString("result");
		
				JSONObject jsonOb = null;
				try {
					jsonOb = UserUtil.getJSON(result);
				} catch (JSONException e) {
					e.printStackTrace();
				}
		
				try {
					String createTime = "", email = "", id = "", nickname = "",
							headpic = "", phone = "", status = "", type = "",
							updateTime = "", username = "", sex = "", birthday = "";
					
				   /**
					*    目前用不到的信息
					*	//服务器指定的User Id
					*	if (jsonOb.has("id")) {
					*	id = jsonOb.getString("id");
					*	}
					*	//login status? to be considered
					*	if (jsonOb.has("status")) {
					*		status = jsonOb.getString("status");
					*	}
					*	if (jsonOb.has("type")) {
					*		type = jsonOb.getString("type");
					*	}
					*	if (jsonOb.has("sex")) {
					*		updateTime = jsonOb.getString("sex");
					*	}
					*	if (jsonOb.has("createTime")) {
					*		updateTime = jsonOb.getString("createTime");
					*	}
					*	if (jsonOb.has("birthday")) {
					*		updateTime = jsonOb.getString(" birthday");
					*	}
					*/
					
					if (jsonOb.has("username")) {
						username = jsonOb.getString("username");
					}
					if (jsonOb.has("email")) {
						email = jsonOb.getString("email");
					}
					if (jsonOb.has("headpic")) {
						headpic = jsonOb.getString("headpic");
					}
					if (jsonOb.has("nickname")) {
						nickname = jsonOb.getString("nickname");
						/*if(nickname.isEmpty()){
							nickname = jsonOb.getString("username");
						}*/
					}
					if (jsonOb.has("phone")) {
						phone = jsonOb.getString("phone");
					}
					
					setEmail(email);
					setHeadIcon(headpic);
					setNickname(nickname);
					setPhoneNum(phone);
					preferences.setAccountId(username);

				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	};
	
	public static boolean isStateLogin() {
		if (preferences != null) {
			return true;
		}
		return false;
	}
	
	//accountId token不提供set方法
	public static String getAccountId() {
		return preferences.getAccountId();
	}
	
	public static String getToken() {
		return preferences.getToken();
	}
	
	public static void setPhoneNum(String phoneNum) {
		preferences.setPhoneNum(phoneNum);
	}
	
	public static String getPhoneNum() {
		return preferences.getPhoneNum();
	}
	
	public static void setEmail(String email) {
		preferences.setEmail(email);
	}
	
	public static String getEmail() {
		return preferences.getEmail();
	}
	
	public static void setNickname(String nickname) {
		String nick = null;
		try {
			nick = URLDecoder.decode(nickname, "UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		preferences.setNickname(nick);
	}
	
	public static String getNickname() {
		return preferences.getNickname();
	}
	
	public static void setHeadIcon(String iconUrl) {
		preferences.setHeadIcon(iconUrl);
	}
	
	public static String getHeadIcon() {
		return preferences.getHeadIcon();
	}
	
	public static void clearUserInfo() {
		preferences.clearInfo();
		preferences = null;
	}
	
	/**
	 * 判断是否手机号码
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		Pattern p1 = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m1 = p1.matcher(phone);
		boolean flag = m1.matches();
		return flag;
	}
	/**
	 * 判断是否邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		Pattern p1 = Pattern
				.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		Matcher m1 = p1.matcher(email);
		boolean flag = m1.matches();
		return flag;
	}
	
	/**
	 * 判断网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
                .getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {   
        } else {
        	//如果仅仅是用来判断网络连接
        	//则可以使用 cm.getActiveNetworkInfo().isAvailable();  
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    }

	/**
	 * 判断密码是否全是数字
	 * @param password
	 * @return
	 */
	public static boolean isPasswordDigit(String password) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(password);
		boolean flag = m.matches();
		return flag;
	}

	/**
	 * 判断密码是否全是字母
	 * @param password
	 * @return
	 */
	public static boolean isPasswordReg(String password) {
		Pattern p1 = Pattern.compile("[a-zA-Z]+");
		Matcher m1 = p1.matcher(password);
		boolean flag = m1.matches();
		return flag;
	}
	
	public static JSONObject getJSON(String sb) throws JSONException {
		return new JSONObject(sb);
	}
	
}
