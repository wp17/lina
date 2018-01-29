package com.github.wp17.lina.net.codec.netty.priv;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;

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
		SslHandler ssslHandler = new SslHandler(createSslEngine(), false);
		
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
	
	/**参考http://blog.csdn.net/ENERGIE1314/article/details/54581411
	 * http://www.ruanyifeng.com/blog/2014/02/ssl_tls.html
	 * http://www.williamlong.info/archives/3461.html
	 * */
	private SSLEngine createSslEngine() throws Exception {
		System.setProperty("javax.net.ssl.trustStore", "server_ks");  

        KeyStore ks = KeyStore.getInstance("jceks");
        ks.load(this.getClass().getResourceAsStream("server_ks"), new char[]{'s','e','r','v','e','r'}); 
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, new char[]{'1','1','0','1','1','9','1','2','0'});

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        KeyStore keyStore = KeyStore.getInstance("jceks");
        keyStore.load(this.getClass().getResourceAsStream("server_cer"), new char[]{'c','l','i','e','n','t'});
        tmf.init(keyStore);
        
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
        SSLEngine sslEngine = context.createSSLEngine();
        sslEngine.setNeedClientAuth(true);
        
		return sslEngine;
	}
}
