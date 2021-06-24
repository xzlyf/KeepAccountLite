package com.xz.kal.entity;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/20
 */
public class DayBill {
	//今日收支总额
	private double dayTotal;
	//今日收入金额
	private double dayIn;
	//进入支出金额
	private double dayOut;

	public double getDayTotal() {
		return dayTotal;
	}

	public void setDayTotal(double dayTotal) {
		this.dayTotal = dayTotal;
	}

	public double getDayIn() {
		return dayIn;
	}

	public void setDayIn(double dayIn) {
		this.dayIn = dayIn;
	}

	public double getDayOut() {
		return dayOut;
	}

	public void setDayOut(double dayOut) {
		this.dayOut = dayOut;
	}

}
