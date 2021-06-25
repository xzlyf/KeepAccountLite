package com.xz.kal.sql;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xz.kal.sql.dao.BillDao;
import com.xz.kal.sql.dao.CategoryDao;
import com.xz.kal.sql.dao.ConfigDao;
import com.xz.kal.sql.dao.KeepDao;
import com.xz.kal.sql.dao.WalletDao;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/4/16
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBHelper";
	private AtomicInteger mOpenCounter = new AtomicInteger();//引用计数
	private SQLiteDatabase mDatabase;

	private static final int DB_VERSION = 3;   // 数据库版本
	private static final String DB_KEYBAG = "kal_db";//数据库名


	DBHelper(Context context) {
		this(context, DB_KEYBAG, null, DB_VERSION);
	}

	DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
		//不可忽略的 进行so库加载
		//SQLiteDatabase.loadLibs(context);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建表
		createTable(db);
	}

	private void createTable(SQLiteDatabase db) {


		try {
			db.execSQL(BillDao.CREATE_SQL);
			db.execSQL(CategoryDao.CREATE_SQL);
			db.execSQL(KeepDao.CREATE_SQL);
			db.execSQL(ConfigDao.CREATE_SQL);
			db.execSQL(WalletDao.CREATE_SQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}


	/**
	 * 获取读写的db对象，解决并发访问sqlite
	 * 参考文章：https://blog.csdn.net/liuyi1207164339/article/details/50966590
	 */
	public synchronized SQLiteDatabase openDB() {
		if (mOpenCounter.incrementAndGet() == 1) {//自增后判断
			mDatabase = super.getWritableDatabase();
		}
		return mDatabase;
	}

	/**
	 * 关闭db数据库，解决并发的问题
	 */
	public synchronized void closeDB() {
		if (mOpenCounter.decrementAndGet() == 0) {//自减后判断
			mDatabase.close();

		}
	}

}
