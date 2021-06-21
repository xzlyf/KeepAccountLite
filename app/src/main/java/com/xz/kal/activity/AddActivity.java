package com.xz.kal.activity;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.xz.kal.R;
import com.xz.kal.adapter.CategoryAdapter;
import com.xz.kal.base.BaseActivity;
import com.xz.kal.constant.Local;
import com.xz.kal.entity.Category;
import com.xz.kal.utils.SpacesItemDecorationVH;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class AddActivity extends BaseActivity {


	@BindView(R.id.tab_layout)
	SmartTabLayout tabLayout;
	@BindView(R.id.item_viewPage)
	ViewPager viewPager;

	RecyclerView inRecycler;
	RecyclerView outRecycler;

	@Override
	public boolean homeAsUpEnabled() {
		return false;
	}

	@Override
	public int getLayoutResource() {
		return R.layout.activity_add;
	}

	@Override
	public void initData() {
		initView();
	}

	private void initView() {
		List<String> pageTitle = new ArrayList<>();
		pageTitle.add("支出");
		pageTitle.add("收入");
		View outView = View.inflate(this, R.layout.view_recycler, null);
		outRecycler = outView.findViewById(R.id.recycler_type);
		View inView = View.inflate(this, R.layout.view_recycler, null);
		inRecycler = inView.findViewById(R.id.recycler_type);
		List<View> viewList = new ArrayList<>();
		viewList.add(outView);
		viewList.add(inView);
		viewPager.setAdapter(new PagerAdapter() {
			@Nullable
			@Override
			public CharSequence getPageTitle(int position) {
				return pageTitle.get(position);
			}

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
		tabLayout.setViewPager(viewPager);

		CategoryAdapter outAdapter = new CategoryAdapter(this);
		CategoryAdapter inAdapter = new CategoryAdapter(this);

		inRecycler.setLayoutManager(new GridLayoutManager(this, 4));
		inRecycler.addItemDecoration(new SpacesItemDecorationVH(10));
		inRecycler.setAdapter(inAdapter);

		outRecycler.setLayoutManager(new GridLayoutManager(this, 4));
		outRecycler.addItemDecoration(new SpacesItemDecorationVH(10));
		outRecycler.setAdapter(outAdapter);


		List<Category> inList = new ArrayList<>();
		List<Category> outList = new ArrayList<>();

		for (Map.Entry<Integer, Category> entry : Local.categories.entrySet()) {
			if (entry.getValue().getInout().contentEquals(Local.SYMBOL_IN)) {
				inList.add(entry.getValue());
			} else {
				outList.add(entry.getValue());
			}
		}

		inAdapter.superRefresh(inList);
		outAdapter.superRefresh(outList);

	}

}
