package com.github.wp17.lina.server;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.wp17.lina.common.log.LoggerProvider;
import com.github.wp17.lina.common.module.ModuleInitOrder;
import com.github.wp17.lina.server.Server;
import com.github.wp17.lina.server.ServerHolder;

@Component
@Order(ModuleInitOrder.latest)
public class Bootstrap implements CommandLineRunner{
	private static final Logger logger = LoggerProvider.getLogger(Bootstrap.class);
	@Autowired @Qualifier("nettyNioServer") Server server;
	
	@Override
	public void run(String... args) throws Exception {
		ServerHolder.server(server);
		server.startup();
		logger.info("server started");
	}
}
