package com.easymeeting.entity.dto;

import lombok.Data;

@Data
public class MeetingInviteDto {
    private String meetingName;
    private String meetingId;
    private String inviteUserName;
}
