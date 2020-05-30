package com.github.wp17.lina.game.grpc;

import com.github.wp17.lina.proto.msg.TestProto;
import com.github.wp17.lina.proto.rpc.service.GreeterGrpc;
import com.googlecode.protobuf.format.JsonFormat;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class HelloWorldClient {
	private final ManagedChannel channel;
	private final GreeterGrpc.GreeterBlockingStub blockingStub;
	private final GreeterGrpc.GreeterFutureStub futureStub;

	public HelloWorldClient(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
		blockingStub = GreeterGrpc.newBlockingStub(channel);
		futureStub = GreeterGrpc.newFutureStub(channel);

//		GreeterGrpc.GreeterStub stub = GreeterGrpc.newStub(channel);
	}

	public void shutdown() {
		channel.shutdown();
	}

	public void greet(String name) {
		TestProto.Test request = TestProto.Test.newBuilder().setId(1).setName(name).build();
		TestProto.Test response = blockingStub.sayHello(request);

		JsonFormat format = new JsonFormat();
		System.out.println(format.printToString(response));
	}

	public static void main(String[] args) {
		HelloWorldClient client = new HelloWorldClient("127.0.0.1", 50051);
		for (int i = 0; i < 5; i++) {
			client.greet("world:" + i);
		}

		client.shutdown();
	}
}
