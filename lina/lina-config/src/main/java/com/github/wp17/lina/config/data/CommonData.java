package com.github.wp17.lina.config.data;

import com.github.wp17.lina.util.csv.ConfigData;

@ConfigData(charset="utf-8", path="/config/common.csv")
public class CommonData {
	
	private int id;
	private int value;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}