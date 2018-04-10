package com.askviky.communityservice.db.mysql.dao;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.askviky.communityservice.db.mysql.bean.Cookbook;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class CookbookDao extends BaseDaoImpl<Cookbook, String> {
	
	private static final String TAG = CookbookDao.class.getSimpleName();
	private Dao<Cookbook, String> mCookbookDao;
	private ConnectionSource mConnSource;
	
	public CookbookDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Cookbook.class);
		mConnSource = connectionSource;
		mCookbookDao = DaoManager.createDao(mConnSource, Cookbook.class);
	}
	
	public List<Cookbook> queryAllCookbook() throws SQLException {
		List<Cookbook> list = null;
		if (mCookbookDao != null) {
			list = mCookbookDao.queryForAll();
		}
		for (Cookbook cookbook : list) {
			Log.d(TAG, "cookbook.getProfile(): "+cookbook.getProfile());
		}
		return list;
	}
	
	/*
	 * *********** just for test >>>
	 */
	public void performDBOpt(ConnectionSource conn) throws SQLException {
		mCookbookDao = DaoManager.createDao(conn, Cookbook.class);
		List<Cookbook> list = mCookbookDao.queryForAll();
		
		System.out.println("List of objects saved in DB:");
		for (Cookbook cookbook : list) {
			System.out.println(cookbook.getProfile());
		}
	}
	
	@SuppressWarnings("unused")
	private void createTable(ConnectionSource connectionSource) throws SQLException {
		TableUtils.createTableIfNotExists(connectionSource, Cookbook.class);
		mCookbookDao = DaoManager.createDao(connectionSource, Cookbook.class);
		
		Cookbook cb = new Cookbook();
		cb.setProfile("大宅门");
		cb.setStyle("京城四方");
		mCookbookDao.create(cb);
	}
	/*
	 * <<< just for test *************
	 */
}
