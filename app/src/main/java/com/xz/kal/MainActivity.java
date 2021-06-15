package com.xz.kal;

import android.util.Log;

import com.xz.kal.base.BaseActivity;
import com.xz.kal.sql.DBManager;

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

		DBManager db = DBManager.getInstance(mContext);
		Log.d(TAG, "initData: "+db.queryTotal("category"));
	}
}
