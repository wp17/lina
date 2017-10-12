package com.github.wp17.lina.net.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.net.connection.LogicSession;
import com.github.wp17.lina.net.packet.Outbound;
import com.github.wp17.lina.net.packet.Packet;

public class Encoder extends ProtocolEncoderAdapter {

	@Override
	public void encode(IoSession session, Object object, ProtocolEncoderOutput output) throws Exception {
		
		Outbound out = new Outbound();
		IMessage message = (IMessage) object;
		message.encode(out);
		
		Packet packet = new Packet(out.getBuffer());
		LogicSession player = LogicSession.getLogicSession(session);
		packet.setSeq(player.getNextOutgoingSeq());
		packet.setMsgID(message.getMsgID());
		packet.setCheckSum(-1);
		
		output.write(packet.encode());
	}
}
