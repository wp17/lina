package com.github.wp17.lina.server;

public abstract class NioServer extends AbstractServer {
	protected NioServer(int id) {
		super(id);
	}
	
	@Override
	protected void aftStartup() {
		bind();
	}
	
	protected abstract void bind();
}
