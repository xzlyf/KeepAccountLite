package com.xz.kal.activity.home;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xz.kal.R;
import com.xz.kal.activity.add.AddActivity;
import com.xz.kal.adapter.BillAdapter;
import com.xz.kal.base.BaseActivity;
import com.xz.kal.constant.Local;
import com.xz.kal.custom.EmptyTipsRecyclerView;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.DayBill;
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
	@BindView(R.id.tv_day_in)
	TextView tvDayIn;

	private Presenter mPresenter;
	private BillAdapter billAdapter;

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
		//获取今日的账单
		mPresenter.getBill();
		//获取今日日期
		mPresenter.getToday();
		//获取今日账单金额
		mPresenter.calcBill();
	}

	/**
	 * 底部导航栏颜色
	 */
	private void changeNavigatorBar() {
		int vis = getWindow().getDecorView().getSystemUiVisibility();
		vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
		getWindow().getDecorView().setSystemUiVisibility(vis);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setNavigationBarColor(getResources().getColor(R.color.white));
	}

	private void initView() {
		recyclerMoney.setLayoutManager(new LinearLayoutManager(mContext));
		billAdapter = new BillAdapter(mContext);
		recyclerMoney.setAdapter(billAdapter);
	}

	@OnClick(R.id.tv_add)
	public void onViewClick(View v) {
		switch (v.getId()) {
			case R.id.tv_add:
				startActivity(new Intent(mContext, AddActivity.class));
				break;
		}
	}

	@Override
	public void today(Calendar calendar) {
		tvDay.setText(TimeUtil.getSimMilliDate("M月d日", calendar.getTimeInMillis()));
	}

	@Override
	public void todayBill(DayBill dayBill) {
		tvDayIn.setText(String.format("%s+%s", Local.symbol, dayBill.getDayIn()));
		tvDayOut.setText(String.format("%s-%s", Local.symbol, dayBill.getDayOut()));
	}

	@Override
	public void refresh(List<Bill> list) {
		if (list != null) {
			billAdapter.superRefresh(list);
		} else {
			billAdapter.superRefresh(new ArrayList<>());
		}
	}

}
