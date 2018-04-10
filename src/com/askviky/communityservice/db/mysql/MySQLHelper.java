package com.askviky.communityservice.db.mysql;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.askviky.communityservice.common.GlobalConstant;
import com.askviky.communityservice.db.mysql.bean.Dish;
import com.askviky.communityservice.db.mysql.bean.Material;
import com.askviky.communityservice.db.mysql.bean.Menu;
import com.askviky.communityservice.db.mysql.bean.Move;
import com.askviky.communityservice.db.mysql.dao.CookbookDao;
import com.askviky.communityservice.db.mysql.dao.DishDao;
import com.askviky.communityservice.db.mysql.dao.MaterialDao;
import com.askviky.communityservice.db.mysql.dao.MenuDao;
import com.askviky.communityservice.db.mysql.dao.MoveDao;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class MySQLHelper {

	private static final String TAG = MySQLHelper.class.getSimpleName();
	private static ConnectionSource mConnection = null;
	private static Map<String, Dao<?, ?>> mDaos = new HashMap<String, Dao<?, ?>>();
	
	public static synchronized ConnectionSource getConnSrc() {
		if (mConnection != null) return mConnection;
		try {
			mConnection = new JdbcConnectionSource(GlobalConstant.DB_URL);
			((JdbcConnectionSource) mConnection).setUsername(GlobalConstant.USER);
			((JdbcConnectionSource) mConnection).setPassword(GlobalConstant.PASSWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mConnection;
	}
	
	public synchronized static Dao<?, ?> getDao(Class<?> clazz) {
		Dao<?, ?> dao = null;
		String className = clazz.getSimpleName();
		
        if (mDaos.containsKey(className)) {
        	dao = mDaos.get(className);
        }
        if (dao == null) {
        	dao = getDaoObj(clazz);
        	mDaos.put(className, dao);
        }
        return dao;
    }
	
	private static Dao<?, ?> getDaoObj(Class<?> clazz) {
		Class<?>[] classParas = new Class[] { ConnectionSource.class };
		Object[] paras = new Object[] { mConnection };
		//获取使用当前构造方法来创建对象的Constructor对象，用它来获取构造函数的一些信息  
        Constructor<?> con = null;
		try {
			con = clazz.getConstructor(classParas);
		} catch (NoSuchMethodException ne) {
			ne.printStackTrace();
		}
        Dao<?, ?> dao = null;
		try {
			if (con != null) dao = (Dao<?, ?>) con.newInstance(paras); // 传入当前构造函数要的参数列表  
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return dao;
	}

	// 获取所有菜肴
	public static List<Dish> getDish() {
		List<Dish> list = null;
		try {
			DishDao dish = (DishDao) getDao(DishDao.class);
			list = dish.queryAllDish();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取指定菜单的所有菜肴
	public static List<Dish> getDishByMenu(int menuId) {
		List<Dish> list = null;
		try {
			DishDao dish = (DishDao) getDao(DishDao.class);
			list = dish.listByMenuId(menuId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取所有食材
	public static List<Material> getMaterial() {
		List<Material> list = null;
		try {
			MaterialDao material = (MaterialDao) getDao(MaterialDao.class);
			list = material.queryAllMaterial();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取指定菜肴的食材
	public static List<Material> getMaterialByDish(int dishId) {
		List<Material> list = null;
		try {
			MaterialDao material = (MaterialDao) getDao(MaterialDao.class);
			list = material.listByDishId(dishId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取指定范围（从某dishId到某dishId）菜肴的食材
	public static List<Material> getMaterialByDish(int startDishId, int endDishId) {
		List<Material> list = null;
		try {
			MaterialDao material = (MaterialDao) getDao(MaterialDao.class);
			list = material.listByDishId(startDishId, endDishId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取指定id的食材
	public static Material getMaterialById(int id) {
		Material material = null;
		try {
			MaterialDao materialDao = (MaterialDao) getDao(MaterialDao.class);
			material = materialDao.get(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return material;
	}

	// 获取所有菜单
	public static List<Menu> getAllMenu() {
		List<Menu> list = null;
		try {
			MenuDao menu = (MenuDao) getDao(MenuDao.class);
			list = menu.queryAllMenu();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// TODO 按条件获取菜单
	public static List<Menu> getMenuByConditions() {
		List<Menu> list = null;
		try {
			MenuDao menu = (MenuDao) getDao(MenuDao.class);
			int pLow = 4;
			int pHigh = 6;
			double costLow = 400;
			double costHigh = 500;
			String style = "北京菜";
			list = menu.listByConditions(pLow, pHigh, costLow, costHigh, style);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取所有做法
	public static List<Move> getMove() {
		List<Move> list = null;
		try {
			MoveDao move = (MoveDao) getDao(MoveDao.class);
			list = move.queryAllMove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取指定菜肴的做法
	public static List<Move> getMoveByDish(int dishId) {
		List<Move> list = null;
		try {
			MoveDao move = (MoveDao) getDao(MoveDao.class);
			list = move.listByDishId(dishId);
//			QueryBuilder<Move, ?> builder = move.queryBuilder(); 
//			builder.where().eq("dish_id", dishID+"");
//			list = builder.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/*
	 * *********** 查询操作 >>>
	 */
	// TODO 多字段条件查询
	/*QueryBuilder builder = dao.queryBuilder(); 
	builder.where().eq(字段名1,条件1).and.eq(字段名2,条件2); 
	builder.query();*/
	
	// TODO 查询并按顺序输出
	/*QueryBuilder<person, integer=""> builder = dao.queryBuilder();
	builder.orderBy(字段名, true);
	builder.query();*/
	
	// TODO 分页查询
	/*QueryBuilder<person, integer=""> builder = dao.queryBuilder();
	builder.offset(10);//表示查询的起始位置
	builder.limit(10);//表示总共获取的对象数量
	builder.query();*/
	/*
	 * <<< 查询操作 *************
	 */
	
	/*
	 * *********** 删除和更改操作 >>>
	 */
	// TODO DeleteBuilder
	// TODO UpdateBuilder
	/*
	 * <<< 删除和更改操作 *************
	 */
	
	/*
	 * *********** just for test >>>
	 */
	public static void main(String[] args) {
		System.out.println("starting...");
		testMySql(GlobalConstant.DB_URL);
	}
	// TODO
	private static void testMySql(String dbUrl) {
		System.out.println("testMySql()");
		ConnectionSource connection;
		try {
			connection = new JdbcConnectionSource(dbUrl);
			((JdbcConnectionSource) connection).setUsername(GlobalConstant.USER);
			((JdbcConnectionSource) connection).setPassword(GlobalConstant.PASSWD);
			CookbookDao cd = new CookbookDao(connection);
			cd.performDBOpt(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * <<< just for test *************
	 */
}
