package com.easymeeting.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageSendDto<T> implements Serializable {
    private static final long serialVersionUID = -1045752033171142417L;
    private Integer messageType;
   private String meetingId;
    private Integer messageSend2Type;
    private String sendUserId;
    private T messageContent;
    private String receiveUserId;
    private Long sendTime;
    private Long messageId;
    private Integer status;
    private String fileName;
    private Integer fileType;
    private Long fileSize;
}
