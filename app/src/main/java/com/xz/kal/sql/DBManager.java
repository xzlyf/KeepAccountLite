package com.xz.kal.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xz.kal.sql.DBHelper.FIELD_CATEGORY_ICON;
import static com.xz.kal.sql.DBHelper.FIELD_CATEGORY_ID;
import static com.xz.kal.sql.DBHelper.FIELD_CATEGORY_INOUT;
import static com.xz.kal.sql.DBHelper.FIELD_CATEGORY_LABEL;
import static com.xz.kal.sql.DBHelper.FIELD_COMMON_CATEGORY_ID;
import static com.xz.kal.sql.DBHelper.FIELD_COMMON_CREATE;
import static com.xz.kal.sql.DBHelper.FIELD_COMMON_ID;
import static com.xz.kal.sql.DBHelper.FIELD_COMMON_INOUT;
import static com.xz.kal.sql.DBHelper.FIELD_COMMON_MONEY;
import static com.xz.kal.sql.DBHelper.FIELD_COMMON_REMARK;
import static com.xz.kal.sql.DBHelper.FIELD_COMMON_UPDATE;
import static com.xz.kal.sql.DBHelper.TABLE_CATEGORY;
import static com.xz.kal.sql.DBHelper.TABLE_COMMON;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/15
 */
public class DBManager {
	public static final String TAG = DBManager.class.getName();
	private static DBManager mInstance;
	private DBHelper dbHelper;

	private DBManager(Context context) {
		dbHelper = new DBHelper(context);

	}

	public static DBManager getInstance(Context context) {
		if (mInstance == null) {
			synchronized (DBManager.class) {
				if (mInstance == null) {
					mInstance = new DBManager(context);
				}
			}
		}
		return mInstance;
	}

	/*
	============================通用方法=============================
	 */

	/**
	 * 查询表中有几条数据
	 *
	 * @param tableName 表明
	 */
	public long queryTotal(String tableName) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "select count(*) from " + tableName;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			long count = cursor.getLong(0);
			return count;
		} catch (Exception e) {
			return -1;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}


	/*
	============================账目操作=============================
	 */

	/**
	 * 新增记账
	 */
	public void insertBill(Bill bill) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(FIELD_COMMON_CATEGORY_ID, bill.getCategoryId());
		cv.put(FIELD_COMMON_INOUT, bill.isInout());
		cv.put(FIELD_COMMON_MONEY, bill.getMoney());
		cv.put(FIELD_COMMON_REMARK, bill.getRemark());
		cv.put(FIELD_COMMON_UPDATE, bill.getUpdateTime().getTime());
		cv.put(FIELD_COMMON_CREATE, bill.getCreateTime().getTime());
		db.insert(TABLE_COMMON, null, cv);
		db.close();
	}

	/**
	 * 删除记账
	 *
	 * @param id 账目id
	 */

	public int deleteBill(int id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int line = db.delete(TABLE_COMMON, FIELD_COMMON_ID + "=?", new String[]{String.valueOf(id)});
		db.close();
		return line;

	}

	/**
	 * 修改记账
	 *
	 * @param id   账目id
	 * @param bill 新的账目
	 */
	public int updateBill(int id, Bill bill) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(FIELD_COMMON_CATEGORY_ID, bill.getCategoryId());
		cv.put(FIELD_COMMON_INOUT, bill.isInout());
		cv.put(FIELD_COMMON_MONEY, bill.getMoney());
		cv.put(FIELD_COMMON_REMARK, bill.getRemark());
		cv.put(FIELD_COMMON_UPDATE, bill.getUpdateTime().getTime());
		cv.put(FIELD_COMMON_CREATE, bill.getCreateTime().getTime());
		int line = db.update(TABLE_COMMON, cv, FIELD_COMMON_ID + "=?", new String[]{String.valueOf(id)});
		db.close();
		return line;
	}

	/**
	 * 查询记账
	 */
	public List<Bill> queryBill() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "select * from " + TABLE_COMMON;
		Cursor cursor = null;
		List<Bill> list = new ArrayList<>();
		Bill bill;
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				bill = new Bill();
				bill.setId(cursor.getInt(0));
				bill.setCategoryId(cursor.getInt(1));
				bill.setInout(cursor.getString(2));
				bill.setMoney(cursor.getDouble(3));
				bill.setRemark(cursor.getString(4));
				bill.setUpdateTime(new Date(cursor.getLong(5)));
				bill.setCreateTime(new Date(cursor.getLong(6)));
				list.add(bill);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			db.close();
		}
		return list;
	}

	/**
	 * 查询记账
	 * 按时间区间来查
	 *
	 * @param start 开始时间 yyyy-MM-dd 的毫秒级时间戳
	 * @param end   结束时间 yyyy-MM-dd 的毫秒级时间戳
	 */
	public List<Bill> queryBill(long start, long end) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder sqlBuild = new StringBuilder();
		sqlBuild.append("select ")
				.append(" * ")
				.append(" from ")
				.append(TABLE_COMMON)
				.append(" where ")
				.append(FIELD_COMMON_CREATE)
				.append(" between ")
				.append(start)
				.append(" and ")
				.append(end);
		Cursor cursor = null;
		List<Bill> list = new ArrayList<>();
		try {
			cursor = db.rawQuery(sqlBuild.toString(), null);
			Bill bill;
			while (cursor.moveToNext()) {
				bill = new Bill();
				bill.setId(cursor.getInt(0));
				bill.setCategoryId(cursor.getInt(1));
				bill.setInout(cursor.getString(2));
				bill.setMoney(cursor.getDouble(3));
				bill.setRemark(cursor.getString(4));
				bill.setUpdateTime(new Date(cursor.getLong(5)));
				bill.setCreateTime(new Date(cursor.getLong(6)));
				list.add(bill);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return list;
	}

	/*
	============================分类表操作=============================
	 */


	/**
	 * 新增分类标签
	 */
	public void insertCategory(Category category) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(FIELD_CATEGORY_LABEL, category.getLabel());
		cv.put(FIELD_CATEGORY_ICON, category.getIcon());
		cv.put(FIELD_CATEGORY_INOUT, category.getInout());
		db.insert(TABLE_CATEGORY, null, cv);
		db.close();
	}

	/**
	 * 新增分类标签 (开启事务-快速插入)
	 *
	 * @return 0 所有数据存入无错误   非0 错误数量
	 */
	public int insertCategory(List<Category> list) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		//开启事务
		db.beginTransaction();
		ContentValues cv;
		int failedCount = 0;
		try {
			for (Category category : list) {
				cv = new ContentValues();
				cv.put(FIELD_CATEGORY_LABEL, category.getLabel());
				cv.put(FIELD_CATEGORY_ICON, category.getIcon());
				cv.put(FIELD_CATEGORY_INOUT, category.getInout());
				db.insert(TABLE_CATEGORY, null, cv);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			failedCount -= 1;
		} finally {
			db.endTransaction();
		}
		return failedCount;
	}

	/**
	 * 删除分类标签
	 *
	 * @param id 标签d
	 */

	public int deleteCategory(int id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int line = db.delete(TABLE_CATEGORY, FIELD_CATEGORY_ID + "=?", new String[]{String.valueOf(id)});
		db.close();
		return line;

	}

	/**
	 * 修改分类
	 *
	 * @param id       分类id
	 * @param category 新的分类
	 */
	public int updateCategory(int id, Category category) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(FIELD_CATEGORY_LABEL, category.getLabel());
		cv.put(FIELD_CATEGORY_ICON, category.getIcon());
		cv.put(FIELD_CATEGORY_INOUT, category.getInout());
		int line = db.update(TABLE_CATEGORY, cv, FIELD_CATEGORY_ID + "=?", new String[]{String.valueOf(id)});
		db.close();
		return line;
	}

	/**
	 * 查询分类
	 */
	public List<Category> queryCategory() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String sql = "select * from " + TABLE_CATEGORY;
		Cursor cursor = null;
		List<Category> list = new ArrayList<>();
		Category category;
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				category = new Category();
				category.setId(cursor.getInt(0));
				category.setLabel(cursor.getString(1));
				category.setIcon(cursor.getInt(2));
				category.setInout(cursor.getString(3));
				list.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			db.close();
		}
		return list;
	}


	public void testData() {
		for (int i = 0; i < 10; i++) {
			Bill bill = new Bill();
			bill.setCategoryId(i + 1);
			bill.setInout(Math.random() > 0.5 ? "in" : "out");
			bill.setMoney(Math.random());
			bill.setRemark("没有备注");
			bill.setCreateTime(new Date());
			bill.setUpdateTime(new Date());
			insertBill(bill);
		}
	}
}
