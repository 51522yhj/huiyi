package com.easymeeting.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MeetingJoinDto implements Serializable {
    private MeetingMemberDto newMember;
    private List<MeetingMemberDto> meetingMemberList;
}
