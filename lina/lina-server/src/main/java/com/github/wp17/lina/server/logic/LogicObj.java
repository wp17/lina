package com.github.wp17.lina.server.logic;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class LogicObj {
	private static final AtomicInteger IDGENER = new AtomicInteger();
	
	private final int objId;
	private final ObjType objType;

	public LogicObj(ObjType objType) {
		this.objType = objType;
		objId = IDGENER.incrementAndGet();
	}

	public int getObjId() {
		return objId;
	}

	public ObjType getObjType() {
		return objType;
	}
}
