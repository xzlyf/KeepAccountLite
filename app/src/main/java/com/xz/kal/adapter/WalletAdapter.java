package com.xz.kal.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.xz.kal.R;
import com.xz.kal.base.BaseRecyclerAdapter;
import com.xz.kal.base.BaseRecyclerViewHolder;
import com.xz.kal.entity.Wallet;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/26
 */
public class WalletAdapter extends BaseRecyclerAdapter<Wallet> {

	public WalletAdapter(Context context) {
		super(context);
	}

	@Override
	protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
		ViewHolder viewHolder = (ViewHolder) holder;
		Wallet wallet = mList.get(position);
		viewHolder.icWallet.setImageResource(wallet.getIcon());
		viewHolder.tvName.setText(wallet.getName());
	}

	@Override
	protected BaseRecyclerViewHolder createNewViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(mInflater.inflate(R.layout.item_wallet, parent, false));
	}


	class ViewHolder extends BaseRecyclerViewHolder {

		@BindView(R.id.ic_wallet)
		ImageView icWallet;
		@BindView(R.id.tv_name)
		TextView tvName;

		ViewHolder(@NonNull View itemView) {
			super(itemView);
		}

		@OnTouch(R.id.ic_wallet)
		void onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150).start();
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					if (mOnItemClickListener != null) {
						mOnItemClickListener.onClick(mList.get(getLayoutPosition()));
					}
				case MotionEvent.ACTION_CANCEL:
					v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150).start();
					break;
			}
		}


	}
}
