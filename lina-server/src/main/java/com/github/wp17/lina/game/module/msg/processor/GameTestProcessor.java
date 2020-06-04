package com.github.wp17.lina.game.module.msg.processor;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.msg.MessageID;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.game.logic.Role;
import com.github.wp17.lina.game.module.msg.GameMsgId;
import com.github.wp17.lina.game.module.msg.RoleMsgProcessor;
import com.github.wp17.lina.proto.msg.TestProto;
import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;

@MessageID(GameMsgId.test)
public class GameTestProcessor extends RoleMsgProcessor {
    @Override
    protected void process(Role role, DefaultPacket packet) {
        try {
            TestProto.Test test = TestProto.Test.parseFrom(packet.body);

            JsonFormat format = new JsonFormat();
            System.out.println(format.printToString(test));

            role.sendMsg(test);
        } catch (InvalidProtocolBufferException e) {
            LoggerProvider.addExceptionLog("消息解析失败", e);
        }
    }
}
