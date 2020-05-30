package com.github.wp17.lina.game.module.msg.processor;

import com.github.wp17.lina.common.msg.MessageClazz;
import com.github.wp17.lina.common.msg.MessageID;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.game.module.msg.MQMsgProcessor;
import com.github.wp17.lina.game.module.msg.GameMsgId;
import com.github.wp17.lina.game.module.cache.CacheModule;
import com.github.wp17.lina.proto.msg.G2GProto;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@MessageID(GameMsgId.g2g_expire_req)
@MessageClazz(G2GProto.G2GExpireCacheReq.class)
public class G2GExpireCacheProcessor extends MQMsgProcessor {
    @Override
    public void process(DefaultPacket packet) {
        G2GProto.G2GExpireCacheReq req = null;
        try {
            req = G2GProto.G2GExpireCacheReq.parseFrom(packet.body);
        } catch (InvalidProtocolBufferException e) {
            log.error("", e);
        }
        if (Objects.nonNull(req)) {
            CacheModule.getInstance().invalidateLocal(req.getCacheKey());
        }
    }
}
