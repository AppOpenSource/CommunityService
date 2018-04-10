package com.askviky.communityservice.db.mysql.dao;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.askviky.communityservice.db.mysql.MySQLHelper;
import com.askviky.communityservice.db.mysql.bean.Move;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class MoveDao extends BaseDaoImpl<Move, String> {
	
	private static final String TAG = MoveDao.class.getSimpleName();
	private Dao<Move, String> mMoveDao;
	private ConnectionSource mConnSource;
	
	public MoveDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Move.class);
		mConnSource = connectionSource;
		mMoveDao = DaoManager.createDao(mConnSource, Move.class);
	}
	
	public List<Move> queryAllMove() throws SQLException {
		List<Move> list = null;
		if (mMoveDao != null) {
			list = mMoveDao.queryForAll();
		}
		for (Move move : list) {
			Log.d(TAG, "move.getProfile(): "+move.getProfile());
		}
		return list;
	}
	
	//通过Id获取一个Move且携带Dish
    public Move getMoveWithDish(int id) throws SQLException {
    	Move move = null;
    	move = mMoveDao.queryForId(id+"");
    	DishDao dish = (DishDao) MySQLHelper.getDao(DishDao.class);
    	dish.refresh(move.getDish());
        return move;
    }
	
	public Move get(int id) throws SQLException {
		if (mMoveDao == null) return null;
		
		return mMoveDao.queryForId(id+"");
	}
	
	public List<Move> listByDishId(int dishId) throws SQLException {
		if (mMoveDao == null) return null;
    	
		return mMoveDao.queryBuilder().where().eq("dish_id", dishId).query();
    }
}
