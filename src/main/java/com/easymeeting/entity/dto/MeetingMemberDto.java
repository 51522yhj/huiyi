package com.easymeeting.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MeetingMemberDto implements Serializable {
private String userId;
private String nickName;
private Long joinTime;
private Integer memberType;
private Integer status;
private Boolean openVideo;
private Integer sex;
}
