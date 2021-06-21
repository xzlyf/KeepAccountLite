package com.xz.kal.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xz.kal.R;
import com.xz.kal.base.BaseRecyclerAdapter;
import com.xz.kal.base.BaseRecyclerViewHolder;
import com.xz.kal.constant.Local;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;
import com.xz.kal.utils.TimeUtil;

import butterknife.BindView;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/17
 */
public class BillAdapter extends BaseRecyclerAdapter<Bill> {

	public BillAdapter(Context context) {
		super(context);
	}

	@Override
	protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
		ViewHolder viewHolder = (ViewHolder) holder;
		Bill bill = mList.get(position);
		Category category = Local.categories.get(bill.getCategoryId());
		if (category == null) {
			category = new Category();
			category.setLabel("未知");
			category.setIcon(R.mipmap.ic_unknow);
		}
		if (TextUtils.equals(bill.getInout(), Local.SYMBOL_IN)) {
			//收入布局
			viewHolder.layoutOut.setVisibility(View.INVISIBLE);
			viewHolder.inCategory.setText(category.getLabel());
			viewHolder.inIcon.setImageResource(category.getIcon());
			viewHolder.inMoney.setText(String.format("+%s", bill.getMoney()));
			viewHolder.inSymbol.setText(Local.symbol);
			viewHolder.inTime.setText(TimeUtil.getSimMilliDate("HH:mm", bill.getCreateTime().getTime()));
		} else if (TextUtils.equals(bill.getInout(), Local.SYMBOL_OUT)) {
			//支出布局
			viewHolder.layoutIn.setVisibility(View.INVISIBLE);
			viewHolder.outCategory.setText(category.getLabel());
			viewHolder.outIcon.setImageResource(category.getIcon());
			viewHolder.outMoney.setText(String.format("-%s", bill.getMoney()));
			viewHolder.outSymbol.setText(Local.symbol);
			viewHolder.outTime.setText(TimeUtil.getSimMilliDate("HH:mm", bill.getCreateTime().getTime()));
		}
	}

	@Override
	protected BaseRecyclerViewHolder createNewViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(mInflater.inflate(R.layout.item_bill, parent, false));
	}

	class ViewHolder extends BaseRecyclerViewHolder {
		@BindView(R.id.out_time)
		TextView outTime;
		@BindView(R.id.out_symbol)
		TextView outSymbol;
		@BindView(R.id.out_icon)
		ImageView outIcon;
		@BindView(R.id.out_category)
		TextView outCategory;
		@BindView(R.id.out_money)
		TextView outMoney;
		@BindView(R.id.layout_out)
		LinearLayout layoutOut;
		@BindView(R.id.in_time)
		TextView inTime;
		@BindView(R.id.in_symbol)
		TextView inSymbol;
		@BindView(R.id.in_category)
		TextView inCategory;
		@BindView(R.id.in_money)
		TextView inMoney;
		@BindView(R.id.in_icon)
		ImageView inIcon;
		@BindView(R.id.layout_in)
		LinearLayout layoutIn;

		ViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
