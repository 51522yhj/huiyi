package com.easymeeting.entity.enums;

public enum UserStatusEnum {

    ENABLE(1), DISABLE(0);
    Integer status;

    private UserStatusEnum(int status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }
}
