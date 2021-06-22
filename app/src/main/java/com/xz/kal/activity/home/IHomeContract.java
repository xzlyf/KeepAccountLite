package com.xz.kal.activity.home;


import com.xz.kal.base.IBaseView;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;
import com.xz.kal.entity.DayBill;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/17
 * mvp 契约类
 */
public interface IHomeContract {
	interface IModel {
		//获取指定时间范围的账单信息 日期格式 yyyy-MM-dd
		Observable<List<Bill>> getBill(Date start, Date end);

		//计算指定时间范围的账单金额 日期格式 yyyy-MM-dd
		Observable<DayBill> calcBill(Date start, Date end);

		//获取所有分类标签数据，包括默认标签和用户自定标签
		Observable<Map<Integer, Category>> getCategory();

		//存储分类标签数据
		Observable<Integer> saveCategory(List<Category> list);
	}

	interface IPresenter {
		void getToday();

		//获取今天的账单
		void getBill();

		//获取指定日期的账单
		void getBill(Date start, Date end);

		//获取今天的账单
		void calcBill();

		//计算指定日期的账单
		void calcBill(Date start, Date end);
	}

	interface IView extends IBaseView {

		void today(Calendar calendar);

		void todayBill(DayBill dayBill);

		void refresh(List<Bill> list);

	}
}
