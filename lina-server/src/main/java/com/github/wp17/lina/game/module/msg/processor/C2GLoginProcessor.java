package com.github.wp17.lina.game.module.msg.processor;

import com.github.wp17.lina.common.msg.IMsgProcessor;
import com.github.wp17.lina.common.msg.MessageID;
import com.github.wp17.lina.common.net.AbstractPacket;
import com.github.wp17.lina.common.net.AbstractSession;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.common.net.NettySession;
import com.github.wp17.lina.game.auth.AuthModule;
import com.github.wp17.lina.game.auth.AuthResult;
import com.github.wp17.lina.game.auth.IAuth;
import com.github.wp17.lina.game.logic.ObjType;
import com.github.wp17.lina.game.logic.Role;
import com.github.wp17.lina.game.logic.RoleBeanFactory;
import com.github.wp17.lina.game.logic.manager.login.PlatformType;
import com.github.wp17.lina.game.module.cache.CacheKey;
import com.github.wp17.lina.game.module.cache.CacheModule;
import com.github.wp17.lina.game.module.db.mapper.role.RoleInfo;
import com.github.wp17.lina.game.module.db.provider.RoleInfoDataProvider;
import com.github.wp17.lina.game.module.line.LineServerModule;
import com.github.wp17.lina.game.module.msg.GameMsgId;
import com.github.wp17.lina.game.module.net.NetModule;
import com.github.wp17.lina.proto.msg.LoginProto;
import com.github.wp17.lina.proto.msg.Options;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@MessageID(GameMsgId.c2g_login_req)
public class C2GLoginProcessor implements IMsgProcessor {
    @Override
    public void process(AbstractSession aSession, AbstractPacket msg) {
        NettySession session = (NettySession) aSession;
        DefaultPacket packet = (DefaultPacket) msg;
        LoginProto.C2GLoginReq req = null;
        try {
            req = LoginProto.C2GLoginReq.parseFrom(packet.body);
        } catch (InvalidProtocolBufferException e) {
            log.error("", e);
            NetModule.getInstance().remSession(session.getId());
            session.close();
            return;
        }

        String username = req.getUsername();
        String pwd = req.getPassword();
        int platform = req.getPlatform();
        PlatformType platformType = PlatformType.getByType(platform);
        IAuth auth = AuthModule.getInstance().getByType(platformType);
        if (Objects.isNull(auth)) {
            LoginProto.G2CLoginResp.Builder resp = LoginProto.G2CLoginResp.newBuilder();
            resp.setResult(1);
            resp.setTips("渠道验证失败");
            sendMsg(resp.build(), session);
            NetModule.getInstance().remSession(session.getId());
            session.close();
            return;
        }

        AuthResult result = auth.auth(username, pwd);
        if (result.getResult() != 0) {
            LoginProto.G2CLoginResp.Builder resp = LoginProto.G2CLoginResp.newBuilder();
            resp.setResult(result.getResult());
            resp.setTips(result.getTips());
            sendMsg(resp.build(), session);
            NetModule.getInstance().remSession(session.getId());
            session.close();
            return;
        }

        RoleInfo roleInfo = CacheModule.getInstance().get(username, CacheKey.user_info_cache);
        if (Objects.isNull(roleInfo)) {
            roleInfo = RoleBeanFactory.newRoleInfo(username, username, "", (byte) 0);
            RoleInfoDataProvider.getInstance().addRoleInfo(roleInfo);
        }

        Role role = new Role(roleInfo.getId(), session, ObjType.ROLE);
        LineServerModule.getInstance().addRole(role);

        LoginProto.G2CLoginResp.Builder resp = LoginProto.G2CLoginResp.newBuilder();
        resp.setResult(0);
        resp.setTips("ok");
        role.sendMsg(resp.build());
    }

    // todo 重复代码
    private void sendMsg(Message message, AbstractSession session) {
        DefaultPacket packet = new DefaultPacket(message.toByteArray());
        int msgId = message.getDescriptorForType().getOptions().getExtension(Options.messageId);
        packet.setMsgId(msgId);
        packet.setCheckSum(-1);
        session.sendMsg(packet);
    }

}
