package com.wyy.endpoint.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyy.endpoint.dao.IRecordDAO;
import com.wyy.endpoint.vo.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 记录消息消费者
 */
@Component
@RabbitListener(queues = "hr.record.queue")
public class RecordReceiver {
    private static Logger LOGGER = LoggerFactory.getLogger(RecordReceiver.class);
    @Autowired
    private IRecordDAO recordDAO;
    @Autowired
    private ObjectMapper objectMapper;
    @RabbitHandler
    public void handle(String msg){

        Record record = null;
        try {
            record = objectMapper.readValue(msg,Record.class); // 将msg json字符串转化为 Record对象
        }catch (Exception e){
            e.printStackTrace();
        }

        recordDAO.insert(record); // 进行消息的消费
        LOGGER.info("receive message record:{}",record); // 记录
    }

}
