package com.xz.kal.sql.dao;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/25
 */
public class KeepDao {
	public static final String TABLE_NAME = "keep";
	public static final String FIELD_KEEP_DATE = "date";
	public static final String FIELD_KEEP_COUNT = "count";

	public static final String CREATE_SQL = "CREATE TABLE keep (" +
			"    date TEXT NOT NULL" +
			"            UNIQUE," +
			"    count INTEGER" +
			");";

}
