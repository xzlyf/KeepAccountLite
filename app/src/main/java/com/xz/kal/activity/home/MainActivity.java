package com.xz.kal.activity.home;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xz.kal.R;
import com.xz.kal.adapter.BillAdapter;
import com.xz.kal.base.BaseActivity;
import com.xz.kal.constant.Local;
import com.xz.kal.custom.SlideRecyclerView;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.DayBill;
import com.xz.kal.utils.TimeUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IHomeContract.IView {


	@BindView(R.id.recycler_money)
	SlideRecyclerView recyclerMoney;
	@BindView(R.id.tv_day)
	TextView tvDay;
	@BindView(R.id.tv_day_in)
	TextView tvDayIn;
	@BindView(R.id.tv_day_out)
	TextView tvDayOut;

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

	}

	@Override
	public void today(Calendar calendar) {
		tvDay.setText(TimeUtil.getSimMilliDate("MM月dd日", calendar.getTimeInMillis()));
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
		}
	}

}
