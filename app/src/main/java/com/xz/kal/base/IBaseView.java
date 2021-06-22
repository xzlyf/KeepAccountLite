package com.xz.kal.base;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/22
 */
public interface IBaseView {
	void showLoading(String message);

	void disLoading();

	void sToast(String message);
}
