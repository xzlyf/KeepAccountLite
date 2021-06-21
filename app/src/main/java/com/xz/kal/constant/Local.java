package com.xz.kal.constant;

import com.xz.kal.entity.Category;

import java.util.Map;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/17
 */
public class Local {
	//进出标识
	public static final CharSequence SYMBOL_IN = "in";
	public static final CharSequence SYMBOL_OUT = "out";
	//货币符号，可变
	public static String symbol = "￥";
	//分类标签数据
	public static Map<Integer, Category> categories;
}
