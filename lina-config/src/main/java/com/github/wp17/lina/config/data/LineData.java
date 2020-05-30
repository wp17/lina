package com.github.wp17.lina.config.data;

import com.github.wp17.lina.common.csv.ConfigData;

@ConfigData(charset="utf-8", path="/lina-server/src/main/resources/csv/line.csv")
public class LineData {
	private int server_id;
	private int id;
	private String name;
	private int enable;
	
	public int getServer_id() {
		return server_id;
	}
	public void setServer_id(int server_id) {
		this.server_id = server_id;
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
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
}
