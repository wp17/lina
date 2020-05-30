package com.github.wp17.lina.game.module.msg.processor;

import com.github.wp17.lina.common.msg.MessageClazz;
import com.github.wp17.lina.common.msg.MessageID;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.game.logic.Role;
import com.github.wp17.lina.game.module.msg.GameMsgId;
import com.github.wp17.lina.game.module.msg.RoleMsgProcessor;
import com.github.wp17.lina.proto.msg.LoginProto;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MessageID(GameMsgId.c2g_login_req)
@MessageClazz(LoginProto.C2GLoginReq.class)
public class C2GLoginProcessor extends RoleMsgProcessor {
    @Override
    protected void process(Role role, DefaultPacket msg) {
        LoginProto.C2GLoginReq req = null;
        try {
            req = LoginProto.C2GLoginReq.parseFrom(msg.body);
        } catch (InvalidProtocolBufferException e) {
            log.error("", e);
            return;
        }
       role.getLoginManager().processLogin(req);
    }
}
