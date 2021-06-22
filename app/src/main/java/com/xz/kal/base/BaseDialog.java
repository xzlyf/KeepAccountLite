package com.xz.kal.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xz.kal.R;

public abstract class BaseDialog extends Dialog {
	protected Context mContext;
	private OnCancelListener cancelListener;

	public BaseDialog(Context context) {
		this(context, 0);
	}

	public BaseDialog(Context context, int themeResId) {
		this(context, false, null);
	}

	public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		if (cancelListener != null) {
			this.cancelListener = cancelListener;
		}

		mContext = context;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResource());
		Window window = getWindow();
		if (window != null) {
			window.setBackgroundDrawableResource(R.color.transparent);
			WindowManager.LayoutParams lp = window.getAttributes();
			DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
			lp.width = (int) (dm.widthPixels * 0.8);
			lp.dimAmount = 0.2f;
			window.setAttributes(lp);
		}

		initData();
	}


	/**
	 * 布局资源
	 *
	 * @return
	 */
	protected abstract int getLayoutResource();

	/**
	 * 数据初始化
	 */
	protected abstract void initData();


	/**
	 * 点击编辑框外隐藏软键盘并清除焦点
	 *
	 * @param ev
	 * @return
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	/**
	 * 是否应该隐藏键盘
	 *
	 * @param v
	 * @param event
	 * @return
	 */
	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = {0, 0};
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				v.clearFocus();
				return true;
			}
		}
		return false;
	}
}
