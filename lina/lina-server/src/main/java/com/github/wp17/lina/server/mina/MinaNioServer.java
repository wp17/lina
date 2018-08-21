package com.github.wp17.lina.server.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.github.wp17.lina.net.codec.mina.CodecFactory;
import com.github.wp17.lina.net.codec.mina.MinaLengthFieldDecoder;
import com.github.wp17.lina.net.codec.mina.MinaNioEncoder;
import com.github.wp17.lina.net.handler.MinaLogicHandler;
import com.github.wp17.lina.server.NioServer;

public class MinaNioServer extends NioServer {
	private NioSocketAcceptor acceptor;
	
	public MinaNioServer(List<SocketAddress> addresses, int id){
		super(1);
	}
	
	@Override
	public void shutdown(){
		acceptor.dispose();
	}
	
	@Override
	protected void aftStartup() {
		super.aftStartup();
	}
	
	@Override
	protected void bind() {
		this.acceptor = new NioSocketAcceptor();
		acceptor.setReuseAddress(true);
		acceptor.setBacklog(6000);
		acceptor.getSessionConfig().setTcpNoDelay(true);
		acceptor.getSessionConfig().setSoLinger(-1);
		
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 300);
		acceptor.getFilterChain().addLast("readEexcutor", new ExecutorFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CodecFactory(new MinaLengthFieldDecoder(), new MinaNioEncoder())));
		acceptor.getFilterChain().addLast("writeExecutor", new ExecutorFilter(IoEventType.WRITE));
		acceptor.setHandler(new MinaLogicHandler());
		try {
			acceptor.bind(new InetSocketAddress(8010));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void preStartup() {
		
	}
}
