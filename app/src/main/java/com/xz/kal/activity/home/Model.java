package com.xz.kal.activity.home;

import android.database.Observable;

import com.xz.kal.entity.Bill;

import java.util.Date;
import java.util.List;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/17
 */
public class Model implements IHomeContract.IModel {
	@Override
	public Observable<List<Bill>> getBill(Date start, Date end) {
		return null;
	}

	@Override
	public Observable<String> calcBill(Date start, Date end) {
		return null;
	}
}
