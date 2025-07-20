package com.easymeeting.entity.enums;

public enum MeetingStatusEnum {
    RUNNING(0,"进行中"),
    FINISH(1,"已结束");
    private Integer status;
    private String desc;
    MeetingStatusEnum(Integer status,String desc){
        this.status = status;
        this.desc = desc;
    }
    public static MeetingStatusEnum getByStatus(Integer status) {
        for (MeetingStatusEnum meetingStatusEnum : MeetingStatusEnum.values())
            if (meetingStatusEnum.getStatus().equals(status))
                return meetingStatusEnum;
        return null;
    }
    public Integer getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }
}
