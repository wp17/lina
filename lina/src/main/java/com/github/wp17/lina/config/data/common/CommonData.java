package com.github.wp17.lina.config.data.common;

import com.github.wp17.lina.config.csv.Charset;
import com.github.wp17.lina.config.csv.Path;

@Charset
@Path("/config/common.csv")
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