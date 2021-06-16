package com.xz.kal.activity.home;

import android.database.Observable;

import com.xz.kal.entity.Bill;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/17
 * mvp 契约类
 */
public interface IHomeContract {
	interface IModel {
		Observable<List<Bill>> getBill(Date start, Date end);

		Observable<String> calcBill(Date start, Date end);
	}

	interface IPresenter {
		//获取指定时间范围的账单信息
		void getBill(Date start, Date end);

		//计算指定时间范围的账单金额
		void calcBill(Date start, Date end);
	}

	interface IView {
		void showLoading(String message);

		void disLoading();

		void sToast(String message);

		void refresh(List<Bill> list);

		void setDay(Calendar calendar);

		void setDayMoney(String st);

		void setMonth(Calendar calendar);

		void setMonthMoney(String st);

	}
}
