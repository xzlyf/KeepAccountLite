package com.xz.kal.sql.dao;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/25
 */
public class WalletDao {
	public static final String TABLE_NAME = "wallet";
	public static final String ID = "id";
	public static final String LABEL = "label";
	public static final String ICON = "icon";

	public static final String CREATE_SQL = "CREATE TABLE wallet (" +
			"id INTEGER PRIMARY KEY AUTOINCREMENT," +
			"label TEXT" +
			"icon INTEGER" +
			");";
}
