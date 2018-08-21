package com.github.wp17.lina.net.codec.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.message.IMessage;
import com.github.wp17.lina.common.net.LogicSession;
import com.github.wp17.lina.net.connection.MinaSession;
import com.github.wp17.lina.net.message.TestMessage;
import com.github.wp17.lina.net.packet.MinaInbound;
import com.github.wp17.lina.net.packet.Packet;
import com.github.wp17.lina.net.packet.PacketHeader;
import com.github.wp17.lina.server.message.MessageModule;

@Component
public class MinaLengthFieldDecoder extends CumulativeProtocolDecoder {
	private static final Logger LOGGER = LoggerFactory.getLogger(MinaLengthFieldDecoder.class);
	private static final AttributeKey HEADER_KEY = new AttributeKey(MinaLengthFieldDecoder.class, "netpacket.header");
	@Autowired private MessageModule messageModule;
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer ioBuffer, ProtocolDecoderOutput output) throws Exception {
		if (ioBuffer.remaining() < Packet.HEADER_LEAGTH) {
			return false;
		}
		
		PacketHeader header = (PacketHeader) session.getAttribute(HEADER_KEY);
		if (null == header) {
			header = new PacketHeader();
			header.decode(new MinaInbound(ioBuffer));
			session.setAttribute(HEADER_KEY, header);
		}
		
		int bodyLength = header.getBodyLength();
		if (ioBuffer.remaining() < bodyLength) {
			return false;
		}
		session.removeAttribute(HEADER_KEY);
		
		short msgId = header.getMsgID();
		
//		IMessage message = messageModule.getMessage(msgId);
//		if (null == message) {
//			LOGGER.error("the message of msgid: "+msgId+" is null", new NullPointerException());
//			return false;
//		}
		
//		LogicSession player = MinaSession.getLogicSession(session);
//		if (null == player) {
//			player = new MinaSession(session);
//		}
//		
//		int except = player.getNextIncomingSeq();
//		if (header.getSeq() != except) {
//			LOGGER.error("message disorder, id: "+msgId+" except is "+except+", actual is "+header.getSeq());
//			return false;
//		}
		
		byte[] bytes = new byte[bodyLength];
		ioBuffer.get(bytes);
		IoBuffer body = IoBuffer.wrap(bytes);
		
		IMessage message = new TestMessage();
		
		message.decode(new MinaInbound(body));
		output.write(message);
		return true;
	}
}