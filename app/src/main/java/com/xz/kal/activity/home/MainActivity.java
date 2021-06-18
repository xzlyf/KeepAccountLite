package com.xz.kal.activity.home;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xz.kal.R;
import com.xz.kal.adapter.BillAdapter;
import com.xz.kal.base.BaseActivity;
import com.xz.kal.constant.Local;
import com.xz.kal.custom.SlideRecyclerView;
import com.xz.kal.entity.Bill;
import com.xz.kal.utils.SpacesItemDecorationVertical;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends BaseActivity implements IHomeContract.IView {


	@BindView(R.id.top_page)
	ViewPager topPage;
	@BindView(R.id.indicator)
	CircleIndicator indicator;
	@BindView(R.id.recycler_money)
	SlideRecyclerView recyclerMoney;
	@BindView(R.id.btn_detail)
	Button btnDetail;
	@BindView(R.id.btn_my)
	Button btnMy;
	@BindView(R.id.btn_add)
	ImageButton btnAdd;
	TextView tvDay;
	TextView tvDayMoney;
	TextView tvMonth;
	TextView tvMonthMoney;

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
		initView();
		//获取今日的账单
		mPresenter.getBill();

	}

	private void initView() {
		View dayView = View.inflate(this, R.layout.fragment_day, null);
		tvDay = dayView.findViewById(R.id.tv_day);
		tvDayMoney = dayView.findViewById(R.id.tv_day_money);
		tvMonth = dayView.findViewById(R.id.tv_month);
		tvMonthMoney = dayView.findViewById(R.id.tv_month_money);
		View MonthView = View.inflate(this, R.layout.fragment_month, null);
		List<View> viewList = new ArrayList<>();
		viewList.add(dayView);
		viewList.add(MonthView);
		topPage.setAdapter(new PagerAdapter() {
			@Override
			public int getCount() {
				return viewList.size();
			}

			@Override
			public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
				return view == object;
			}

			@NonNull
			@Override
			public Object instantiateItem(@NonNull ViewGroup container, int position) {
				container.addView(viewList.get(position));
				return viewList.get(position);
			}
		});
		indicator.setViewPager(topPage);

		recyclerMoney.setLayoutManager(new LinearLayoutManager(this));
		recyclerMoney.addItemDecoration(new SpacesItemDecorationVertical(10));
		billAdapter = new BillAdapter(mContext);
		recyclerMoney.setAdapter(billAdapter);
	}

	@Override
	public void refresh(List<Bill> list) {
		if (list != null) {
			billAdapter.superRefresh(list);
		}
	}

	@Override
	public void setDay(Calendar calendar) {
		tvDay.setText(String.format("%s月%s日",
				calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONDAY) + 1));
	}

	@Override
	public void setDayMoney(String st) {
		tvDayMoney.setText(String.format(Local.symbol + "%s", st));
	}

	@Override
	public void setMonth(Calendar calendar) {
		tvMonth.setText(String.format("%s月", calendar.get(Calendar.DAY_OF_MONTH)));
	}

	@Override
	public void setMonthMoney(String st) {
		tvMonthMoney.setText(String.format(Local.symbol + "%s", st));
	}
}
