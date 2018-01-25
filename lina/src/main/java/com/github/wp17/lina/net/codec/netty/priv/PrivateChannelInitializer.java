package com.github.wp17.lina.net.codec.netty.priv;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import com.github.wp17.lina.net.handler.NettyHeartbeatHandler;
import com.github.wp17.lina.net.handler.NettyInboundLogicHandler;
import com.github.wp17.lina.net.packet.Packet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class PrivateChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		SslHandler ssslHandler = new SslHandler(createSslEngine("server_ks", "110119120"), false);
		
		ch.pipeline()
		.addLast(new IdleStateHandler(10, 10, 5, TimeUnit.SECONDS))
		.addLast(new NettyHeartbeatHandler())
		.addLast(new ReadTimeoutHandler(30))
		.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, Packet.LENGTHFIELD_OFFSET, Packet.LENGTHFIELD_LENGTH))
		.addLast(new NettyByte2MsgDecoder())
		.addLast(new NettyMsg2ByteEncoder())
		.addLast(new NettyInboundLogicHandler())
		.addLast(ssslHandler);
		
	}
	
	/**参考http://blog.csdn.net/ENERGIE1314/article/details/54581411*/
	private SSLEngine createSslEngine(String ketStorePath, String keyStorePwd) throws Exception {
		System.setProperty("javax.net.ssl.trustStore", ketStorePath);  
        SSLContext context = SSLContext.getInstance("TLS");  

        KeyStore ks = KeyStore.getInstance("jceks");
        ks.load(new FileInputStream(ketStorePath), null); 
        
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");  
        kf.init(ks, keyStorePwd.toCharArray());  

        context.init(kf.getKeyManagers(), null, null);
		return context.createSSLEngine();
	}
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
	}
}
