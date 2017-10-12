package com.github.wp17.lina;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.github.wp17.lina.message.msgs.TestMessage;
import com.github.wp17.lina.net.codec.CodecFactory;
import com.github.wp17.lina.net.codec.Decoder;
import com.github.wp17.lina.net.codec.Encoder;
import com.github.wp17.lina.net.connection.LogicSession;

public class Client {

	public static void main(String[] args) {
		NioSocketConnector connector = new NioSocketConnector();
		
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CodecFactory(new Decoder(), new Encoder())));
		
		connector.setHandler(new IoHandlerAdapter());
		
		ConnectFuture connectFuture = connector.connect(new InetSocketAddress(8000));
		
		connectFuture.addListener(new IoFutureListener<ConnectFuture>() {

			@Override
			public void operationComplete(ConnectFuture future) {
				IoSession session = future.getSession();
				
				new LogicSession(session);
				for (int i = 0; i < 20; i++) {
					TestMessage testMessage = new TestMessage();
					testMessage.setId(i);
					testMessage.setAge((byte) 28);
					testMessage.setName("peter");
					session.write(testMessage);
				}
			}
		});
	}
}
