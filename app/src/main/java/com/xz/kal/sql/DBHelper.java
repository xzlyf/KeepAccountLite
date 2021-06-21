package com.xz.kal.sql;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

	private static final int DB_VERSION = 1;   // 数据库版本
	private static final String DB_KEYBAG = "kal_db";//数据库名
	public static String DB_PWD;//数据库密码
	public static final String TABLE_CATEGORY = "category";
	public static final String TABLE_COMMON = "common";
	public static final String TABLE_KEEP = "keep";
	public static final String TABLE_CONFIG = "config";
	public static final String FIELD_CATEGORY_ID = "id";
	public static final String FIELD_CATEGORY_LABEL = "label";
	public static final String FIELD_CATEGORY_ICON = "icon";
	public static final String FIELD_CATEGORY_INOUT = "inout";
	public static final String FIELD_COMMON_ID = "id";
	public static final String FIELD_COMMON_CATEGORY_ID = "category_id";
	public static final String FIELD_COMMON_INOUT = "inout";
	public static final String FIELD_COMMON_MONEY = "money";
	public static final String FIELD_COMMON_REMARK = "remark";
	public static final String FIELD_COMMON_UPDATE = "update_time";
	public static final String FIELD_COMMON_CREATE = "create_time";
	public static final String FIELD_KEEP_DATE = "date";
	public static final String FIELD_KEEP_COUNT = "count";
	public static final String FIELD_CONFIG_DATA = "data";

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
		String sql_category = "CREATE TABLE category (" +
				"    id    INTEGER PRIMARY KEY AUTOINCREMENT," +
				"    label TEXT    NOT NULL," +
				"    icon  TEXT    NOT NULL," +
				"    inout VARCHAR(32) " +
				");";

		String sql_common = "CREATE TABLE common (" +
				"    id          INTEGER PRIMARY KEY AUTOINCREMENT," +
				"    category_id INTEGER NOT NULL," +
				"    inout       VARCHAR(32)," +
				"    money       REAL," +
				"    remark      TEXT," +
				"    update_time INTEGER," +
				"    create_time INTEGER" +
				");";
		String sql_keep = "CREATE TABLE keep (" +
				"    date TEXT NOT NULL" +
				"            UNIQUE," +
				"    count INTEGER" +
				");";
		String sql_config = "CREATE TABLE config (" +
				"    data TEXT" +
				");";
		try {
			db.execSQL(sql_category);
			db.execSQL(sql_common);
			db.execSQL(sql_keep);
			db.execSQL(sql_config);
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
