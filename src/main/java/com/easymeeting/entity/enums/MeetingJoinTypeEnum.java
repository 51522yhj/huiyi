package com.easymeeting.entity.enums;

public enum MeetingJoinTypeEnum {
    NO_PASSWORD(0,"无需密码"),
    PASSWORD(1,"需要密码");
    private Integer type;
    private String desc;
    MeetingJoinTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public Integer getType() {
        return type;
    }
    public String getDesc() {
        return desc;
    }
    public static MeetingJoinTypeEnum getByType(Integer type) {
        for (MeetingJoinTypeEnum meetingJoinTypeEnum : MeetingJoinTypeEnum.values()) {
            if (meetingJoinTypeEnum.getType().equals(type)) {
                return meetingJoinTypeEnum;
            }
        }
        return null;
    }
}
