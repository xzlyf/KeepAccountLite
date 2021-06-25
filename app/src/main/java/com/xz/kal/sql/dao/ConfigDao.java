package com.xz.kal.sql.dao;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/25
 */
public class ConfigDao {
	public static final String TABLE_NAME = "config";
	public static final String FIELD_CONFIG_DATA = "data";


	public static final String CREATE_SQL = "CREATE TABLE config (" +
			"    data TEXT" +
			");";
}
