package com.easymeeting.websocket.message;

import com.easymeeting.constants.Constants;
import com.easymeeting.entity.dto.MessageSendDto;
import com.easymeeting.utils.JsonUtils;
import com.easymeeting.websocket.ChannelContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
@Slf4j
@Component
@ConditionalOnProperty(name= Constants.MESSAGEING_HANDLE_CHANNEL_KEY,havingValue = Constants.MESSAGEING_HANDLE_CHANNEL_REDIS)
public class MessageHandler4Redis implements MessageHandler{
    private static final String MESSAGE_TOPIC = "message.topic";
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private ChannelContextUtils channelContextUtils;
    @Override
    public void listenMessage() {
        RTopic rTopic = redissonClient.getTopic(MESSAGE_TOPIC);
        rTopic.addListener(MessageSendDto.class, (MessageSendDto, sendDto) -> {
            log.info("收到消息：{}", JsonUtils.convertObj2Json(sendDto));
            channelContextUtils.sendMessage(sendDto);
        });
    }

    @Override
    public void sendMessage(MessageSendDto messageSendDto) {
        RTopic rTopic = redissonClient.getTopic(MESSAGE_TOPIC);
        rTopic.publish(messageSendDto);
    }
    @PreDestroy
    public void destory(){
        redissonClient.shutdown();
    }
}
