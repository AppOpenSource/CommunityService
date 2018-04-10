package com.askviky.communityservice.db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.SQLException;
import android.util.Log;

import com.askviky.communityservice.common.GlobalConstant;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

/**
 * 没有使用ORMLite框架，暂时用不上该类
 * @author weiqi.huang
 *
 */
public class MySQL {

	private static final String TAG = MySQL.class.getSimpleName();
	private static Connection mConnection = null;
	private static ResultSet mResultSet = null;
	private static Statement mState = null;
	private PreparedStatement mPreparedStatement = null;

	public static void initDBConn() {
		try {
			Log.d(TAG, "connDB()");
			Class.forName("com.mysql.jdbc.Driver");	
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String url = "jdbc:mysql://"+GlobalConstant.SERVER_IP+":3306/"
					+GlobalConstant.DB_NAME+"?user="+GlobalConstant.USER+"&password="
					+GlobalConstant.PASSWD+"&useUnicode=true&characterEncoding=UTF-8";
			mConnection = (Connection) DriverManager.getConnection(url);
			Log.d(TAG, "getConnection: "+mConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Object> queryDB() {
		String sql = "select * from dishes"; // TODO
		try {
			mState = (Statement) mConnection.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			mResultSet = mState.executeQuery(sql);
			Log.d(TAG, "queryDB ResultSet： "+mResultSet);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		StringBuilder str = new StringBuilder();
		ResultSetMetaData resultSetMateData = null;
		try { //获得列的相关信息
			resultSetMateData = (ResultSetMetaData) mResultSet.getMetaData();
		} catch (java.sql.SQLException e1) {
			e1.printStackTrace();
		}

		int columnCount = 0;
		try { //获得列的长度
			columnCount = resultSetMateData.getColumnCount();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		Log.d(TAG, "columnCount ： "+columnCount);

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			while (mResultSet.next()) {
				for (int i = 0; i < columnCount; i++) {
					String columnName = resultSetMateData.getColumnName(i+1) ;
					Object columnValue = mResultSet.getObject(columnName);
					if (columnValue == null) {
						columnValue = "";
					}
					Log.d(TAG, "mResultSet columnValue: " +columnValue);
					str.append(sql + mResultSet.getString(1) + "\n");
					map.put(columnName, columnValue);
				}
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		Log.d(TAG, "mResultSet str: " + str);
		return map;
	}

	public static void exitDBConn() {
		Log.d(TAG, "exitDBConn()");
		try {
			if (mResultSet != null) mResultSet.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		try {
			if (mState != null) mState.close();
		} catch (java.sql.SQLException e1) {
			e1.printStackTrace();
		}

		try {
			if (mConnection != null) mConnection.close();
		} catch (java.sql.SQLException e2) {
			e2.printStackTrace();
		}
	}

	// 查询返回单条记录
	public Map<String, Object> findResult(String sql, List<Object> params) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 1;
		try {
			mPreparedStatement = (PreparedStatement) mConnection.prepareStatement(sql);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		if ((params != null) && (!params.isEmpty())) {
			for (int i = 0; i < params.size(); i++) {
				try {
					mPreparedStatement.setObject(index++, params.get(i));
				} catch (java.sql.SQLException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			mResultSet = mPreparedStatement.executeQuery(); //返回查询结果
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		ResultSetMetaData resultSetMateData = null;
		try {
			resultSetMateData = (ResultSetMetaData) mResultSet.getMetaData(); //获得列的相关信息
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		int colsLen = 0;
		try {
			colsLen = resultSetMateData.getColumnCount(); //获得列的长度
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		try {
			while (mResultSet.next()) {
				for(int i = 0; i < colsLen; i++) {
					String colsName = resultSetMateData.getColumnName(i+1);
					Object colsValue = mResultSet.getObject(colsName);
					if(colsValue == null) {
						colsValue = "";
					}
					map.put(colsName, colsValue);
				}
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
}
