package com.xz.kal.activity.home;

import com.xz.kal.entity.Bill;
import com.xz.kal.entity.DayBill;
import com.xz.kal.utils.CalendarUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.internal.observers.BlockingBaseObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/17
 */
public class Presenter implements IHomeContract.IPresenter {
	public static final String TAG = Presenter.class.getName();
	private IHomeContract.IView mView;
	private IHomeContract.IModel model;
	private Calendar dayCal;


	Presenter(IHomeContract.IView view) {
		mView = view;
		model = new Model();
		dayCal = Calendar.getInstance();
	}

	@Override
	public void getToday() {
		mView.today(dayCal);
	}

	@Override
	public void getBill() {
		getBill(CalendarUtil.getDayStart().getTime(), CalendarUtil.getDayEnd().getTime());
	}

	@Override
	public void getBill(Date start, Date end) {
		if (end.getTime() < start.getTime()) {
			mView.sToast("结束日期不可小于开始日期");
			return;
		}
		model.getBill(start, end)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BlockingBaseObserver<List<Bill>>() {
					@Override
					public void onNext(@NonNull List<Bill> list) {
						mView.refresh(list);
					}

					@Override
					public void onError(@NonNull Throwable e) {
						mView.refresh(null);
						e.printStackTrace();
					}
				});
	}

	@Override
	public void calcTodayBill() {
		calcBill(CalendarUtil.getDayStart().getTime(), CalendarUtil.getDayEnd().getTime());
	}

	@Override
	public void calcMonthBill() {
		model.calcBill(CalendarUtil.getMonthStart().getTime(), CalendarUtil.getMonthEnd().getTime())
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BlockingBaseObserver<DayBill>() {
					@Override
					public void onNext(@NonNull DayBill dayBill) {
						mView.todayBill(dayBill);
					}

					@Override
					public void onError(@NonNull Throwable e) {
						DayBill err = new DayBill();
						err.setDayIn(0.0f);
						err.setDayOut(0.0f);
						mView.todayBill(err);
					}
				});
	}

	@Override
	public void calcBill(Date start, Date end) {
		model.calcBill(start, end)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BlockingBaseObserver<DayBill>() {
					@Override
					public void onNext(@NonNull DayBill dayBill) {
						mView.todayBill(dayBill);
					}

					@Override
					public void onError(@NonNull Throwable e) {
						DayBill err = new DayBill();
						err.setDayIn(0.0f);
						err.setDayOut(0.0f);
						mView.todayBill(err);
					}
				});
	}
}
