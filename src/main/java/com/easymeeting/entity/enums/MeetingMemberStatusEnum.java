package com.easymeeting.entity.enums;

public enum MeetingMemberStatusEnum {
    DEL_MEETING(0,"删除会议"),
    NORMAL(1,"正常"),
    EXIT_MEETING(2,"退出会议"),
    KICK_OUT(3,"被踢出会议"),
    BLACKLIST(4,"被拉黑");
    private Integer status;
    private String desc;
    MeetingMemberStatusEnum(Integer status,String desc){
        this.status = status;
        this.desc = desc;
    }
    public static MeetingMemberStatusEnum getByStatus(Integer status) {
        for (MeetingMemberStatusEnum meetingMemberStatusEnum : MeetingMemberStatusEnum.values()) {
            if (meetingMemberStatusEnum.getStatus().equals(status)) {
                return meetingMemberStatusEnum;
            }


        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }
}
