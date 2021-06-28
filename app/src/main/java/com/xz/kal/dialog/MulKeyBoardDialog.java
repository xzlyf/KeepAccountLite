package com.xz.kal.dialog;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;
import com.xz.kal.R;
import com.xz.kal.base.BaseDialog;
import com.xz.kal.constant.Local;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;
import com.xz.kal.entity.Wallet;
import com.xz.kal.utils.DatePickerUtil;
import com.xz.kal.utils.TimeUtil;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class MulKeyBoardDialog extends BaseDialog {

	@BindView(R.id.back)
	ImageView back;
	@BindView(R.id.tv_wallet)
	TextView tvWallet;
	@BindView(R.id.select_type)
	ImageView selectType;
	@BindView(R.id.select_category)
	TextView selectCategory;
	@BindView(R.id.money)
	EditText money;
	@BindView(R.id.symbol)
	TextView symbol;
	@BindView(R.id.time_text)
	TextView timeText;
	@BindView(R.id.date_text)
	TextView dateText;
	@BindView(R.id.remarks)
	EditText remarks;
	@BindView(R.id.text_count)
	TextView textCount;
	@BindView(R.id.custom_keyboard)
	KeyboardView mNumberView;
	private long timeStamp;//时间戳

	private static final int DATE_SELECT = -1;
	private static final int SUBMIT = -4;
	private static final int BACKSPACE = -5;
	private static final int ADD = -10;//+
	private static final int SUB = -11;//-
	private static final int MULTI = -12;//*
	private static final int DIVISION = -13;//÷
	private static final int DECIMAL = -9;//.


	private Category mCategory;
	private OnSubmitCallback mCallback;
	private List<Wallet> wallets;

	public MulKeyBoardDialog(Context context) {
		this(context, R.style.BottomDialog);
	}

	private MulKeyBoardDialog(Context context, int themeResId) {
		super(context, themeResId);
	}


	@Override
	protected int getLayoutResource() {
		return R.layout.dialog_input_view;
	}

	@Override
	protected void initData() {
		Window window = getWindow();
		if (window != null) {
			//底部显示
			window.setGravity(Gravity.BOTTOM);
			//弹出动画
			window.setWindowAnimations(R.style.BottomDialog_Animation);
			WindowManager.LayoutParams lp = window.getAttributes();
			//屏幕宽度
			lp.width = mContext.getResources().getDisplayMetrics().widthPixels;
			lp.dimAmount = 0.4f;
			window.setAttributes(lp);
		}
		setCanceledOnTouchOutside(true);
		setCancelable(true);

		// 数字键盘
		Keyboard mNumberKeyboard = new Keyboard(mContext, R.xml.custom_keyboard_v1);
		//数字键盘View
		symbol.setText(Local.symbol);

		mNumberView.setKeyboard(mNumberKeyboard);
		mNumberView.setEnabled(true);
		mNumberView.setPreviewEnabled(false);
		mNumberView.setOnKeyboardActionListener(listener);

		remarks.addTextChangedListener(textWatcher);

		timeStamp = System.currentTimeMillis();//获取当前系统时间
		dateText.setText(TimeUtil.getSimMilliDate("yyyy年M月d日", timeStamp));
		timeText.setText(TimeUtil.getSimMilliDate("H:mm", timeStamp));

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		// TODO: 2021/6/27 获取钱包数据
	}

	@OnClick(R.id.tv_wallet)
	void selectWallet() {

	}

	@Override
	public void show() {
		selectType.setImageResource(mCategory.getIcon());
		selectCategory.setText(mCategory.getLabel());
		super.show();
	}

	private int nowCursor;
	private Editable editable;
	/**
	 * 键盘监听
	 */
	private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
		/**
		 * 按下，在onKey之前，可以在这里做一些操作，这里让有的没有按下的悬浮提示
		 * @param i
		 */
		@Override
		public void onPress(int i) {
		}

		/**
		 * 松开
		 * @param primaryCode
		 */
		@Override
		public void onRelease(int primaryCode) {
		}

		/**
		 * 按下
		 * @param i
		 * @param keyCodes
		 */
		@Override
		public void onKey(int i, int[] keyCodes) {

			editable = money.getText();
			nowCursor = money.getSelectionStart();

			switch (i) {
				case BACKSPACE:
					if (editable != null && editable.length() > 0) {
						if (nowCursor > 0) {
							editable.delete(nowCursor - 1, nowCursor);
						}
					}
					break;
				case SUBMIT:
					saveBill();
					break;
				case DATE_SELECT:
					selectDate();
					break;
				case ADD:
					editable.insert(nowCursor, "+");
					break;
				case SUB:
					editable.insert(nowCursor, "-");
					break;
				case MULTI:
					editable.insert(nowCursor, "×");
					break;
				case DIVISION:
					editable.insert(nowCursor, "÷");
					break;
				case DECIMAL:
					if (editable.length() == 0) {
						editable.insert(nowCursor, "0.");
						return;
					}
					if (editable.toString().contains(".")) {
						return;
					}
					editable.insert(nowCursor, ".");
					break;
				default:
					//只能输入不操作两位的小数
					if (editable.toString().contains(".")) {
						String[] arr = editable.toString().split("\\.");
						if (arr.length == 2) {
							if (arr[1].length() >= 2) {
								return;
							}
						}
					}
					//都不是自定义的值就插入
					editable.insert(nowCursor, Character.toString((char) i));
					break;
			}


		}

		@Override
		public void onText(CharSequence text) {

		}

		@Override
		public void swipeLeft() {

		}

		@Override
		public void swipeRight() {

		}

		@Override
		public void swipeDown() {

		}

		@Override
		public void swipeUp() {

		}
	};

	/**
	 * 备注框监听
	 */
	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			if (textCount.getVisibility() == View.GONE) {
				textCount.setVisibility(View.VISIBLE);
			}
			if (remarks.length() == 0) {
				textCount.setVisibility(View.GONE);
			}
			textCount.setText(remarks.length() + "/100");
		}
	};


	/**
	 * 日期选择器
	 */
	private void selectDate() {
		DatePickerUtil.showDatePicker(mContext, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
				final String d = year + "年" + (month + 1) + "月" + dayOfMonth + "日";
				timeStamp = TimeUtil.getStringToDate(d, "yyyy年MM月dd日");
				dateText.setText(d);
				dateText.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

				DatePickerUtil.showTimePicker(mContext, new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						String da = hourOfDay + ":" + minute;
						String target = d + da;
						timeStamp = TimeUtil.getStringToDate(target, "yyyy年MM月dd日HH:mm");
						timeText.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
						timeText.setText(da);

					}
				});
			}
		});
	}

	private void saveBill() {
		if (money.getText().length() <= 0) {
			Snackbar.make(money, "请输入金额", Snackbar.LENGTH_SHORT).show();
			return;
		}

		double m;
		try {
			m = Double.parseDouble(money.getText().toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Snackbar.make(money, "数值异常", Snackbar.LENGTH_SHORT).show();
			return;
		}

		Bill bill = new Bill();
		bill.setCreateTime(new Date(System.currentTimeMillis()));
		bill.setUpdateTime(bill.getCreateTime());
		bill.setRemark(remarks.getText().toString().trim());
		bill.setInout(mCategory.getInout());
		bill.setCategory(mCategory);
		//todo  选中哪个钱包
		bill.setWallet(wallets.get(0));
		bill.setMoney(m);

		if (mCallback != null) {
			MulKeyBoardDialog.this.dismiss();
			mCallback.onSubmit(bill);
		}
	}

	public void setOnSubmitCallback(OnSubmitCallback callback) {
		this.mCallback = callback;
	}

	public void setWallet(List<Wallet> wallet) {
		this.wallets = wallet;
		tvWallet.setText(String.format("%s ▾", wallet.get(0).getName()));
	}


	public void setCategory(Category category) {
		this.mCategory = category;
		money.setText("");
	}

	public interface OnSubmitCallback {
		void onSubmit(Bill bill);
	}


	/**
	 * 是否整数
	 *
	 * @param str
	 * @return
	 */
	private boolean isInteger(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\\\+]?[\\\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 是否浮点数
	 *
	 * @param str
	 * @return
	 */
	private boolean isDouble(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$"); // 之前这里正则表达式错误，现更正
		return pattern.matcher(str).matches();
	}

}
