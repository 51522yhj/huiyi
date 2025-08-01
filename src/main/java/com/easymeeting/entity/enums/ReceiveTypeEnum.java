package com.easymeeting.entity.enums;

public enum ReceiveTypeEnum {
    ALL(0,"全员"),
    USER(1,"个人");
    private Integer type;
    private String name;

    ReceiveTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
public static ReceiveTypeEnum getByType(Integer type){
        for (ReceiveTypeEnum receiveTypeEnum : ReceiveTypeEnum.values()){
            if (receiveTypeEnum.getType().equals(type)){
                return receiveTypeEnum;
            }
        }
        return null;
}
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
