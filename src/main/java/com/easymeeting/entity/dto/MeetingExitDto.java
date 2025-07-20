package com.easymeeting.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class MeetingExitDto {
    public String exitUserId;
    private List<MeetingMemberDto> meetingMemberList;
    private Integer exitStatus;


}
