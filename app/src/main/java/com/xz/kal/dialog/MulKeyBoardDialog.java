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
import android.widget.Toast;

import com.xz.kal.R;
import com.xz.kal.base.BaseDialog;
import com.xz.kal.utils.DatePickerUtil;
import com.xz.kal.utils.TimeUtil;

import java.util.regex.Pattern;

public class MulKeyBoardDialog extends BaseDialog {

	private KeyboardView mNumberView;   //数字键盘View
	private Keyboard mNumberKeyboard;   // 数字键盘
	private EditText money;// 金额
	private EditText remarks;//备注
	private TextView selectCategory;
	private TextView wordCount;//字数
	private TextView time;//时间
	private TextView date;//日期
	private ImageView selectType;//选中的图标
	private ImageView back;
	private long timeStamp;//时间戳

	private final int DATE_SELECT = -1;
	private final int ADD = 43;
	private final int SUB = 45;
	private final int SUBMIT = -4;
	private final int BACKSPACE = -5;

	private EditText et;

	private int icon = 0;
	private String label = "";

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

		mNumberKeyboard = new Keyboard(mContext, R.xml.custom_keyboard);
		mNumberView = findViewById(R.id.custom_keyboard);
		money = findViewById(R.id.money);
		remarks = findViewById(R.id.remarks);
		selectCategory = findViewById(R.id.select_category);
		wordCount = findViewById(R.id.text_count);
		selectType = findViewById(R.id.select_type);
		time = findViewById(R.id.time_text);
		date = findViewById(R.id.date_text);
		back = findViewById(R.id.back);
		setEditable(money);

		mNumberView.setKeyboard(mNumberKeyboard);
		mNumberView.setEnabled(true);
		mNumberView.setPreviewEnabled(false);
		mNumberView.setOnKeyboardActionListener(listener);

		remarks.addTextChangedListener(textWatcher);

		timeStamp = System.currentTimeMillis();//获取当前系统时间
		date.setText(TimeUtil.getSimMilliDate("yyyy年M月d日", timeStamp));
		time.setText(TimeUtil.getSimMilliDate("H:mm", timeStamp));

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	public void setIcon(int type) {
		this.icon = type;

	}

	public void setLabel(String label) {
		this.label = label;
	}


	public void setEditable(EditText editable) {
		this.et = editable;
	}

	@Override
	public void show() {
		selectType.setImageResource(icon);
		selectCategory.setText(label);
		super.show();
	}

	private int start;
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

			editable = et.getText();
			start = et.getSelectionStart();
			//删除键操作
			if (i == BACKSPACE) {
				// 回退键,删除字符
				if (editable != null && editable.length() > 0) {
					if (start > 0) {
						editable.delete(start - 1, start);
					}
				}
			} else if (i == SUBMIT) {
				Toast.makeText(mContext, "提交", Toast.LENGTH_SHORT).show();

			} else if (i == DATE_SELECT) {
				selectDate();
			} else {
				//都不是自定义的值就插入
				editable.insert(start, Character.toString((char) i));
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
			if (wordCount.getVisibility() == View.GONE) {
				wordCount.setVisibility(View.VISIBLE);
			}
			if (remarks.length() == 0) {
				wordCount.setVisibility(View.GONE);
			}
			wordCount.setText(remarks.length() + "/100");
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
				date.setText(d);
				date.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

				DatePickerUtil.showTimePicker(mContext, new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						String da = hourOfDay + ":" + minute;
						String target = d + da;
						timeStamp = TimeUtil.getStringToDate(target, "yyyy年MM月dd日HH:mm");
						time.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
						time.setText(da);

					}
				});
			}
		});
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
