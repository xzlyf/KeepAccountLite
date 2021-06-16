package com.xz.kal.activity;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.xz.kal.R;
import com.xz.kal.base.BaseActivity;
import com.xz.kal.entity.Bill;
import com.xz.kal.sql.DBManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends BaseActivity {


	@BindView(R.id.top_page)
	ViewPager topPage;
	@BindView(R.id.indicator)
	CircleIndicator indicator;
	private DBManager db;


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
		initView();
		db = DBManager.getInstance(mContext);

	}

	private void initView() {
		View dayView = View.inflate(this, R.layout.fragment_day, null);
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
	}

	private void testData() {
		for (int i = 0; i < 10; i++) {
			Bill bill = new Bill();
			bill.setCategoryId(i + 1);
			bill.setInout(Math.random() > 0.5 ? "in" : "out");
			bill.setMoney(Math.random());
			bill.setRemark("没有备注");
			bill.setCreateTime(new Date());
			bill.setUpdateTime(new Date());
			db.insertBill(bill);
		}
	}

}
