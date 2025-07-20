package com.easymeeting.websocket.message;

import com.easymeeting.entity.dto.MessageSendDto;

public interface MessageHandler {
    void listenMessage();
    void sendMessage(MessageSendDto messageSendDto);
}
