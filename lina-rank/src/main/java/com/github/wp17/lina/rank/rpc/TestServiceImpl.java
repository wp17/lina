package com.github.wp17.lina.rank.rpc;

import com.github.wp17.lina.proto.msg.TestProto;
import com.github.wp17.lina.proto.rpc.service.GreeterGrpc;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.grpc.stub.StreamObserver;

public class TestServiceImpl extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(TestProto.Test request, StreamObserver<TestProto.Test> responseObserver) {
        try {
            System.out.println(JsonFormat.printer().print(request));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        TestProto.Test reply = TestProto.Test.newBuilder().setId(1).setName("111").build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
