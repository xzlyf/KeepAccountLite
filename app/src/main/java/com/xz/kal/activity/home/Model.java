package com.xz.kal.activity.home;


import com.xz.kal.base.BaseApplication;
import com.xz.kal.entity.Bill;
import com.xz.kal.sql.DBManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		//查询时间为开始时间的00:00:00 结束时间的第二天的00:00:00
		start.setHours(0);
		start.setMinutes(0);
		start.setSeconds(0);
		end.setTime(end.getTime() + 86400000L);//获取第二天
		end.setHours(0);
		end.setMinutes(0);
		end.setSeconds(0);

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
	public Observable<String> calcBill(Date start, Date end) {
		return null;
	}
}
