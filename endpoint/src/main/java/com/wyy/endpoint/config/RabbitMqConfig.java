package com.wyy.endpoint.config;

import com.wyy.endpoint.vo.MqQueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 交换机
     */
    @Bean
    DirectExchange recordDirect() {
        return ExchangeBuilder
                .directExchange(MqQueueEnum.QUEUE_RECORD.getExchange())
                .durable(true)
                .build();
    }
    /**
     * 队列
     */
    @Bean
    public Queue recordQueue() {
        return QueueBuilder
                .durable(MqQueueEnum.QUEUE_RECORD.getQueueName())
                .build();
    }
    /**
     * 将队列绑定到交换机
     * @param recordDirect 交换机
     * @param recordQueue 队列
     */
    @Bean
    Binding recordBinding(DirectExchange logDirect, Queue logQueue) {
        return BindingBuilder
                .bind(logQueue)
                .to(logDirect)
                .with(MqQueueEnum.QUEUE_RECORD.getRouteKey());
    }

}
