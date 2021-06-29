package com.xz.kal.utils;

import java.util.Calendar;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/24
 */
public class CalendarUtil {

	/**
	 * 获取今天0点时间
	 *
	 * @return
	 */
	public static Calendar getDayStart() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);//控制时
		cal.set(Calendar.MINUTE, 0);//控制分
		cal.set(Calendar.SECOND, 0);//控制秒
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	/**
	 * 获取当天24:59:59点时间
	 */
	public static Calendar getDayEnd() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);//控制时
		cal.set(Calendar.MINUTE, 59);//控制分
		cal.set(Calendar.SECOND, 59);//控制秒
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	/**
	 * 获取本月开始的第一天
	 * 例如:2021-6-1 00:00:00
	 */
	public static Calendar getMonthStart() {
		Calendar start = Calendar.getInstance();
		start.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		start.set(Calendar.DAY_OF_MONTH, start.getActualMinimum(Calendar.DAY_OF_MONTH));
		return start;
	}

	/**
	 * 获取本月最后一天24:59
	 */
	public static Calendar getMonthEnd() {
		Calendar end = Calendar.getInstance();
		end.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
		end.set(Calendar.HOUR_OF_DAY, 23);//控制时
		end.set(Calendar.MINUTE, 59);//控制分
		end.set(Calendar.SECOND, 59);//控制秒
		return end;
	}
}
