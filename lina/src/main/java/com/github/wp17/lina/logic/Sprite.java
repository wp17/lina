package com.github.wp17.lina.logic;

import com.github.wp17.lina.net.connection.LogicSession;

public abstract class Sprite extends LogicObj{
	private final LogicSession session;
	
	public Sprite(LogicSession session, ObjType objType){
		super(objType);
		this.session = session;
		session.setObj(this);
	}
	
	public LogicSession getSession() {
		return session;
	}
}
