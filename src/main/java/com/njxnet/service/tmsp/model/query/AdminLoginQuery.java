package com.njxnet.service.tmsp.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AdminLoginQuery {

    @ApiModelProperty(notes = "用户名", name = "username")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(notes = "密码", name = "password")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(notes = "验证码key", name = "captchaKey")
    @NotBlank(message = "验证码key不能为空")
    private String captchaKey;

    @ApiModelProperty(notes = "验证码", name = "captchaValue")
    @NotBlank(message = "验证码不能为空")
    private String captchaValue;
}
