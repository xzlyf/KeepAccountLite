package com.xz.kal.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.xz.kal.R;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/21
 */
public class EmptyTipsRecyclerView extends RecyclerView {
	private Bitmap emptyIconBitmap;
	private Paint paint;

	public EmptyTipsRecyclerView(Context context) {
		this(context, null);
	}

	public EmptyTipsRecyclerView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EmptyTipsRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EmptyTipsRecyclerView);
		int iconSrc = ta.getResourceId(R.styleable.EmptyTipsRecyclerView_empty_image, 0);
		ta.recycle();
		if (iconSrc != 0) {
			emptyIconBitmap = BitmapFactory.decodeResource(getResources(), iconSrc);
			paint = new Paint();

			int width = emptyIconBitmap.getWidth();
			//如果指定的资源图片大于500px，则通过计算两图的缩放倍率，让指定的资源图片尽可能的接近500px
			if (width > 500) {
				float zoom = 500.0f / (float) width;
				int height = emptyIconBitmap.getHeight();
				Matrix matrix = new Matrix();
				matrix.postScale(zoom, zoom);
				emptyIconBitmap = Bitmap.createBitmap(emptyIconBitmap, 0, 0, width, height, matrix, true);
			}

		}
	}


	@Override
	public void onDraw(Canvas c) {
		// TODO: 2021/6/21 解决图标不显示的问题
		//如果子控件等于0，显示图像
		if (getChildCount() == 0 && emptyIconBitmap != null) {
			c.drawBitmap(emptyIconBitmap, (float) ((getMeasuredWidth() / 2) - (emptyIconBitmap.getWidth() / 2))
					, (float) ((getMeasuredHeight() / 2) - (emptyIconBitmap.getHeight() / 2)), paint);
		}
		super.onDraw(c);
	}


}