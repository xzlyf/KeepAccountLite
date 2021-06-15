package com.xz.kal;

import com.xz.kal.base.BaseActivity;

public class MainActivity extends BaseActivity {


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

	}
}
