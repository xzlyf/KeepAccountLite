package com.xz.kal.activity.add;

import com.xz.kal.base.IBaseView;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/22
 */
public interface IAddContract {
	interface IModel {
		Observable<Map<Integer, List<Category>>> getItemData();

		Observable<Bill> saveBill(Bill bill);
	}

	interface IPresenter {
		void getItemData();

		void saveBill(Bill bill);
	}

	interface IView extends IBaseView {
		void refreshItem(Map<Integer, List<Category>> refresh);
	}
}
