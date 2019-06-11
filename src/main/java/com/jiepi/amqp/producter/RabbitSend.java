package com.jiepi.amqp.producter;

import org.springframework.amqp.rabbit.connection.CorrelationData;
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

    /**
     *确认是否成功发送
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            System.out.println("correlationData"+correlationData);
            System.out.println("ack"+ack);
            if (!ack) {
                System.out.println("4444444444"+s);
            }
        }
    };
    /**
     * 没有发送成功
     */

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int i, String s, String s1, String s2) {
            System.out.println(message);
        }
    };


    public  void  send(Object message, Map<String ,Object> properties)  throws Exception{
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message,mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData correlationData = new CorrelationData("1234567890");
        rabbitTemplate.convertAndSend("exchange-1","spring1.hello",msg,correlationData);
    }

}
