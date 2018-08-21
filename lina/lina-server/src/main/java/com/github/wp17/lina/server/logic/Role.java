package com.github.wp17.lina.server.logic;

import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.net.LogicSession;
import com.github.wp17.lina.server.line.LineServer;

public class Role extends Sprite{
	private LineServer lineServer;
	
	public Role(LogicSession session, ObjType objType) {
		super(session, objType);
	}

	public Long getRoleUuid(){
		return 1L;
	}
	
	public void sendMsg(IMessage message){
		getSession().sendMsg(message);
	}

	public LineServer getLineServer() {
		return lineServer;
	}

	public void setLineServer(LineServer lineServer) {
		this.lineServer = lineServer;
	}
	
	public void downline(){}
}
