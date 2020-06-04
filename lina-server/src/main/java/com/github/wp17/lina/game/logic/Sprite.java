package com.github.wp17.lina.game.logic;

import com.github.wp17.lina.common.net.AbstractSession;

public abstract class Sprite extends Obj {
    protected final AbstractSession session;

    public Sprite(AbstractSession session, ObjType objType) {
        super(objType);
        this.session = session;
        session.setObj(this);
    }

    public AbstractSession getSession() {
        return session;
    }
}
