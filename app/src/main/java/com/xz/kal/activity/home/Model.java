package com.xz.kal.activity.home;


import com.xz.kal.base.BaseApplication;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;
import com.xz.kal.entity.DayBill;
import com.xz.kal.sql.DBManager;
import com.xz.kal.sql.DefaultData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/17
 */
public class Model implements IHomeContract.IModel {
	private DBManager db;

	Model() {
		db = DBManager.getInstance(BaseApplication.getContext());
	}

	@Override
	public Observable<List<Bill>> getBill(Date start, Date end) {
		return Observable.create(new ObservableOnSubscribe<List<Bill>>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<List<Bill>> emitter) throws Throwable {
				List<Bill> list = new ArrayList<>();
				list = db.queryBill(start.getTime(), end.getTime());
				emitter.onNext(list);
			}
		});
	}

	@Override
	public Observable<DayBill> calcBill(Date start, Date end) {
		return Observable.create(new ObservableOnSubscribe<DayBill>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<DayBill> emitter) throws Throwable {
				DayBill dayBill = db.calcBill(start, end);
				BigDecimal inDec = new BigDecimal(dayBill.getDayIn());
				BigDecimal outDec = new BigDecimal(dayBill.getDayOut());
				//计算收支
				dayBill.setTotal(inDec.subtract(outDec).doubleValue());
				emitter.onNext(dayBill);
			}
		});
	}

	@Override
	public Observable<Map<Integer, Category>> getCategory() {
		return Observable.create(new ObservableOnSubscribe<Map<Integer, Category>>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<Map<Integer, Category>> emitter) throws Throwable {
				List<Category> list = db.queryCategory();
				if (list.size() == 0) {
					//等于空，那重新生成默认分类给数据库
					list = DefaultData.getInstance().makeDefaultCategory();
					//存储分类标签
					db.insertCategory(list);
				}

				//填装map，供全局使用
				Map<Integer, Category> categoryMap = new HashMap<>();
				for (Category c : list) {
					categoryMap.put(c.getId(), c);
				}

				emitter.onNext(categoryMap);
			}
		});
	}


	@Override
	public Observable<Integer> saveCategory(List<Category> list) {
		return Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
				int count = db.insertCategory(list);
				emitter.onNext(count);
			}
		});
	}
}
