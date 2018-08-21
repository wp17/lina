package com.github.wp17.lina.server.netty;

import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.github.wp17.lina.net.codec.netty.priv.NettyByte2MsgDecoder;
import com.github.wp17.lina.net.codec.netty.priv.NettyMsg2ByteEncoder;
import com.github.wp17.lina.net.codec.netty.priv.PrivateChannelInitializer;
import com.github.wp17.lina.net.handler.NettyHeartbeatHandler;
import com.github.wp17.lina.net.handler.NettyInboundLogicHandler;
import com.github.wp17.lina.net.packet.Packet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

@Configuration
@PropertySource("classpath:config/net/netty.properties")
public class NettyConfig {
	@Value("${boss.thread.count}")
	private int bossCount;
	@Value("${worker.thread.count}")
	private int workerCount;
	@Value("${tcp.port}")
	private int tcpPort;
	@Value("${so.keepalive}")
	private boolean keepAlive;
	@Value("${so.backlog}")
	private int backlog;
	
	@Autowired
	@Qualifier("privateChannelInitializer")
	private PrivateChannelInitializer privateChannelInitializer;
	
	@Bean(name = "privateChannelInitializer")
	public PrivateChannelInitializer privateChannelInitializer() {
		return new PrivateChannelInitializer();
	}

	@SuppressWarnings("unchecked")
	@Bean(name = "serverBootstrap")
	public ServerBootstrap bootstrap() {
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup(), workerGroup())
		.channel(NioServerSocketChannel.class)
		.childHandler(privateChannelInitializer);

		Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
		Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
		for (@SuppressWarnings("rawtypes")
		ChannelOption option : keySet) {
			b.option(option, tcpChannelOptions.get(option));
		}
		return b;
	}

	@Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup bossGroup() {
		return new NioEventLoopGroup(bossCount);
	}

	@Bean(name = "workerGroup", destroyMethod = "shutdownGracefully")
	public NioEventLoopGroup workerGroup() {
		return new NioEventLoopGroup(workerCount);
	}

	@Bean(name = "tcpSocketAddress")
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(tcpPort);
	}

	@Bean(name = "tcpChannelOptions")
	public Map<ChannelOption<?>, Object> tcpChannelOptions() {
		Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
		options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
		options.put(ChannelOption.SO_BACKLOG, backlog);
		return options;
	}
	
	@Bean(name = "channelGroup")
	public ChannelGroup channelGroup() {
		return new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	}
	
	@Bean(name = "idleStateHandler")
	public IdleStateHandler idleStateHandler() {
		return new IdleStateHandler(10, 10, 5, TimeUnit.SECONDS);
	}
	
	@Bean(name = "nettyHeartbeatHandler")
	public NettyHeartbeatHandler nettyHeartbeatHandler() {
		return new NettyHeartbeatHandler();
	}
	
	@Bean(name = "readTimeoutHandler")
	public ReadTimeoutHandler readTimeoutHandler() {
		return new ReadTimeoutHandler(30);
	}
	
	@Bean(name = "lengthFieldBasedFrameDecoder")
	public LengthFieldBasedFrameDecoder lengthFieldBasedFrameDecoder() {
		return new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, Packet.LENGTHFIELD_OFFSET, Packet.LENGTHFIELD_LENGTH);
	}
	
	@Bean(name = "nettyByte2MsgDecoder")
	public NettyByte2MsgDecoder nettyByte2MsgDecoder() {
		return new NettyByte2MsgDecoder();
	}
	
	@Bean(name = "nettyMsg2ByteEncoder")
	public NettyMsg2ByteEncoder nettyMsg2ByteEncoder() {
		return new NettyMsg2ByteEncoder();
	}
	
	@Bean(name = "nettyInboundLogicHandler")
	public NettyInboundLogicHandler nettyInboundLogicHandler() {
		return new NettyInboundLogicHandler();
	}
	
//	@Bean(name = "sslHandler")
//	public SslHandler sslHandler() throws Exception {
//		return new SslHandler(createSslEngine(), false);
//	}
	
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
	
//	@Bean
//	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
//		return new PropertySourcesPlaceholderConfigurer();
//	}
}
