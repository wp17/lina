package com.github.wp17.lina.server;

public class ServerHolder {
	private static Server s;
	public static final void server(Server server){
		s = server;
	}
	public static final Server server(){
		return s;
	}
}
