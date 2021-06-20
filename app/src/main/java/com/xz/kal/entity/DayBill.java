package com.xz.kal.entity;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/20
 */
public class DayBill {
	//今日收入金额
	public  String dayIn;
	//进入支出金额
	public  String dayOut;

	public String getDayIn() {
		return dayIn;
	}

	public void setDayIn(String dayIn) {
		this.dayIn = dayIn;
	}

	public String getDayOut() {
		return dayOut;
	}

	public void setDayOut(String dayOut) {
		this.dayOut = dayOut;
	}
}
