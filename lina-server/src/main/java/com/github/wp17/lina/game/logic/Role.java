package com.github.wp17.lina.game.logic;

import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.common.net.IConnection;
import com.github.wp17.lina.game.logic.manager.RoleFriendManager;
import com.github.wp17.lina.game.logic.manager.RoleRankManager;
import com.github.wp17.lina.game.logic.manager.RoleRoomManager;
import com.github.wp17.lina.game.logic.manager.login.RoleLoginManager;
import com.github.wp17.lina.game.module.line.LineServerModule;
import com.github.wp17.lina.game.module.msg.GameMessageModule;
import com.github.wp17.lina.game.module.line.LineServer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Getter
public class Role extends Sprite implements IConnection {
    @Setter
    private volatile LineServer lineServer;
    private AtomicBoolean downLine = new AtomicBoolean(false);
    private AtomicBoolean downLining = new AtomicBoolean(false);

    private RoleLoginManager loginManager = new RoleLoginManager(this);
    private RoleFriendManager friendManager = new RoleFriendManager(this);
    private RoleRankManager rankManager = new RoleRankManager(this);
    private RoleRoomManager roomManager = new RoleRoomManager(this);

    private final long roleUuid;
    public Role(long roleUuid, AbstractSession session, ObjType objType) {
        super(session, objType);
        this.roleUuid = roleUuid;
    }

    public Long getRoleUuid() {
        return roleUuid;
    }

    public void processMsg(AbstractPacket packet) {
        GameMessageModule.getInstance().process(getSession(), (DefaultPacket)packet);
    }

    @Override
    public void downLine() {
        if (downLine.get() || downLining.get()) return;
        downLining.compareAndSet(false, true);
        friendManager.downline();
        roomManager.downLine();

        LineServerModule.getInstance().roleDownLine(this);
        session.close();
        downLine.compareAndSet(false, true);
        log.info("role({}) is down", getRoleUuid());
    }

    public AbstractPacket pollMsg() {
        return session.pollMsg();
    }

    @Override
    public AbstractSession getSession() {
        return super.session;
    }

    @Override
    public void upFrame() {
        AbstractPacket packet = null;
        while ((packet = pollMsg()) != null) {
            processMsg(packet);
        }
    }

    public void changeLine(LineServer newLine) {
        LineServerModule.getInstance().changeLine(this, newLine);
    }
}
