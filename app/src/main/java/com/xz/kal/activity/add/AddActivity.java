package com.xz.kal.activity.add;

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
import com.xz.kal.base.BaseRecyclerAdapter;
import com.xz.kal.dialog.MulKeyBoardDialog;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;
import com.xz.kal.utils.SpacesItemDecorationVH;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddActivity extends BaseActivity implements IAddContract.IView {

	public static final String EXTRA_NEW = "newBill";

	@BindView(R.id.tab_layout)
	SmartTabLayout tabLayout;
	@BindView(R.id.item_viewPage)
	ViewPager viewPager;

	RecyclerView inRecycler;
	RecyclerView outRecycler;
	CategoryAdapter inAdapter;
	CategoryAdapter outAdapter;
	private String[] pageTitle = {"支出", "收入"};
	private Presenter mPresenter;
	private MulKeyBoardDialog multiDialog;

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
		mPresenter = new Presenter(this);
		changeNavigatorBar();
		initView();
		mPresenter.getItemData();
	}

	private void initView() {
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
				return pageTitle[position];
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

		inAdapter = new CategoryAdapter(this);
		inRecycler.setLayoutManager(new GridLayoutManager(this, 4));
		inRecycler.addItemDecoration(new SpacesItemDecorationVH(10));
		inRecycler.setAdapter(inAdapter);
		outAdapter = new CategoryAdapter(this);
		outRecycler.setLayoutManager(new GridLayoutManager(this, 4));
		outRecycler.addItemDecoration(new SpacesItemDecorationVH(10));
		outRecycler.setAdapter(outAdapter);

		inAdapter.setOnItemClickListener(onItemClickListener);
		outAdapter.setOnItemClickListener(onItemClickListener);


	}

	@OnClick(R.id.ic_back)
	public void onViewClick(View v) {
		switch (v.getId()) {
			case R.id.ic_back:
				finish();
				break;
		}
	}

	private BaseRecyclerAdapter.OnItemClickListener<Category> onItemClickListener =
			new BaseRecyclerAdapter.OnItemClickListener<Category>() {
				@Override
				public void onClick(Category category) {

					if (multiDialog == null) {
						multiDialog = new MulKeyBoardDialog(mContext);
						multiDialog.create();
						multiDialog.setOnSubmitCallback(new MulKeyBoardDialog.OnSubmitCallback() {
							@Override
							public void onSubmit(Bill bill) {
								mPresenter.saveBill(bill);
							}
						});

					}
					multiDialog.setCategory(category);
					multiDialog.show();

				}
			};


	@Override
	public void insertSuccess(Bill bill) {
		setResult(RESULT_OK);
		finish();
	}

	@Override
	public void refreshItem(Map<Integer, List<Category>> refresh) {
		inAdapter.refreshAndClear(refresh.get(0));
		outAdapter.refreshAndClear(refresh.get(1));
	}

	@Override
	public void onBackPressed() {
		if (multiDialog != null && multiDialog.isShowing()) {
			multiDialog.dismiss();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.no_anim, R.anim.push_out_addactivity);
	}
}
