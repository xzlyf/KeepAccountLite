package com.xz.kal.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;
import com.xz.kal.entity.DayBill;
import com.xz.kal.entity.Wallet;
import com.xz.kal.sql.dao.BillDao;
import com.xz.kal.sql.dao.CategoryDao;
import com.xz.kal.sql.dao.WalletDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		SQLiteDatabase db = dbHelper.openDB();
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

	/**
	 * 查询这个表最近插入数据的id   可用于刚插入数据返回主键id
	 *
	 * @param table 表明
	 * @return
	 */
	public int queryLastNewRowId(String table) {
		SQLiteDatabase db = dbHelper.openDB();
		String sql = "select last_insert_rowid() from " + table;
		Cursor cursor = null;
		int a = -1;
		try {
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToFirst()) {
				a = cursor.getInt(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			dbHelper.closeDB();
		}
		return a;
	}


	/*
	============================账目操作=============================
	 */

	/**
	 * 新增记账
	 */
	public void insertBill(Bill bill) {
		SQLiteDatabase db = dbHelper.openDB();
		ContentValues cv = new ContentValues();
		cv.put(BillDao.CATEGORY_ID, bill.getCategory().getId());
		cv.put(BillDao.WALLET_ID, bill.getWallet().getId());
		cv.put(BillDao.INOUT, bill.getInout());
		cv.put(BillDao.MONEY, bill.getMoney());
		cv.put(BillDao.REMARK, bill.getRemark());
		cv.put(BillDao.UPDATE_TIME, bill.getUpdateTime().getTime());
		cv.put(BillDao.CREATE_TIME, bill.getCreateTime().getTime());
		db.insert(BillDao.TABLE_NAME, null, cv);
		dbHelper.closeDB();
	}

	/**
	 * 删除记账
	 *
	 * @param id 账目id
	 */

	public int deleteBill(int id) {
		SQLiteDatabase db = dbHelper.openDB();
		int line = db.delete(BillDao.TABLE_NAME, BillDao.ID + "=?", new String[]{String.valueOf(id)});
		dbHelper.closeDB();
		return line;

	}

	/**
	 * 修改记账
	 *
	 * @param id   账目id
	 * @param bill 新的账目
	 */
	public int updateBill(int id, Bill bill) {
		SQLiteDatabase db = dbHelper.openDB();
		ContentValues cv = new ContentValues();
		cv.put(BillDao.CATEGORY_ID, bill.getCategory().getId());
		cv.put(BillDao.INOUT, bill.getInout());
		cv.put(BillDao.MONEY, bill.getMoney());
		cv.put(BillDao.REMARK, bill.getRemark());
		cv.put(BillDao.UPDATE_TIME, bill.getUpdateTime().getTime());
		cv.put(BillDao.CREATE_TIME, bill.getCreateTime().getTime());
		int line = db.update(BillDao.TABLE_NAME, cv, BillDao.ID + "=?", new String[]{String.valueOf(id)});
		dbHelper.closeDB();
		return line;
	}

	/**
	 * 根据账单id查询账单数据
	 *
	 * @return
	 */
	public Bill queryBill(int id) {
		return null;
	}

	/**
	 * 查询记账（所有账单）
	 */
	public List<Bill> queryBill() {
		return null;
	}

	/**
	 * 查询记账
	 * 按时间区间来查
	 *
	 * @param start 开始时间 yyyy-MM-dd 的毫秒级时间戳
	 * @param end   结束时间 yyyy-MM-dd 的毫秒级时间戳
	 */
	public List<Bill> queryBill(long start, long end) {
		SQLiteDatabase db = dbHelper.openDB();
		String sqlBuild = "select * from " + BillDao.TABLE_NAME + " c join " + CategoryDao.TABLE_NAME
				+ " g on c." + BillDao.CATEGORY_ID + " = g." + CategoryDao.ID +
				" where " + BillDao.CREATE_TIME + "  between ? and ? order by " + BillDao.CREATE_TIME + " desc";
		Cursor cursor = null;
		List<Bill> list = new ArrayList<>();
		try {
			//返回结果包含了Bill和Category的所有字段，bill在前面
			cursor = db.rawQuery(sqlBuild, new String[]{String.valueOf(start), String.valueOf(end)});
			Bill bill;
			Category category;
			while (cursor.moveToNext()) {
				bill = new Bill();
				category = new Category();
				bill.setId(cursor.getInt(0));
				bill.setInout(cursor.getString(2));
				bill.setMoney(cursor.getDouble(3));
				bill.setRemark(cursor.getString(4));
				bill.setUpdateTime(new Date(cursor.getLong(5)));
				bill.setCreateTime(new Date(cursor.getLong(6)));
				category.setId(cursor.getInt(7));
				category.setLabel(cursor.getString(8));
				category.setIcon(cursor.getInt(9));
				category.setInout(cursor.getString(10));
				bill.setCategory(category);
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

	/**
	 * 计算一天的账单金额
	 *
	 * @param start 开始日期 00:00:00
	 * @param end   结束日期是开始日期的第二天的00:00:00
	 * @return 账单金额数据
	 */
	public DayBill calcBill(Date start, Date end) {
		//查询指定日期的收入金额sql语句
		String sqlIn = "SELECT sum(" + BillDao.MONEY + ") FROM " + BillDao.TABLE_NAME + " where inout =\"in\" and " + BillDao.CREATE_TIME + " between " + start.getTime() + " and " + end.getTime();
		//查询指定日期的支出金额sql语句
		String sqlOut = "SELECT sum(" + BillDao.MONEY + ") FROM " + BillDao.TABLE_NAME + " where inout =\"out\" and " + BillDao.CREATE_TIME + " between " + start.getTime() + " and " + end.getTime();
		DayBill dayBill = new DayBill();
		SQLiteDatabase db = dbHelper.openDB();
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sqlIn, null);
			if (cursor.moveToFirst()) {
				double total = cursor.getDouble(cursor.getColumnIndex("sum(money)"));
				dayBill.setDayIn(total);
			}
			cursor = db.rawQuery(sqlOut, null);
			if (cursor.moveToFirst()) {
				double total = cursor.getDouble(cursor.getColumnIndex("sum(money)"));
				dayBill.setDayOut(total);
			}
			BigDecimal inDec = new BigDecimal(dayBill.getDayIn());
			BigDecimal outDec = new BigDecimal(dayBill.getDayOut());
			//计算收支
			dayBill.setDayTotal(inDec.subtract(outDec).doubleValue());

		} catch (Exception e) {
			e.printStackTrace();
			dayBill.setDayIn(1f);
			dayBill.setDayOut(1f);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			dbHelper.closeDB();
		}

		return dayBill;
	}

	/*
	============================分类表操作=============================
	 */


	/**
	 * 新增分类标签
	 */
	public void insertCategory(Category category) {
		SQLiteDatabase db = dbHelper.openDB();
		ContentValues cv = new ContentValues();
		cv.put(CategoryDao.LABEL, category.getLabel());
		cv.put(CategoryDao.ICON, category.getIcon());
		cv.put(CategoryDao.INOUT, category.getInout());
		db.insert(CategoryDao.TABLE_NAME, null, cv);
		dbHelper.closeDB();
	}

	/**
	 * 新增分类标签 (开启事务-快速插入)
	 *
	 * @return 0 所有数据存入无错误   非0 错误数量
	 */
	public int insertCategory(List<Category> list) {
		SQLiteDatabase db = dbHelper.openDB();
		//开启事务
		db.beginTransaction();
		ContentValues cv;
		int failedCount = 0;
		try {
			for (Category category : list) {
				cv = new ContentValues();
				cv.put(CategoryDao.LABEL, category.getLabel());
				cv.put(CategoryDao.ICON, category.getIcon());
				cv.put(CategoryDao.INOUT, category.getInout());
				db.insert(CategoryDao.TABLE_NAME, null, cv);
			}
			db.setTransactionSuccessful();
		} catch (Throwable e) {
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
		SQLiteDatabase db = dbHelper.openDB();
		int line = db.delete(CategoryDao.TABLE_NAME, CategoryDao.ID + "=?", new String[]{String.valueOf(id)});
		dbHelper.closeDB();
		return line;

	}

	/**
	 * 修改分类
	 *
	 * @param id       分类id
	 * @param category 新的分类
	 */
	public int updateCategory(int id, Category category) {
		SQLiteDatabase db = dbHelper.openDB();
		ContentValues cv = new ContentValues();
		cv.put(CategoryDao.LABEL, category.getLabel());
		cv.put(CategoryDao.ICON, category.getIcon());
		cv.put(CategoryDao.INOUT, category.getInout());
		int line = db.update(CategoryDao.TABLE_NAME, cv, CategoryDao.ID + "=?", new String[]{String.valueOf(id)});
		dbHelper.closeDB();
		return line;
	}

	/**
	 * 查询分类
	 */
	public List<Category> queryCategory() {
		SQLiteDatabase db = dbHelper.openDB();
		String sql = "select * from " + CategoryDao.TABLE_NAME;
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
			dbHelper.closeDB();
		}
		return list;
	}


	/*
	============================钱包表操作=============================
	 */

	/**
	 * 新增钱包
	 */
	public void insertWallet(Wallet wallet) {
		SQLiteDatabase db = dbHelper.openDB();
		ContentValues cv = new ContentValues();
		cv.put(WalletDao.LABEL, wallet.getName());
		cv.put(WalletDao.ICON, wallet.getIcon());
		db.insert(WalletDao.TABLE_NAME, null, cv);
		dbHelper.closeDB();
	}

	/**
	 * 新增钱包 (开启事务-快速插入)
	 *
	 * @return 0 所有数据存入无错误   非0 错误数量
	 */
	public int insertWallet(List<Wallet> list) {
		SQLiteDatabase db = dbHelper.openDB();
		//开启事务
		db.beginTransaction();
		ContentValues cv;
		int failedCount = 0;
		try {
			for (Wallet wallet : list) {
				cv = new ContentValues();
				cv.put(WalletDao.LABEL, wallet.getName());
				cv.put(WalletDao.ICON, wallet.getIcon());
				db.insert(WalletDao.TABLE_NAME, null, cv);
			}
			db.setTransactionSuccessful();
		} catch (Throwable e) {
			failedCount -= 1;
		} finally {
			db.endTransaction();
		}
		return failedCount;
	}

	/**
	 * 删除钱包
	 *
	 * @param id 钱包id
	 */

	public int deleteWallet(int id) {
		SQLiteDatabase db = dbHelper.openDB();
		int line = db.delete(WalletDao.TABLE_NAME, WalletDao.ID + "=?", new String[]{String.valueOf(id)});
		dbHelper.closeDB();
		return line;

	}

	/**
	 * 修改钱包
	 *
	 * @param id     钱包id
	 * @param wallet 新的分类
	 */
	public int updateWallet(int id, Wallet wallet) {
		SQLiteDatabase db = dbHelper.openDB();
		ContentValues cv = new ContentValues();
		cv.put(WalletDao.LABEL, wallet.getName());
		int line = db.update(WalletDao.TABLE_NAME, cv, WalletDao.ID + "=?", new String[]{String.valueOf(id)});
		dbHelper.closeDB();
		return line;
	}

	/**
	 * 查询钱包
	 */
	public List<Wallet> queryWallet() {
		SQLiteDatabase db = dbHelper.openDB();
		String sql = "select * from " + WalletDao.TABLE_NAME;
		Cursor cursor = null;
		List<Wallet> list = new ArrayList<>();
		Wallet wallet;
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				wallet = new Wallet();
				wallet.setId(cursor.getInt(0));
				wallet.setName(cursor.getString(1));
				wallet.setIcon(cursor.getInt(2));
				list.add(wallet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			dbHelper.closeDB();
		}
		return list;
	}

}
