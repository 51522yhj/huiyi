package com.easymeeting.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenUserInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private String userId;
    private String nickName;
    private Integer sex;
    private String currentMeetingId;
    private String currentNickName;
    private String myMeetingNo;
    private Boolean admin;

}
