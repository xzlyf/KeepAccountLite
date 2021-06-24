package com.xz.kal.dialog;

import android.content.Context;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xz.kal.R;
import com.xz.kal.base.BaseDialog;
import com.xz.kal.entity.Wallet;

import java.util.List;

import butterknife.BindView;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/24
 */
public class BagSelectDialog extends BaseDialog {

	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.radio_group)
	RadioGroup radioGroup;
	private OnSelectItemListener mListener;

	public BagSelectDialog(Context context) {
		super(context);
	}

	@Override
	protected int getLayoutResource() {
		return R.layout.dialog_bag;
	}

	@Override
	protected void initData() {
		setCancelable(true);
		setCanceledOnTouchOutside(true);
	}

	public void setWallList(List<Wallet> list) {

	}

	public void setOnSelectItemListener(OnSelectItemListener listener) {
		this.mListener = listener;
	}

	public interface OnSelectItemListener {
		void onSelect(Wallet wallet);
	}
}
