package com.github.wp17.lina.client.module.msg.processor.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wp17.lina.client.login.PrivateLogin;
import com.github.wp17.lina.client.module.msg.ClientMsgId;
import com.github.wp17.lina.client.module.msg.processor.NettyMsgProcessor;
import com.github.wp17.lina.common.msg.MessageID;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.common.net.NettySession;
import com.github.wp17.lina.proto.msg.LoginProto;
import com.github.wp17.lina.proto.msg.Options;
import com.github.wp17.lina.util.encrypt.Crypt;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MessageID(ClientMsgId.g2c_handshake_syn)
public class HandshakeSynProcessor extends NettyMsgProcessor {
    @Override
    public void process(NettySession session, DefaultPacket packet) {
        LoginProto.HandshakeSyn syn = null;
        try {
           syn = LoginProto.HandshakeSyn.parseFrom(packet.body);
        } catch (InvalidProtocolBufferException e) {
            log.info("", e);
        }
        if (null != syn) {
            int salt = syn.getSalt();
            session.setSalt(salt);
            LoginProto.HandshakeAck.Builder ack = LoginProto.HandshakeAck.newBuilder();
            session.sendMsg(buildPacket(ack.build(), session));

            PrivateLogin login = new PrivateLogin();
            JSONObject jsonObject = login.login("111", "111");
            LoginProto.C2GLoginReq.Builder req = LoginProto.C2GLoginReq.newBuilder();
            req.setPlatform(1);
            req.setUsername("111");
            req.setPassword(jsonObject.getString("token"));
            session.sendMsg(buildPacket(req.build(), session));
        }
    }
}
