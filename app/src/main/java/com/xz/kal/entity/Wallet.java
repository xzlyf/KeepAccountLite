package com.xz.kal.entity;

/**
 * @author czr
 * @email czr2001@outlook.com
 * @date 2021/6/24
 */
public class Wallet {
	private int id;
	private String name;

	public Wallet() {
	}


	public Wallet(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
