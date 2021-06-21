package com.xz.kal.entity;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/16
 */
public class Category {
	private int id;
	private String label;
	private int icon;
	private String inout;

	public Category() {
	}

	public Category(int id, String label, int icon, String inout) {
		this.id = id;
		this.label = label;
		this.icon = icon;
		this.inout = inout;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getInout() {
		return inout;
	}

	public void setInout(String inout) {
		this.inout = inout;
	}
}
