package com.github.wp17.lina.rank.mq;

import com.github.wp17.lina.common.net.DefaultPacket;
import com.github.wp17.lina.common.net.IPacket;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "${g2g_broadcast_queue}")
public class G2GRabbitListener {
    @RabbitHandler
    public void receive(byte[] data) {
        byte[] header = new byte[IPacket.HEADER_LEAGTH];
        byte[] body = new byte[data.length - IPacket.HEADER_LEAGTH];
        System.arraycopy(data, 0, header, 0, header.length);
        System.arraycopy(data, IPacket.HEADER_LEAGTH, body, 0, body.length);

        DefaultPacket packet = new DefaultPacket(body);
        packet.header.decode(header);

        System.out.println(packet.header);
    }

    @RabbitHandler
    public void receive(String data) {
        System.out.println(data);
    }
}
