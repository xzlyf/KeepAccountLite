package com.xz.kal.activity.home;

import com.orhanobut.logger.Logger;
import com.xz.kal.constant.Local;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;
import com.xz.kal.entity.DayBill;
import com.xz.kal.entity.Wallet;
import com.xz.kal.utils.CalendarUtil;

import java.util.ArrayList;
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
	private List<Wallet> wallets = new ArrayList<>();


	Presenter(IHomeContract.IView view) {
		mView = view;
		model = new Model();
		dayCal = Calendar.getInstance();
	}

	@Override
	public void init() {
		//如果没有标签则生成默认标签数据
		model.getCategory()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BlockingBaseObserver<List<Category>>() {
					@Override
					public void onNext(@NonNull List<Category> list) {
						Local.initCategory = true;
					}

					@Override
					public void onError(@NonNull Throwable e) {
						Local.initCategory = false;
					}
				});
		//如果没有钱包，生成默认钱包
		model.getWallet()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BlockingBaseObserver<List<Wallet>>() {
					@Override
					public void onNext(@NonNull List<Wallet> wallets) {
						Presenter.this.wallets.clear();
						Presenter.this.wallets.addAll(wallets);
						if (wallets.size() > 0) {
							mView.setBag(wallets.get(0).getName());
						}
					}

					@Override
					public void onError(@NonNull Throwable e) {
						Logger.e("钱包获取失败");
					}
				});

	}

	@Override
	public List<Wallet> getWalletData() {
		return wallets;
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
						mView.refresh(list, start, end);
					}

					@Override
					public void onError(@NonNull Throwable e) {
						mView.refresh(null);
						e.printStackTrace();
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
