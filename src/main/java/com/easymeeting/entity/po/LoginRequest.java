package com.easymeeting.entity.po;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @NotEmpty
    private String checkCodeKey;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(max = 20)
    private String password;
    @NotEmpty
    private String checkCode;
}
