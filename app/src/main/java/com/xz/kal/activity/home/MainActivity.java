package com.xz.kal.activity.home;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.google.android.material.snackbar.Snackbar;
import com.xz.kal.R;
import com.xz.kal.activity.add.AddActivity;
import com.xz.kal.adapter.BillAdapterV2;
import com.xz.kal.base.BaseActivity;
import com.xz.kal.base.BaseRecyclerAdapter;
import com.xz.kal.constant.Local;
import com.xz.kal.custom.EmptyTipsRecyclerView;
import com.xz.kal.dialog.BagSelectDialog;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.DayBill;
import com.xz.kal.utils.SpacesItemDecorationVertical;
import com.xz.kal.utils.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	@BindView(R.id.tv_wallet)
	TextView tvWallet;

	private Presenter mPresenter;
	private BillAdapterV2 billAdapter;
	private BagSelectDialog walletDialog;

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
		billAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Bill>() {
			@Override
			public void onClick(Bill bill) {
				sToast("详情页待做：" + bill.getCategory().getLabel());
			}
		});
	}


	@OnClick({R.id.tv_add, R.id.tv_wallet, R.id.tv_day, R.id.ic_setting})
	public void onViewClick(View v) {
		switch (v.getId()) {
			case R.id.tv_add:
				v.animate().rotationBy(360f).setDuration(500).start();
				startActivityForResult(new Intent(mContext, AddActivity.class), Local.REQ_ADD);
				overridePendingTransition(R.anim.push_in_addactivity, R.anim.no_anim);
				break;
			case R.id.tv_wallet:
				if (walletDialog == null) {
					walletDialog = new BagSelectDialog(mContext);
					walletDialog.create();
					walletDialog.setWallList(mPresenter.getWalletData());
				}
				walletDialog.show();
				break;
			case R.id.tv_day:
				DatePickDialog datePickDialog = new DatePickDialog(mContext);
				//设置标题
				datePickDialog.setTitle("选择时间");
				//设置类型
				datePickDialog.setType(DateType.TYPE_YMD);
				//设置消息体的显示格式，日期格式
				datePickDialog.setMessageFormat("yyyy-MM-dd");
				//设置点击确定按钮回调
				datePickDialog.setOnSureLisener(null);
				datePickDialog.setOnSureLisener(new OnSureLisener() {
					@Override
					public void onSure(Date date) {
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						today(c);
						c.set(Calendar.HOUR_OF_DAY, 23);//控制时
						c.set(Calendar.MINUTE, 59);//控制分
						c.set(Calendar.SECOND, 59);//控制秒
						c.set(Calendar.MILLISECOND, 0);
						Date end = new Date(c.getTimeInMillis());
						c.set(Calendar.HOUR_OF_DAY, 0);//控制时
						c.set(Calendar.MINUTE, 0);//控制分
						c.set(Calendar.SECOND, 0);//控制秒
						c.set(Calendar.MILLISECOND, 0);
						Date start = new Date(c.getTimeInMillis());
						mPresenter.getBill(start, end);

					}
				});
				datePickDialog.show();
				break;
			case R.id.ic_setting:
				sToast("设置页待做");
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Local.REQ_ADD && resultCode == RESULT_OK) {
			mPresenter.getBill();
			mPresenter.getToday();
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
	public void refresh(List<Bill> list, Date... time) {
		if (refreshLayout.isRefreshing()) {
			refreshLayout.setRefreshing(false);
			Snackbar.make(refreshLayout, "刷新成功", Snackbar.LENGTH_SHORT).show();
		}
		//刷新列表控件
		if (list != null) {
			billAdapter.refreshAndClear(list);
		} else {
			billAdapter.refreshAndClear(new ArrayList<>());
		}
		//刷新指定日期范围账单金额
		if (time != null && time.length == 2) {
			mPresenter.calcBill(time[0], time[1]);
		}
	}

	@Override
	public void setBag(String text) {
		tvWallet.setText(String.format("%s ▾", text));
	}

}
