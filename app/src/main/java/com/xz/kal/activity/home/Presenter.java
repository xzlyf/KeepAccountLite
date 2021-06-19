package com.xz.kal.activity.home;

import com.orhanobut.logger.Logger;
import com.xz.kal.constant.Local;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;

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

	Presenter(IHomeContract.IView view) {
		mView = view;
		model = new Model();
		model.getCategory()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BlockingBaseObserver<List<Category>>() {
					@Override
					public void onNext(@NonNull List<Category> list) {
						Local.categories = list;
					}

					@Override
					public void onError(@NonNull Throwable e) {

					}
				});
	}

	@Override
	public void getBill() {
		getBill(new Date(), new Date());
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
	public void calcBill(Date start, Date end) {

	}
}
