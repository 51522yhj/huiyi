package com.easymeeting.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoVO implements Serializable {
    private String userId;
    private String nickName;
    private Integer sex;
    private String token;
    private String meetingNo;
    private Boolean admin;


}
