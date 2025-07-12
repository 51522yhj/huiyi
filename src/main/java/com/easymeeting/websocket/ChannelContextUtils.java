package com.easymeeting.websocket;

import com.alibaba.fastjson.JSON;
import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.entity.po.UserInfo;
import com.easymeeting.entity.vo.UserInfoVO;
import com.easymeeting.mappers.UserInfoMapper;
import com.easymeeting.redis.RedisComponent;
import com.easymeeting.utils.StringTools;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import io.netty.channel.Channel;
import com.easymeeting.entity.enums.MessageSend2TypeEnum;
import com.easymeeting.entity.dto.MessageSendDto;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ChannelContextUtils {
    public static final ConcurrentHashMap<String, Channel> USER_CONTEXT_MAP = new ConcurrentHashMap<>();
    public static final  ConcurrentHashMap<String, ChannelGroup> MEETING_ROOM_CONTEXT_MAP = new ConcurrentHashMap<>();
    private  final UserInfoMapper userInfoMapper;
    private final RedisComponent redisComponent;

    public ChannelContextUtils(UserInfoMapper userInfoMapper , RedisComponent redisComponent) {
        this.userInfoMapper = userInfoMapper;
        this.redisComponent = redisComponent;
    }
    public void addChannel(String userId, Channel channel){
        try {
            String channelId = channel.id().toString();
            AttributeKey attributeKey = null;
            if(!AttributeKey.exists(channelId)){
                attributeKey = AttributeKey.newInstance(channelId);
            }
            else {
                attributeKey = AttributeKey.valueOf(channelId);
            }
            channel.attr(attributeKey).set(userId);
            USER_CONTEXT_MAP.put(userId,channel);
            UserInfo userInfo = new UserInfo();
            userInfo.setLastLoginTime(System.currentTimeMillis());
            userInfoMapper.updateByUserId(userInfo,userId);
            TokenUserInfoDto tokenUserInfoDto = redisComponent.getTokenUserInfoDtoByUserId(userId);
            if(tokenUserInfoDto.getCurrentMeetingId() == null){
                return;
            }
            addMeetingRoom(tokenUserInfoDto.getCurrentMeetingId(),userId);
        }catch (Exception e){
            log.error("addChannel error",e);
        }
    }
    public void addMeetingRoom(String meetingId,String userId){
        try {
            Channel context = USER_CONTEXT_MAP.get(userId);
            if(context == null){
                return;
            }
            ChannelGroup group = MEETING_ROOM_CONTEXT_MAP.get(meetingId);
            if(group == null){
                group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                MEETING_ROOM_CONTEXT_MAP.put(meetingId,group);
            }
            Channel channel = group.find(context.id());
            if (channel == null) {
                group.add(context);
            }

        }catch (Exception e){
            log.error("addMeetingRoom error",e);
        }
    }
    public void sendMessage(MessageSendDto messageSendDto){
        if (MessageSend2TypeEnum.USER.getType().equals(messageSendDto.getMessageSend2Type())) {
            sendMsg2User(messageSendDto);
        }else {
            sendMsg2Group(messageSendDto);
        }
    }
    private void sendMsg2User(MessageSendDto messageSendDto){
        if (messageSendDto.getReceiveUserId() == null) {
            return;
        }
        Channel channel = USER_CONTEXT_MAP.get(messageSendDto.getReceiveUserId());
        if (channel == null) {
            return;
        }
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageSendDto)));
    }
    private void sendMsg2Group(MessageSendDto messageSendDto){
        if (messageSendDto.getMeetingId() == null) {
            return;
        }
        ChannelGroup channelGroup = MEETING_ROOM_CONTEXT_MAP.get(messageSendDto.getMeetingId());
        if (channelGroup == null) {
            return;

        }
        channelGroup.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageSendDto)));
    }
    public void closeContext(String userId){
        if (StringTools.isEmpty(userId)) {
            return;
        }
        Channel channel = USER_CONTEXT_MAP.get(userId);
        USER_CONTEXT_MAP.remove(userId);
        if (channel != null) {
            channel.close();
        }
    }
}
