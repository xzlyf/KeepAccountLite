package com.xz.kal.activity.home;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xz.kal.R;
import com.xz.kal.adapter.BillAdapter;
import com.xz.kal.base.BaseActivity;
import com.xz.kal.custom.SlideRecyclerView;
import com.xz.kal.entity.Bill;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements IHomeContract.IView {


	@BindView(R.id.recycler_money)
	SlideRecyclerView recyclerMoney;

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

	@Override
	public void refresh(List<Bill> list) {
		if (list != null) {
			billAdapter.superRefresh(list);
		}
	}
}
