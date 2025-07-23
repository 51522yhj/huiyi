package com.easymeeting.websocket.message;

import com.easymeeting.constants.Constants;
import com.easymeeting.entity.dto.MessageSendDto;
import com.easymeeting.utils.JsonUtils;
import com.easymeeting.websocket.ChannelContextUtils;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@ConditionalOnProperty(name= Constants.MESSAGEING_HANDLE_CHANNEL_KEY,havingValue = Constants.MESSAGEING_HANDLE_CHANNEL_RABBITMQ)
public class MessageHandler4RabbitMQ implements MessageHandler{
    private static final String EXCHANGE_NAME = "fanout_exchange";
    private static final Integer MAX_RETRYTIMES = 3;
    private static final String RETRY_COUNT_KEY = "retryCount";

    @Resource
    private ChannelContextUtils channelContextUtils;
    @Value("${rabbitmq.host:}")
    private String host;
    @Value("${rabbitmq.port:}")
    private Integer port;

    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    @Override
    public void listenMessage() {
        factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName,EXCHANGE_NAME,"");
            Boolean autoAck = false;
            DeliverCallback deliverCallback =(consumerTag, delivery) -> {
                try {
                    String message = new String(delivery.getBody(),"UTF-8");
                    log.info("收到消息:{}",message);
                    channelContextUtils.sendMessage(JsonUtils.convertJson2Obj(message,MessageSendDto.class));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                } catch (Exception e) {
                    log.error("消息处理失败:{}",e.getMessage());
                    handleFaileMessage(channel,delivery,queueName);
                }
            };
            channel.basicConsume(queueName,autoAck,deliverCallback,consumerTag -> {

            });
        } catch (Exception e) {
            log.error("消息监听失败:{}",e.getMessage());
            throw new RuntimeException(e);

        }
    }

    private static void handleFaileMessage(Channel channel, Delivery delivery, String queueName) throws IOException {
        Map<String,Object> headers = delivery.getProperties().getHeaders();
        if (headers == null){
            headers = new HashMap<>();
        }
        Integer retryCount = 0;
        if (headers.containsKey(RETRY_COUNT_KEY)){
            retryCount = (Integer) headers.get(RETRY_COUNT_KEY);
        }
        if (retryCount<MAX_RETRYTIMES -1){
            headers.put(RETRY_COUNT_KEY,retryCount+1);
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().headers(headers).build();
            channel.basicPublish("",queueName,properties,delivery.getBody());
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
        }else {
            log.info("超过最大重试次数");
            channel.basicReject(delivery.getEnvelope().getDeliveryTag(),false);
        }
    }

    @Override
    public void sendMessage(MessageSendDto messageSendDto) {
        try(Connection connection = factory.newConnection(); Channel channel =connection.createChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
            channel.basicPublish(EXCHANGE_NAME,"",null,messageSendDto.toString().getBytes());
        }catch(Exception e){
            log.error("消息发送失败:{}",e.getMessage());
        }
    }
    @PreDestroy
    public void destory() throws IOException, TimeoutException {
        if (channel!=null&&channel.isOpen()){
            channel.close();
        }
        if (connection!=null&&connection.isOpen()){
            connection.close();
        }
    }
}
