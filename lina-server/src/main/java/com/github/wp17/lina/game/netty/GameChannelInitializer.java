package com.github.wp17.lina.game.netty;

import com.github.wp17.lina.common.codec.VarTypeProtobufDecoder;
import com.github.wp17.lina.common.codec.VarTypeProtobufEncoder;
import com.github.wp17.lina.common.net.IPacket;
import com.github.wp17.lina.game.netty.handler.GameHandshakeHandler;
import com.github.wp17.lina.game.netty.handler.NettyHeartbeatHandler;
import com.github.wp17.lina.game.netty.handler.GameLogicHandler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;
import java.nio.ByteOrder;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

public class GameChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) {
		ch.pipeline()
		.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, Integer.MAX_VALUE,
				IPacket.LENGTHFIELD_OFFSET, IPacket.LENGTHFIELD_LENGTH, 0, 0, true))
		.addLast(new VarTypeProtobufDecoder())
		.addLast(new VarTypeProtobufEncoder())
		.addLast(new GameLogicHandler())
        .addLast(handshakeHandler())
		.addLast(new NettyHeartbeatHandler())
		;
	}

	/**空闲链路检测*/
	public IdleStateHandler idleStateHandler() {
		return new IdleStateHandler(10, 10, 5, TimeUnit.SECONDS);
	}

	/**读超时处理器*/
	public ReadTimeoutHandler readTimeoutHandler() {
		return new ReadTimeoutHandler(30);
	}

	/**握手协议处理器*/
	public ChannelHandler handshakeHandler() {
		return new GameHandshakeHandler();
	}

	/**ssl协议处理器*/
	public SslHandler sslHandler() throws Exception {
		return new SslHandler(createSslEngine(), false);
	}

	/**
	 * 参考http://blog.csdn.net/ENERGIE1314/article/details/54581411
	 * http://www.ruanyifeng.com/blog/2014/02/ssl_tls.html
	 * http://www.williamlong.info/archives/3461.html
	 */
	private SSLEngine createSslEngine() throws Exception {
		System.setProperty("javax.net.ssl.trustStore", "server_ks");

		KeyStore ks = KeyStore.getInstance("jceks");
		ks.load(this.getClass().getResourceAsStream("server_ks"), new char[]{'s', 'e', 'r', 'v', 'e', 'r'});
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
		kmf.init(ks, new char[]{'1', '1', '0', '1', '1', '9', '1', '2', '0'});

		TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
		KeyStore keyStore = KeyStore.getInstance("jceks");
		keyStore.load(this.getClass().getResourceAsStream("server_cer"),
				new char[]{'c', 'l', 'i', 'e', 'n', 't'});
		tmf.init(keyStore);

		SSLContext context = SSLContext.getInstance("TLS");
		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
		SSLEngine sslEngine = context.createSSLEngine();
		sslEngine.setNeedClientAuth(true);

		return sslEngine;
	}
}
