package com.xz.kal.activity.add;

import com.xz.kal.constant.Local;
import com.xz.kal.entity.Category;

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
	@Override
	public Observable<Map<Integer, List<Category>>> getItemData() {
		return Observable.create(new ObservableOnSubscribe<Map<Integer, List<Category>>>() {
			@Override
			public void subscribe(@NonNull ObservableEmitter<Map<Integer, List<Category>>> emitter) throws Throwable {
				List<Category> inList = new ArrayList<>();
				List<Category> outList = new ArrayList<>();
				for (Map.Entry<Integer, Category> entry : Local.categories.entrySet()) {
					if (entry.getValue().getInout().contentEquals(Local.SYMBOL_IN)) {
						inList.add(entry.getValue());
					} else {
						outList.add(entry.getValue());
					}
				}

				Map<Integer, List<Category>> map = new HashMap<>();
				map.put(0, inList);
				map.put(1, outList);
				emitter.onNext(map);
			}
		});
	}
}
