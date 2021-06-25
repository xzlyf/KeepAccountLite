package com.xz.kal.sql.dao;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/25
 */
public class CategoryDao {
	public static final String TABLE_NAME = "category";
	public static final String ID = "id";
	public static final String LABEL = "label";
	public static final String ICON = "icon";
	public static final String INOUT = "inout";

	public static final String CREATE_SQL = "CREATE TABLE category (" +
			"    id    INTEGER PRIMARY KEY AUTOINCREMENT," +
			"    label TEXT    NOT NULL," +
			"    icon  TEXT    NOT NULL," +
			"    inout VARCHAR(32) " +
			");";
}
