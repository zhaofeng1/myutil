package com.zf.util.list;

public enum Character {

	IN("内向"), OUT("外向"), BOTH("太监"), UNKNOWN("不知道");

	private String name;

	private Character(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}

}