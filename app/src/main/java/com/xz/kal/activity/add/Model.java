package com.xz.kal.activity.add;

import com.xz.kal.base.BaseApplication;
import com.xz.kal.constant.Local;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;
import com.xz.kal.entity.Wallet;
import com.xz.kal.sql.DBManager;
import com.xz.kal.sql.DefaultData;
import com.xz.kal.sql.dao.BillDao;

import java.util.ArrayList;
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
 * @date 2021/6/22
 */
class Model implements IAddContract.IModel {
	private DBManager db;

	public Model() {
		db = DBManager.getInstance(BaseApplication.getContext());
	}

	@Override
	public Observable<List<Wallet>> getWallet() {
		return Observable.create(new ObservableOnSubscribe<List<Wallet>>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<List<Wallet>> emitter) throws Throwable {
				List<Wallet> list = db.queryWallet();
				if (list.size() == 0) {
					list = DefaultData.getInstance().makeDefaultWallet();
					db.insertWallet(list);
				}
				emitter.onNext(list);
			}
		});
	}

	@Override
	public Observable<Map<Integer, List<Category>>> getCategory() {
		return Observable.create(new ObservableOnSubscribe<Map<Integer, List<Category>>>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<Map<Integer, List<Category>>> emitter) throws Throwable {
				List<Category> allList = db.queryCategory();
				List<Category> inList = new ArrayList<>();
				List<Category> outList = new ArrayList<>();
				for (Category c : allList) {
					if (c.getInout().contentEquals(Local.SYMBOL_IN)){
						inList.add(c);
					}else if (c.getInout().contentEquals(Local.SYMBOL_OUT)){
						outList.add(c);
					}
				}

				Map<Integer, List<Category>> map = new HashMap<>();
				map.put(0, inList);
				map.put(1, outList);
				emitter.onNext(map);
			}
		});
	}

	@Override
	public Observable<Bill> saveBill(Bill bill) {
		return Observable.create(new ObservableOnSubscribe<Bill>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<Bill> emitter) throws Throwable {
				db.insertBill(bill);
				//加上生成的主键id
				bill.setId(db.queryLastNewRowId(BillDao.TABLE_NAME));
				emitter.onNext(bill);
			}
		});
	}

}
