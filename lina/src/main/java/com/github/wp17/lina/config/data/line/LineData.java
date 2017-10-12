package com.github.wp17.lina.config.data.line;

import com.github.wp17.lina.config.csv.Charset;
import com.github.wp17.lina.config.csv.Path;

@Charset
@Path("/config/line.csv")
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
