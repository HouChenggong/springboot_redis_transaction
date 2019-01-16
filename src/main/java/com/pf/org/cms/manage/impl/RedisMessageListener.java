package com.pf.org.cms.manage.impl;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;


@Service
public class RedisMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
       //消息体
        String body =new String(message.getBody());
        // 渠道消息
        String topic =new String(pattern);
        System.out.println("消息体："+body);
        System.out.println("渠道消息"+pattern);
    }
}
