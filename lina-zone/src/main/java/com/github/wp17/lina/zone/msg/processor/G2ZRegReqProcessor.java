package com.github.wp17.lina.zone.msg.processor;

import com.github.wp17.lina.common.msg.CommMsgId;
import com.github.wp17.lina.common.msg.MessageClazz;
import com.github.wp17.lina.common.msg.MessageID;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.proto.msg.G2ZProto;
import com.github.wp17.lina.zone.register.RegisterModule;
import com.github.wp17.lina.zone.register.ServerTye;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MessageID(CommMsgId.g2z_reg_req)
@MessageClazz(G2ZProto.G2ZGameRegisterReq.class)
public class G2ZRegReqProcessor extends G2ZMsgProcessor {

    @Override
    public void process(DefaultPacket packet) {
        G2ZProto.G2ZGameRegisterReq req = null;
        try {
            req = G2ZProto.G2ZGameRegisterReq.parseFrom(packet.body);
        } catch (InvalidProtocolBufferException e) {
            log.error("", e);
        }
        RegisterModule.getInstance().register(req.getHost(), req.getPort(), ServerTye.game);
    }
}
