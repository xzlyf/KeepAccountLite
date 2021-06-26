package com.xz.kal.dialog;

import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.xz.kal.R;
import com.xz.kal.adapter.WalletAdapter;
import com.xz.kal.base.BaseDialog;
import com.xz.kal.base.BaseRecyclerAdapter;
import com.xz.kal.entity.Wallet;
import com.xz.kal.utils.SpacesItemDecorationVH;

import java.util.ArrayList;
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
	@BindView(R.id.recycler_wallet)
	RecyclerView recyclerView;
	private OnSelectItemListener mListener;
	private List<Wallet> mList;
	private WalletAdapter adapter;

	public BagSelectDialog(Context context) {
		super(context);
		mList = new ArrayList<>();
	}

	@Override
	protected int getLayoutResource() {
		return R.layout.dialog_bag;
	}

	@Override
	protected void initData() {
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		//设置Dialog样式
		Window window = getWindow();
		if (window != null) {
			window.setWindowAnimations(R.style.CommonDialog_inOut);
		}
		GridLayoutManager gm = new GridLayoutManager(mContext, 4);
		recyclerView.setLayoutManager(gm);
		recyclerView.addItemDecoration(new SpacesItemDecorationVH(20));
		adapter = new WalletAdapter(mContext);
		recyclerView.setAdapter(adapter);
		adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<Wallet>() {
			@Override
			public void onClick(Wallet wallet) {
				if (mListener != null) {
					mListener.onSelect(wallet);
				}
				BagSelectDialog.this.dismiss();
			}
		});
	}

	public void setWallList(List<Wallet> list) {
		mList.clear();
		mList.addAll(list);

	}


	public void setOnSelectItemListener(OnSelectItemListener listener) {
		this.mListener = listener;
	}


	@Override
	public void show() {
		adapter.refresh(mList);
		super.show();
	}

	public interface OnSelectItemListener {
		void onSelect(Wallet wallet);
	}
}
