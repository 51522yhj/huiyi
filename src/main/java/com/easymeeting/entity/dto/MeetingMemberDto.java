package com.easymeeting.entity.dto;

import lombok.Data;

@Data
public class MeetingMemberDto {
private String userId;
private String nickName;
private Long joinTime;
private Integer memberType;
private Integer status;
private Boolean openVideo;
private Integer sex;
}
