package com.github.wp17.lina;

import java.net.InetSocketAddress;
import java.util.Arrays;

import org.slf4j.Logger;

import com.github.wp17.lina.log.LogModule;
import com.github.wp17.lina.log.LoggerProvider;
import com.github.wp17.lina.server.MinaNioServer;
import com.github.wp17.lina.server.Server;
import com.github.wp17.lina.server.ServerHolder;

public class Bootstrap {
	private static final Logger logger = LoggerProvider.getLogger(Bootstrap.class);
	public static void main(String[] args) {
		LogModule.getInstance().init();
		Server server = new MinaNioServer(Arrays.asList(new InetSocketAddress(8000)), 1);
		ServerHolder.server(server);
		server.startup();
		logger.info("server started");
	}
}
