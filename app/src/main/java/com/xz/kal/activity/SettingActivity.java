package com.xz.kal.activity;

import com.xz.kal.R;
import com.xz.kal.base.BaseActivity;

public class SettingActivity extends BaseActivity {


	@Override
	public boolean homeAsUpEnabled() {
		return true;
	}

	@Override
	public int getLayoutResource() {
		return R.layout.activity_setting;
	}

	@Override
	public void initData() {

	}
}
