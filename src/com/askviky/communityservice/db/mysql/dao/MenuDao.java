package com.askviky.communityservice.db.mysql.dao;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.askviky.communityservice.db.mysql.bean.Menu;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class MenuDao extends BaseDaoImpl<Menu, String> {
	
	private static final String TAG = MenuDao.class.getSimpleName();
	private Dao<Menu, String> mMenuDao;
	private ConnectionSource mConnSource;
	
	public MenuDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Menu.class);
		mConnSource = connectionSource;
		mMenuDao = DaoManager.createDao(mConnSource, Menu.class);
	}
	
	public List<Menu> queryAllMenu() throws SQLException {
		List<Menu> list = null;
		if (mMenuDao != null) {
			list = mMenuDao.queryForAll();
		}
		for (Menu menu : list) {
			Log.d(TAG, "menu.getName(): "+menu.getName());
		}
		return list;
	}
	
	public Menu get(int id) throws SQLException {
		if (mMenuDao == null) return null;
		
		return mMenuDao.queryForId(id+"");
	}
	
	public List<Menu> listByCookbookId(int cookbookId) throws SQLException {
		if (mMenuDao == null) return null;
		
		return mMenuDao.queryBuilder().where().eq("cookbook_id", cookbookId).query();
    }
	
	public List<Menu> listByConditions(int pLow, int pHigh, double costLow, double costHigh, String style) throws SQLException {
		if (mMenuDao == null) return null;
		
		QueryBuilder<Menu, String> builder = mMenuDao.queryBuilder();
		builder.where().between("people", pLow, pHigh);
			/*.and().between("price", costLow, costHigh)
			.and().eq("style", "");*/
		return builder.query();
	}
}
