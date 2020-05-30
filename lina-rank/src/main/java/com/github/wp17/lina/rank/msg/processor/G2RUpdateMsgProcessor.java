package com.github.wp17.lina.rank.msg.processor;

import com.github.wp17.lina.common.msg.CommMsgId;
import com.github.wp17.lina.common.msg.MessageClazz;
import com.github.wp17.lina.common.msg.MessageID;
import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.proto.msg.G2RProto;
import com.github.wp17.lina.rank.db.mapper.ScoreRecord;
import com.github.wp17.lina.rank.db.provider.ScoreRankDataProvider;
import com.github.wp17.lina.rank.module.RankLogicModule;
import com.github.wp17.lina.rank.msg.MQMsgProcessor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MessageID(CommMsgId.g2r_update_req)
@MessageClazz(G2RProto.G2RUpdateReq.class)
public class G2RUpdateMsgProcessor extends MQMsgProcessor {

    @Override
    public void process(DefaultPacket packet) {
        try {
            G2RProto.G2RUpdateReq req = G2RProto.G2RUpdateReq.parseFrom(packet.body);
            ScoreRecord record = new ScoreRecord();
            record.setRoleId(req.getRoleId());
            record.setScore((int)req.getScore());
            record.setTime(System.currentTimeMillis());
            RankLogicModule.getInstance().saveOrUpdateScore(record);
        } catch (InvalidProtocolBufferException e) {
            log.error("", e);
        }
    }
}




















