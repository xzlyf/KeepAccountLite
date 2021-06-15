package com.xz.kal.utils;

import java.util.Currency;
import java.util.Locale;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/15
 */
public class CurrencyUtil {

	/**
	 * 根据手机语言获取当前当前国家的货币符号
	 *
	 * @return
	 */
	public static String getSymbol() {
		Locale locale = new Locale(Locale.getDefault().getLanguage(), Locale.getDefault().getCountry());
		Currency currency = Currency.getInstance(locale);
		return currency.getSymbol();
	}
}
