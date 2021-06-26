package com.xz.kal.sql;

import com.xz.kal.R;
import com.xz.kal.entity.Category;
import com.xz.kal.entity.Wallet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/19
 * <p>
 * 默认数据生成
 */
public class DefaultData {
	private static DefaultData instance;


	private DefaultData() {

	}

	public static DefaultData getInstance() {
		if (instance == null) {
			synchronized (DefaultData.class) {
				if (instance == null) {
					instance = new DefaultData();
				}
			}
		}
		return instance;
	}

	/**
	 * 生成默认标签数据
	 */
	public List<Category> makeDefaultCategory() {
		List<Category> list = new ArrayList<>();
		list.add(new Category(1, "购物", R.mipmap.ic_gouwu, "out"));
		list.add(new Category(2, "交通", R.mipmap.ic_jiaotong, "out"));
		list.add(new Category(3, "饮食", R.mipmap.ic_noodle, "out"));
		list.add(new Category(4, "通讯", R.mipmap.ic_tongxun, "out"));
		list.add(new Category(5, "娱乐", R.mipmap.ic_yule, "out"));
		list.add(new Category(6, "办公", R.mipmap.ic_bangon, "out"));
		list.add(new Category(7, "宠物", R.mipmap.ic_chongwu, "out"));
		list.add(new Category(8, "服装", R.mipmap.ic_fuzhuang, "out"));
		list.add(new Category(9, "快递", R.mipmap.ic_kuaidi, "out"));
		list.add(new Category(10, "零食", R.mipmap.ic_lingshi, "out"));
		list.add(new Category(11, "礼金", R.mipmap.ic_lijin, "out"));
		list.add(new Category(12, "数码", R.mipmap.ic_shuma, "out"));
		list.add(new Category(13, "医疗", R.mipmap.ic_yiliao, "out"));
		list.add(new Category(14, "运动", R.mipmap.ic_yundong, "out"));
		list.add(new Category(15, "书籍", R.mipmap.ic_shuji, "out"));
		list.add(new Category(16, "旅游", R.mipmap.ic_lvyou, "out"));
		list.add(new Category(17, "日用品", R.mipmap.ic_riyongpin, "out"));
		list.add(new Category(18, "水电", R.mipmap.ic_shuidian, "out"));
		list.add(new Category(19, "维修", R.mipmap.ic_weixiu, "out"));
		list.add(new Category(20, "学习", R.mipmap.ic_xuexi, "out"));
		list.add(new Category(21, "工资", R.mipmap.ic_gongzi, "in"));
		list.add(new Category(22, "奖金", R.mipmap.ic_shengjilijin, "in"));
		list.add(new Category(23, "报销", R.mipmap.ic_baoxiao, "in"));
		list.add(new Category(24, "礼金", R.mipmap.ic_shengjilijin, "in"));
		list.add(new Category(25, "理财", R.mipmap.ic_licai, "in"));
		list.add(new Category(26, "理财", R.mipmap.ic_jianzhi, "in"));
		list.add(new Category(27, "其他收入", R.mipmap.ic_shouru, "in"));
		return list;
	}

	/**
	 * 生成默认钱包
	 *
	 * @return
	 */
	public List<Wallet> makeDefaultWallet() {
		List<Wallet> list = new ArrayList<>();
		Wallet wallet1 = new Wallet(1, "零钱", R.mipmap.ic_lijin);
		list.add(wallet1);
		return list;
	}
}
