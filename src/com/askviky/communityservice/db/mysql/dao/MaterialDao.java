package com.askviky.communityservice.db.mysql.dao;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.askviky.communityservice.db.mysql.bean.Dish;
import com.askviky.communityservice.db.mysql.bean.Material;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class MaterialDao extends BaseDaoImpl<Material, String> {

	private static final String TAG = MaterialDao.class.getSimpleName();
	private Dao<Material, String> mMaterialDao;
	private ConnectionSource mConnSource;

	public MaterialDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Material.class);
		mConnSource = connectionSource;
		mMaterialDao = DaoManager.createDao(mConnSource, Material.class);
	}

	public List<Material> queryAllMaterial() throws SQLException {
		List<Material> list = null;
		if (mMaterialDao != null) {
			list = mMaterialDao.queryForAll();
		}
		for (Material material : list) {
			Log.d(TAG, "material.getName(): " + material.getName());
		}
		return list;
	}

	public List<Material> listByDishId(int dishId) throws SQLException {
		Log.d(TAG, "listByDishId(): "+dishId);
		List<Material> list = null;
		if (mMaterialDao != null) {
			list = mMaterialDao.queryBuilder().where().eq("dish_id", dishId)
					.query();
			Log.d(TAG, "list: "+list);
			if (list != null) {
				Log.d(TAG, "list.size(): "+list.size());
			}
		}
		for (Material material : list) {
			Log.d(TAG, "material.getName(): " + material.getName());
		}
		return list;
	}

	public List<Material> listByDishId(int startDishId, int endDishId)
			throws SQLException {
		List<Material> list = null;
		if (mMaterialDao != null) {
			list = mMaterialDao.queryBuilder().where()
					.between("dish_id", startDishId, endDishId).query();
		}
		for (Material material : list) {
			Log.d(TAG, "material.getName(): " + material.getName());
		}
		return list;
	}

	public Material get(int id) throws SQLException {
		if (mMaterialDao == null)
			return null;

		return mMaterialDao.queryForId(id + "");
	}
}
