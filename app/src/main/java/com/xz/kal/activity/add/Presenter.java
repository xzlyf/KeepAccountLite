package com.xz.kal.activity.add;

import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;
import com.xz.kal.entity.Wallet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.internal.observers.BlockingBaseObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/22
 */
class Presenter implements IAddContract.IPresenter {
	private IAddContract.IModel mModel;
	private IAddContract.IView mView;
	private List<Wallet> wallets;

	public Presenter(IAddContract.IView view) {
		mView = view;
		mModel = new Model();
		wallets = new ArrayList<>();

	}

	@Override
	public List<Wallet> getWalletData() {
		return wallets;
	}

	@Override
	public void getWallet() {
		mModel.getWallet()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BlockingBaseObserver<List<Wallet>>() {
					@Override
					public void onNext(@NonNull List<Wallet> list) {
						wallets.clear();
						wallets.addAll(list);
					}

					@Override
					public void onError(@NonNull Throwable e) {
						wallets.clear();
					}
				});
	}

	@Override
	public void getCategory() {
		mModel.getCategory()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BlockingBaseObserver<Map<Integer, List<Category>>>() {
					@Override
					public void onNext(@NonNull Map<Integer, List<Category>> integerListMap) {
						mView.refreshItem(integerListMap);
					}

					@Override
					public void onError(@NonNull Throwable e) {
						mView.sToast("分类标签读取失败");
					}
				});
	}

	@Override
	public void saveBill(Bill bill) {
		mModel.saveBill(bill)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new BlockingBaseObserver<Bill>() {
					@Override
					public void onNext(@NonNull Bill bill) {
						//插入成功
						mView.insertSuccess(bill);
					}

					@Override
					public void onError(@NonNull Throwable e) {
						mView.sToast("账单存储失败，请重试！");
					}
				});
	}
}
