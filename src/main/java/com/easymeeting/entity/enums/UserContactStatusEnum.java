package com.easymeeting.entity.enums;

public enum UserContactStatusEnum {
    FRIEND(1,"好友"),
    DEL(2,"已删除"),
    BLACKLIST(3,"已拉黑好友");

    private Integer status;
    private String desc;
    UserContactStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    public Integer getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }
    public static UserContactStatusEnum getByStatus(Integer status){
        for(UserContactStatusEnum statusEnum : UserContactStatusEnum.values()){
            if(statusEnum.getStatus().equals(status)){
                return statusEnum;
            }
        }
        return null;
    }

}
