package com.easymeeting.entity.enums;


public enum ResponseCodeEnum {
    CODE_200(200, "请求成功"),
    CODE_404(404, "请求地址不存在"),
    CODE_600(600, "请求参数错误"),
    CODE_601(601, "信息已经存在"),
    CODE_603(603,"json转换异常"),
    CODE_500(500, "服务器返回错误，请联系管理员"),
    CODE_502(502, "账号已禁用"),
    CODE_503(503, "此账号已在别处登录"),
    CODE_501(501, "用户名或密码错误"),
    CODE_901(901, "用户登录已过期"),
    CODE_902(902, "权限不足"),
    CODE_903(903, "你有未结束的会议，无法创建新的会议");
    private Integer code;

    private String msg;

    ResponseCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
