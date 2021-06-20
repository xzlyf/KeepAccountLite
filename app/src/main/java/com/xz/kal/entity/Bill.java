package com.xz.kal.entity;

import java.util.Date;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/16
 */
public class Bill {
	private int id;
	private int categoryId;
	private String inout;
	private double money;
	private String remark;
	private Date updateTime;
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getInout() {
		return inout;
	}

	public void setInout(String inout) {
		this.inout = inout;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Bill{" +
				"id=" + id +
				", categoryId=" + categoryId +
				", inout='" + inout + '\'' +
				", money=" + money +
				", remark='" + remark + '\'' +
				", updateTime=" + updateTime +
				", createTime=" + createTime +
				'}';
	}
}
