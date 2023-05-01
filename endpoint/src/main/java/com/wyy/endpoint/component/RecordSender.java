package com.wyy.endpoint.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyy.endpoint.vo.MqQueueEnum;
import com.wyy.endpoint.vo.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 记录消息发送者
 */
@Component
public class RecordSender {
    private static Logger LOGGER = LoggerFactory.getLogger(RecordSender.class); //slf4j
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 发送消息
     * @param 要发送的对象，需转化为 json字符串
     */
    public void sendMessage(Record record) {
        String msg = null;
        try {   // 对Jackson操作异常处理
            msg = objectMapper.writeValueAsString(record); //将Record对象转换为json字符串
        }catch (Exception e){
            e.printStackTrace();
        }

        /**
         * 发送消息，发送的 msg 消息为 json字符串
         */
        amqpTemplate.convertAndSend(MqQueueEnum.QUEUE_RECORD.getExchange(), // 发送到那个交换机
                MqQueueEnum.QUEUE_RECORD.getRouteKey(), // 指定路由键
                msg); // 发送的消息

        LOGGER.info("send message record:{}",record);
    }

}
