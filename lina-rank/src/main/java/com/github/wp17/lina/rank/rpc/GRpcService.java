package com.github.wp17.lina.rank.rpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class GRpcService {
	private int port = 50051;
	private Server server;
	private List<BindableService> services = new ArrayList<>();
	
	public void addService(BindableService bindableService) {
		services.add(bindableService);
		
	}

	public void start() throws IOException {
		ServerBuilder<?> builder = ServerBuilder.forPort(port);
		services.forEach(service -> builder.addService(service));
		server = builder.build().start();
		System.out.println("service start...");
	}

	public void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	// block 一直到退出程序
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}
}
