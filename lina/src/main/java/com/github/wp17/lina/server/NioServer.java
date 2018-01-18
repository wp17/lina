package com.github.wp17.lina.server;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class NioServer extends AbstractServer {
	private List<SocketAddress> addresses = new ArrayList<SocketAddress>();
	public List<SocketAddress> getAddresses() {
		return Collections.unmodifiableList(addresses);
	}
	
	protected NioServer(List<SocketAddress> addresses, int id) {
		super(id);
		this.addresses.addAll(addresses);
	}
	
	@Override
	protected void aftStartup() {
		bind();
	}
	
	protected abstract void bind();
	protected abstract void setBacklog(int backlog);
	protected abstract void setTcpNoDelay(boolean tcpNoDelay);
	protected abstract void setSoLinger(int soLinger);
	protected abstract void setReuseAddress(boolean reuseAddress);
}
