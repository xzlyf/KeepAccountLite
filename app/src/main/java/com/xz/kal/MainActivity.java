package com.xz.kal;

import com.orhanobut.logger.Logger;
import com.xz.kal.base.BaseActivity;
import com.xz.kal.entity.Bill;
import com.xz.kal.sql.DBManager;

import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity {


	DBManager db;


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
		db = DBManager.getInstance(mContext);
		db.deleteBill(3);
		List<Bill> list = db.queryBill();
		for (int i = 0; i < list.size(); i++) {
			Logger.d(list.get(i).toString());
		}
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
