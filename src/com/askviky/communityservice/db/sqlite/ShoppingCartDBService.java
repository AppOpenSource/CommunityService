package com.askviky.communityservice.db.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.askviky.communityservice.bean.Product;
import com.askviky.communityservice.bean.Shop;
import com.askviky.communityservice.bean.ShoppingCart;

public class ShoppingCartDBService {

	private ShoppingCartDBHelper mDBHelper;
	private SQLiteDatabase mDB;
	private ContentResolver mResolver;

	public ShoppingCartDBService(Context context) {
		mDBHelper = new ShoppingCartDBHelper(context);
	}

	/**
	 * search task
	 */
	public List<ShoppingCart> getAll()  throws Exception {
		mDB = mDBHelper.getWritableDatabase();
		List<ShoppingCart> shoppingCartList = new ArrayList<ShoppingCart>();
		String sql = "SELECT shop_id, shop_name  FROM shop";
		Cursor cursor = null;
		Cursor c = null;
		try {
			cursor = mDB.rawQuery(sql, null);
			List<Shop> shopList = new ArrayList<Shop>();
			while (cursor.moveToNext()) {
				int shopId = cursor.getInt(cursor.getColumnIndex("shop_id"));
				String shopName = cursor.getString(cursor.getColumnIndex("shop_name"));
				String sql2 = "SELECT shop_id, product_id, icon, title, price, count, description FROM product WHERE shop_id=" + shopId;
				c = mDB.rawQuery(sql2, null);		
				List<Product> productList = new ArrayList<Product>();
				while (c.moveToNext()) {
					Product product = new Product( 
							c.getInt(c.getColumnIndex("product_id")), 
							c.getString(c.getColumnIndex("icon")), 
							c.getString(c.getColumnIndex("title")), 
							c.getFloat(c.getColumnIndex("price")), 
							c.getInt(c.getColumnIndex("count")), 
							c.getString(c.getColumnIndex("description")));
					productList.add(product);
				}
				Shop shop = new Shop(shopId, shopName, productList);
				shopList.add(shop);
				if (c != null) {
					c.close();
				}
			}
			shoppingCartList.addAll(ShoppingCart.makeSampleList(shopList));
		} finally {
			if (c != null) {
				c.close();
			}
			if (cursor != null) {
				cursor.close();
			}
			if (mDB != null) {
				mDB.close();
			}
		}
		return shoppingCartList; 
	}

	/**
	 * insert data to db
	 * @param task
	 * @throws Exception
	 */
	public void insert(int shopId, String shopName, int goodsId, String goodsImg, String goodsName, float goodsPrice, String goodsDesp, int goodsCount) throws Exception {
		mDB = mDBHelper.getWritableDatabase();
		try {
			mDB.execSQL("replace into product(shop_id,product_id,icon,title,price,count,description) values (?,?,?,?,?,?,?)", 
					new Object[] {shopId,goodsId,goodsImg,goodsName,goodsPrice,goodsCount,goodsDesp});
			mDB.execSQL("replace into shop(shop_id,shop_name) values (?,?)", 
					new Object[] {shopId,shopName});
		} finally {
			if (mDB != null) {
				mDB.close();
			}
		}
	}

	public void deleteShop(int shopId) throws Exception {
		mDB = mDBHelper.getWritableDatabase();
		try {
			mDB.delete("shop", "shop_id=?", new String[]{shopId+""});
			mDB.delete("product", "shop_id=?", new String[]{shopId+""});
		} finally {
			if (mDB != null) {
				mDB.close();
			}
		}
	}
	
	public void deleteProduct(int productId) throws Exception {
		mDB = mDBHelper.getWritableDatabase();
		try {
			mDB.delete("product", "product_id=?", new String[]{productId+""});
		} finally {
			if (mDB != null) {
				mDB.close();
			}
		}
	}

	public void deleteByOrder(List<Shop> shops) throws Exception {
		mDB = mDBHelper.getWritableDatabase();
		mDB.beginTransaction();
		try {
			for (Shop shop : shops) {
				List<Product> pList = shop.getProductList();
				for (Product p : pList) {
					mDB.delete("product", "product_id=?", new String[]{p.getProductId()+""});
				}
				mDB.delete("shop", "shop_id=? AND (SELECT count(product_id) FROM product WHERE shop_id=?)=0", 
						new String[]{shop.getId()+"", shop.getId()+""});
			}
			mDB.setTransactionSuccessful();
			mDB.endTransaction();
		} finally {
			if (mDB != null) {
				mDB.close();
			}
		}
	}
}
