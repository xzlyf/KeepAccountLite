package com.xz.kal.sql.dao;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/25
 */
public class BillDao {
	public static final String TABLE_NAME = "common";
	public static final String ID = "id";
	public static final String CATEGORY_ID = "category_id";
	public static final String WALLET_ID = "wallet_id";
	public static final String INOUT = "inout";
	public static final String MONEY = "money";
	public static final String REMARK = "remark";
	public static final String UPDATE_TIME = "update_time";
	public static final String CREATE_TIME = "create_time";


	public static final String CREATE_SQL = "CREATE TABLE common (" +
			"    id          INTEGER PRIMARY KEY AUTOINCREMENT," +
			"    category_id INTEGER NOT NULL," +
			"    wallet_id INTEGER NOT NULL," +
			"    inout       VARCHAR(32)," +
			"    money       REAL," +
			"    remark      TEXT," +
			"    update_time INTEGER," +
			"    create_time INTEGER" +
			");";

}
