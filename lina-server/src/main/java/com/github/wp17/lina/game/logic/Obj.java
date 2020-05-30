package com.github.wp17.lina.game.logic;

import com.github.wp17.lina.common.interfaces.IObj;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Obj implements IObj {
	private static final AtomicInteger idGenerator = new AtomicInteger();
	
	private final int objId;
	private final ObjType objType;

	public Obj(ObjType objType) {
		this.objType = objType;
		objId = idGenerator.incrementAndGet();
	}

	public int getObjId() {
		return objId;
	}

	public ObjType getObjType() {
		return objType;
	}
}
