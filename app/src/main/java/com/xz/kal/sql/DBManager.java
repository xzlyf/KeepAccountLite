package com.xz.kal.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/15
 */
public class DBManager {
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

	/**
	 * 查询表中有几条数据
	 *
	 * @param tableName
	 * @return
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

}
