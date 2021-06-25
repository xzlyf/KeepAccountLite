package com.xz.kal.activity.home;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.xz.kal.R;
import com.xz.kal.activity.add.AddActivity;
import com.xz.kal.adapter.BillAdapterV2;
import com.xz.kal.base.BaseActivity;
import com.xz.kal.constant.Local;
import com.xz.kal.custom.EmptyTipsRecyclerView;
import com.xz.kal.dialog.BagSelectDialog;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.DayBill;
import com.xz.kal.utils.SpacesItemDecorationVertical;
import com.xz.kal.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IHomeContract.IView {


	@BindView(R.id.recycler_money)
	EmptyTipsRecyclerView recyclerMoney;
	@BindView(R.id.tv_day)
	TextView tvDay;
	@BindView(R.id.tv_day_out)
	TextView tvDayOut;
	@BindView(R.id.refresh_layout)
	SwipeRefreshLayout refreshLayout;

	private Presenter mPresenter;
	private BillAdapterV2 billAdapter;

	@Override
	public boolean homeAsUpEnabled() {
		return false;
	}

	@Override
	public int getLayoutResource() {
		return R.layout.activity_main;
	}

	@Override
	public void initData() {
		mPresenter = new Presenter(this);
		changeNavigatorBar();
		initView();
		mPresenter.init();
		mPresenter.getBill();
		mPresenter.getToday();

	}

	private void initView() {
		recyclerMoney.setLayoutManager(new LinearLayoutManager(mContext));
		recyclerMoney.addItemDecoration(new SpacesItemDecorationVertical(15));
		billAdapter = new BillAdapterV2(mContext);
		recyclerMoney.setAdapter(billAdapter);
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				mPresenter.init();
				mPresenter.getBill();
				mPresenter.getToday();
			}
		});
	}


	@OnClick({R.id.tv_add, R.id.tv_wallet})
	public void onViewClick(View v) {
		switch (v.getId()) {
			case R.id.tv_add:
				v.animate().rotationBy(360f).setDuration(500).start();
				startActivityForResult(new Intent(mContext, AddActivity.class), Local.REQ_ADD);
				overridePendingTransition(R.anim.push_in_addactivity, R.anim.no_anim);
				break;
			case R.id.tv_wallet:
				BagSelectDialog dialog = new BagSelectDialog(mContext);
				dialog.create();
				dialog.setWallList(mPresenter.getWalletData());
				dialog.show();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Local.REQ_ADD && resultCode == RESULT_OK) {
			mPresenter.getBill();
		}
	}

	@Override
	public void today(Calendar calendar) {
		tvDay.setText(TimeUtil.getSimMilliDate("M月d日", calendar.getTimeInMillis()));
	}

	@Override
	public void todayBill(DayBill dayBill) {
		tvDayOut.setText(String.format("%s%s", dayBill.getDayTotal(), Local.symbol));
	}

	@Override
	public void refresh(List<Bill> list) {
		if (refreshLayout.isRefreshing()) {
			refreshLayout.setRefreshing(false);
			Snackbar.make(refreshLayout, "刷新成功", Snackbar.LENGTH_SHORT).show();
		}
		//刷新今日账单金额
		mPresenter.calcTodayBill();
		if (list != null) {
			billAdapter.refreshAndClear(list);
		} else {
			billAdapter.refreshAndClear(new ArrayList<>());
		}
	}

}
