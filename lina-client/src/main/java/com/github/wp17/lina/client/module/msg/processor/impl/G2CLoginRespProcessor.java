package com.github.wp17.lina.client.module.msg.processor.impl;

import com.github.wp17.lina.client.module.msg.ClientMsgId;
import com.github.wp17.lina.client.module.msg.processor.NettyMsgProcessor;
import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.msg.MessageID;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.common.net.NettySession;
import com.github.wp17.lina.proto.msg.LoginProto;
import com.github.wp17.lina.proto.msg.TestProto;
import com.google.protobuf.InvalidProtocolBufferException;

@MessageID(ClientMsgId.g2c_login_resp)
public class G2CLoginRespProcessor extends NettyMsgProcessor {

    @Override
    public void process(NettySession session, DefaultPacket packet) {
        System.out.println(packet.getMsgId());
        try {
            LoginProto.G2CLoginResp loginResp = LoginProto.G2CLoginResp.parseFrom(packet.body);
            if (loginResp.getResult() == 0) {
                TestProto.Test.Builder test = TestProto.Test.newBuilder();
                session.sendMsg(buildPacket(test.build(), session));
            }else {
                LoggerProvider.addExceptionLog("登陆失败");
            }
        } catch (InvalidProtocolBufferException e) {
            LoggerProvider.addExceptionLog("登陆失败", e);
        }
    }
}
