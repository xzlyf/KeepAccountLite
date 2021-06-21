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
import com.xz.kal.entity.Category;

import butterknife.BindView;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/21
 */
public class CategoryAdapter extends BaseRecyclerAdapter<Category> {

	public CategoryAdapter(Context context) {
		super(context);
	}

	@Override
	protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
		ViewHolder viewHolder = (ViewHolder) holder;
		Category category = mList.get(position);
		viewHolder.idName.setText(category.getLabel());
		viewHolder.idType.setImageResource(category.getIcon());
	}

	@Override
	protected BaseRecyclerViewHolder createNewViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(mInflater.inflate(R.layout.item_category, parent, false));
	}

	public class ViewHolder extends BaseRecyclerViewHolder {
		@BindView(R.id.id_type)
		ImageView idType;
		@BindView(R.id.id_name)
		TextView idName;

		ViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}
