package com.jiepi.spring.amqp.producter;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitSend {

    @Autowired
    private RabbitTemplate  rabbitTemplate;

    public  void  send(Object message, Map<String ,Object> properties)  throws Exception{
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message,mhs);
        rabbitTemplate.convertAndSend("exchange-1","spring.hello",msg);
    }

}
