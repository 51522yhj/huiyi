package com.easymeeting.entity.enums;

import com.easymeeting.utils.StringTools;

public enum FileTypeEnum {
    IMAGE(0,new String[]{".jpg",".jpeg",".png",".gif",".bmp",".webp"},".jpg","图片"),
    VIDEO(1,new String[]{".mp4",".avi",".rmvb",".mkv",".mov"},".mp4","视频");
    private Integer type;
    private String[] suffixArray;
    private String suffix;
    private String desc;

    FileTypeEnum(Integer type, String[] suffixArray, String suffix, String desc) {
        this.type = type;
        this.suffixArray = suffixArray;
        this.suffix = suffix;
        this.desc = desc;
    }

    public static FileTypeEnum getBySuffix(String fileSuffix) {
        if (StringTools.isEmpty(fileSuffix)){
            return null;
        }
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()){
            for (String suffix : fileTypeEnum.getSuffixArray()){
                if (fileSuffix.equals(suffix)){
                    return fileTypeEnum;
                }
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

    public String[] getSuffixArray() {
        return suffixArray;
    }

    public void setSuffixArray(String[] suffixArray) {
        this.suffixArray = suffixArray;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public static FileTypeEnum getByType(Integer type){
        if (null == type){
            return null;
        }
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()){
            if (fileTypeEnum.getType().equals(type))
                return fileTypeEnum;
        }
        return null;
    }
}
