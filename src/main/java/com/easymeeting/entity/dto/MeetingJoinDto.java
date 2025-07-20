package com.easymeeting.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class MeetingJoinDto {
    private MeetingMemberDto newMember;
    private List<MeetingMemberDto> meetingMemberList;
}
