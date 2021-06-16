package com.xz.kal.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xz.kal.R;
import com.xz.kal.base.BaseRecyclerAdapter;
import com.xz.kal.base.BaseRecyclerViewHolder;
import com.xz.kal.entity.Bill;

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

	}

	@Override
	protected BaseRecyclerViewHolder createNewViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(mInflater.inflate(R.layout.item_bill, parent, false));
	}

	class ViewHolder extends BaseRecyclerViewHolder {
		ViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
