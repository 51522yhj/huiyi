package com.easymeeting.entity.po;

import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data

public class RegisterRequest {
    @NotEmpty
    private String checkCodeKey;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(max = 20)
    private String password;
    @NotEmpty
    @Size(max = 20)
    private String nickName;
    @NotEmpty
    private String checkCode;
}
