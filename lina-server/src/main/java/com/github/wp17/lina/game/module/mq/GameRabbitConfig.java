package com.github.wp17.lina.game.module.mq;

import com.github.wp17.lina.common.util.RabbitConst;
import com.github.wp17.lina.game.spring.SpringContext;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:rabbit.properties")
@ComponentScan({"com.github.wp17.lina.game.module.mq"})
@EnableRabbit
public class GameRabbitConfig {
    @Value("${spring.rabbitmq.host}")
    String host;
    @Value("${spring.rabbitmq.port}")
    int port;
    @Value("${spring.rabbitmq.username}")
    String username;
    @Value("${spring.rabbitmq.password}")
    String password;
    @Value("${g2g_broadcast_queue}")
    String g2g_broadcast_queue;

    @Bean
    public ConnectionFactory g2rConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(host);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setPort(port);
        return factory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(g2rConnectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(g2rConnectionFactory());
        return template;
    }

    @Bean(RabbitConst.g2r_update_queue)
    public Queue g2rQueue() {
        return new Queue(RabbitConst.g2r_update_queue);
    }

    @Bean(RabbitConst.g2r_update_exchange)
    public DirectExchange exchange() {
        return new DirectExchange(RabbitConst.g2r_update_exchange);
    }

    @Bean
    public Binding binding(@Qualifier(RabbitConst.g2r_update_exchange) DirectExchange exchange,
                           @Qualifier(RabbitConst.g2r_update_queue) Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitConst.g2r_update_route_key);
    }

    @Bean(RabbitConst.g2g_broadcast_exchange)
    public FanoutExchange g2gBroadcastExchange() {
        return new FanoutExchange(RabbitConst.g2g_broadcast_exchange);
    }

    @Bean(RabbitConst.g2g_broadcast_queue)
    public Queue g2gBroadcastQueue() {
        return new Queue(g2g_broadcast_queue);
    }

    @Bean
    public Binding bindingG2gBroadcast() {
        return BindingBuilder.bind(g2gBroadcastQueue()).to(g2gBroadcastExchange());
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        //SimpleRabbitListenerContainerFactory发现消息中有content_type有text就会默认将其转换成string类型的
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }
}
