package com.xz.kal.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xz.kal.R;
import com.xz.kal.base.BaseRecyclerAdapter;
import com.xz.kal.base.BaseRecyclerViewHolder;
import com.xz.kal.constant.Local;
import com.xz.kal.entity.Bill;
import com.xz.kal.entity.Category;

import butterknife.BindView;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/17
 */
public class BillAdapterV2 extends BaseRecyclerAdapter<Bill> {


	public BillAdapterV2(Context context) {
		super(context);
	}

	@Override
	protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
		ViewHolder viewHolder = (ViewHolder) holder;
		Bill bill = mList.get(position);
		Category category = bill.getCategory();
		viewHolder.icCategory.setImageResource(category.getIcon());
		viewHolder.tvLabel.setText(category.getLabel());
		if (bill.getInout().contentEquals(Local.SYMBOL_OUT)) {
			viewHolder.tvMoney.setText(String.format("-%s%s", Local.symbol, bill.getMoney()));
			viewHolder.tvMoney.setTextColor(mContext.getResources().getColor(R.color.primary_text));
		} else if (bill.getInout().contentEquals(Local.SYMBOL_IN)) {
			viewHolder.tvMoney.setText(String.format("+%s%s", Local.symbol, bill.getMoney()));
			viewHolder.tvMoney.setTextColor(mContext.getResources().getColor(R.color.green_2));
		}
	}

	@Override
	protected BaseRecyclerViewHolder createNewViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(mInflater.inflate(R.layout.item_bill_v2, parent, false));
	}

	class ViewHolder extends BaseRecyclerViewHolder {
		@BindView(R.id.ic_category)
		ImageView icCategory;
		@BindView(R.id.tv_label)
		TextView tvLabel;
		@BindView(R.id.tv_money)
		TextView tvMoney;

		ViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
