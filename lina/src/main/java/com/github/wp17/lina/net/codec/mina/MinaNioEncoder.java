package com.github.wp17.lina.net.codec.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.github.wp17.lina.message.IMessage;
import com.github.wp17.lina.net.connection.LogicSession;
import com.github.wp17.lina.net.connection.MinaSession;
import com.github.wp17.lina.net.packet.MinaPacket;

public class MinaNioEncoder extends ProtocolEncoderAdapter {

	@Override
	public void encode(IoSession session, Object object, ProtocolEncoderOutput output) throws Exception {
		LogicSession player = MinaSession.getLogicSession(session);
		MinaPacket packet = new MinaPacket();
		packet.setSeq(player.getNextOutgoingSeq());
		
		IMessage message = (IMessage) object;
		output.write(packet.encode(message));
	}
}
