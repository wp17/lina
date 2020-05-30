package com.github.wp17.lina.game.logic;

import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.game.logic.manager.RoleFriendManager;
import com.github.wp17.lina.game.logic.manager.RoleRankManager;
import com.github.wp17.lina.game.logic.manager.login.RoleLoginManager;
import com.github.wp17.lina.game.module.line.LineServerModule;
import com.github.wp17.lina.game.module.msg.GameMessageModule;
import com.github.wp17.lina.game.module.line.LineServer;
import com.google.protobuf.MessageLite;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicBoolean;

@Getter
public class Role extends Sprite {

    @Setter
    private LineServer lineServer;
    private AtomicBoolean downLine = new AtomicBoolean(false);
    private AtomicBoolean downLining = new AtomicBoolean(false);

    private RoleLoginManager loginManager = new RoleLoginManager(this);
    private RoleFriendManager friendManager = new RoleFriendManager(this);
    private RoleRankManager roleRankManager = new RoleRankManager(this);

    public Role(AbstractSession session, ObjType objType) {
        super(session, objType);
    }

    public Long getRoleUuid() {
        return 1L;
    }

    public void sendMsg(MessageLite message) {
        byte[] body = message.toByteArray();
        DefaultPacket packet = new DefaultPacket(body);
        packet.setBodyLength(body.length);
        packet.setSeq(session.getNextOutgoingSeq());
        packet.setMsgID(GameMessageModule.getInstance().getMessageID(message.getClass()));
        packet.setCheckSum(-1);
        getSession().sendMsg(packet);
    }

    public void processMsg(AbstractPacket packet) {
        GameMessageModule.getInstance().process(getSession(), (DefaultPacket)packet);
    }

    @Override
    public void downLine() {
        if (downLine.get() || downLining.get()) return;
        downLining.compareAndSet(false, true);
        friendManager.downline();
        LineServerModule.getInstance().roleDownLine(this);
        session.close();
        downLine.compareAndSet(false, true);
    }

    public boolean verified() {
        return  session.verified();
    }

    public void verified(boolean verified) {
        session.verified(verified);
    }

    public AbstractPacket pollMsg() {
        return session.pollMsg();
    }
}
