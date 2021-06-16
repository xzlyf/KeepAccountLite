package com.xz.kal.activity.home;

import java.util.Date;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/17
 */
public class Presenter implements IHomeContract.IPresenter {
	private IHomeContract.IView mView;
	private IHomeContract.IModel model;

	public Presenter(IHomeContract.IView view) {
		mView = view;
		model = new Model();
	}

	@Override
	public void getBill(Date start, Date end) {

	}

	@Override
	public void calcBill(Date start, Date end) {

	}
}
