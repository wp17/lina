package com.github.wp17.lina;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.github.wp17.lina.module.ModuleManager;
import com.github.wp17.lina.net.codec.CodecFactory;
import com.github.wp17.lina.net.codec.Decoder;
import com.github.wp17.lina.net.codec.Encoder;
import com.github.wp17.lina.net.handler.LogicHandler;

public abstract class Server {
	private List<SocketAddress> addresses = new ArrayList<SocketAddress>();
	private List<NioSocketAcceptor> acceptors = new ArrayList<NioSocketAcceptor>();
	
	public List<SocketAddress> getAddresses() {
		return addresses;
	}
	

	public final void startup(){
		preStartup();
		initModules();
		startListenPort();
		aftStartup();
	}
	
	protected abstract int getServerId();
	protected abstract void preStartup();
	protected abstract void aftStartup();
	protected abstract void initModules();
	
	private void startListenPort() {
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		
		acceptor.setReuseAddress(true);
		acceptor.setBacklog(6000);
		
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 300);
		acceptor.getSessionConfig().setTcpNoDelay(true);
		acceptor.getSessionConfig().setSoLinger(-1);
		
		acceptor.getFilterChain().addLast("readEexcutor", new ExecutorFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CodecFactory(new Decoder(), new Encoder())));
		acceptor.getFilterChain().addLast("writeExecutor", new ExecutorFilter(IoEventType.WRITE));
		
		acceptor.setHandler(new LogicHandler());
		try {
			acceptor.bind(addresses);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		acceptors.add(acceptor);
	}
	
	public void close(){
		for (NioSocketAcceptor acceptor : acceptors) {
			acceptor.dispose();
		}
		
		ModuleManager.getInstance().destory();
	}
}
