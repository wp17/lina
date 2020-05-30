package com.github.wp17.lina.game.module.mq;

import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.common.util.RabbitConst;
import com.github.wp17.lina.game.GameApplication;
import com.github.wp17.lina.game.module.AbsModule;
import com.github.wp17.lina.game.module.log.LogModule;
import com.github.wp17.lina.game.module.msg.GameMessageModule;
import com.github.wp17.lina.game.spring.SpringContext;
import com.github.wp17.lina.proto.msg.G2GProto;
import com.github.wp17.lina.proto.msg.Options;
import com.google.protobuf.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class GameMQModule implements AbsModule {
    private GameMQModule() {
    }

    private static final GameMQModule instance = new GameMQModule();

    public static GameMQModule getInstance() {
        return instance;
    }

    private RabbitTemplate template;

    @Override
    public void init() {
        template = SpringContext.getInstance().getBean("rabbitTemplate", RabbitTemplate.class);
//        SpringContext.getInstance().getContext().register(G2GRabbitListener.class);
//        SpringContext.getInstance().getContext().stop();
    }

    public void sendMsg(String queueName, String msg) {
        template.convertAndSend(queueName, msg);
    }

    public void sendMsg(String queueName, Message msg) {
        DefaultPacket packet = buildPacket(msg);
        template.convertAndSend(queueName, packet.toByte());
    }

    public void g2gBroadcast(Message msg) {
        DefaultPacket packet = buildPacket(msg);
        template.convertAndSend(RabbitConst.g2g_broadcast_exchange, null, packet.toByte());
    }

    private DefaultPacket buildPacket(Message msg) {
        byte[] body = msg.toByteArray();
        int msgId = msg.getDescriptorForType().getOptions().getExtension(Options.messageId);

        DefaultPacket packet = new DefaultPacket(body);
        packet.setMsgID((short) msgId);
        packet.setBodyLength(body.length);
        packet.setCheckSum(-1);
        packet.setSeq(-1);

        return packet;
    }

    @Override
    public void shutdown() {

    }

    public static void main(String[] args) throws InterruptedException {
        GameApplication.main(args);

        G2GProto.G2GExpireCacheReq.Builder builder = G2GProto.G2GExpireCacheReq.newBuilder();
        builder.setCacheKey(""+System.currentTimeMillis());
        for (int i = 0; i < 100; i++) {
            GameMQModule.getInstance().g2gBroadcast(builder.build());
            Thread.sleep(2000);
        }

//        for (int i = 0; i < 3000; i++) {
//            G2RProto.G2RUpdateReq.Builder builder = G2RProto.G2RUpdateReq.newBuilder();
//            builder.setRoleId(i);
//            builder.setScore(i);
//            GameMQModule.getInstance().sendMsg(RabbitConst.g2r_update_queue, builder.build());
////            Thread.sleep(2000);
//            getInstance().sendMsg(RabbitConst.g2r_update_queue, ""+System.currentTimeMillis());
//        }
    }
}
