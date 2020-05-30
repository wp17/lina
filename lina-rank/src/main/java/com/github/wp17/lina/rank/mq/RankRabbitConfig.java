package com.github.wp17.lina.rank.mq;

import com.github.wp17.lina.common.util.RabbitConst;
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
@ComponentScan(basePackages = "com.github.wp17.lina.rank.mq")
@EnableRabbit
public class RankRabbitConfig {
    @Value("${g2g_broadcast_queue}")
    String g2g_broadcast_queue;

    @Bean
    public ConnectionFactory g2rConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        return factory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(g2rConnectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(g2rConnectionFactory());
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

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        //SimpleRabbitListenerContainerFactory发现消息中有content_type有text就会默认将其转换成string类型的
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
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
}
