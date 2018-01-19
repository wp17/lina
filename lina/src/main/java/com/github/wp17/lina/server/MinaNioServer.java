package com.github.wp17.lina.server;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.List;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.github.wp17.lina.module.ModuleManager;
import com.github.wp17.lina.net.codec.CodecFactory;
import com.github.wp17.lina.net.codec.MinaLengthFieldDecoder;
import com.github.wp17.lina.net.codec.MinaNioEncoder;
import com.github.wp17.lina.net.handler.MinaLogicHandler;

public class MinaNioServer extends NioServer {
	private NioSocketAcceptor acceptor;
	
	public MinaNioServer(List<SocketAddress> addresses, int id){
		super(addresses, 1);
	}
	
	@Override
	public void shutdown(){
		acceptor.dispose();
		ModuleManager.getInstance().destory();
	}
	
	@Override
	protected void aftStartup() {
		super.aftStartup();
	}
	
	@Override
	protected void bind() {
		this.acceptor = new NioSocketAcceptor();
		setReuseAddress(true);
		setBacklog(6000);
		setTcpNoDelay(true);
		setSoLinger(-1);
		
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 300);
		acceptor.getFilterChain().addLast("readEexcutor", new ExecutorFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CodecFactory(new MinaLengthFieldDecoder(), new MinaNioEncoder())));
		acceptor.getFilterChain().addLast("writeExecutor", new ExecutorFilter(IoEventType.WRITE));
		acceptor.setHandler(new MinaLogicHandler());
		try {
			acceptor.bind(getAddresses());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void preStartup() {
		
	}

	@Override
	protected void setBacklog(int backlog) {
		acceptor.setBacklog(backlog);
	}

	@Override
	protected void setTcpNoDelay(boolean tcpNoDelay) {
		acceptor.getSessionConfig().setTcpNoDelay(tcpNoDelay);
	}

	@Override
	protected void setSoLinger(int soLinger) {
		acceptor.getSessionConfig().setSoLinger(soLinger);
	}

	@Override
	protected void setReuseAddress(boolean reuseAddress) {
		acceptor.setReuseAddress(reuseAddress);
	}
}
