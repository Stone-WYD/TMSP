package com.njxnet.service.tmsp.model.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TmspSysUserInfo {
    //主键
    @ApiModelProperty(notes = "用户id", name = "id")
    @NotNull(message = "用户id不能为空", groups = {update.class})
    private Long id;
    //用户名
    @ApiModelProperty(notes = "用户名", name = "userName")
    @NotBlank(message = "用户名不能为空", groups = {insert.class})
    private String userName;
    //密码
    @ApiModelProperty(notes = "密码", name = "password")
    @NotBlank(message = "密码不能为空", groups = {insert.class})
    private String password;
    //所属法院
    @ApiModelProperty(notes = "所属法院", name = "courtCode")
    @NotBlank(message = "所属法院不能为空", groups = {insert.class})
    private String courtCode;

    public interface insert{}
    public interface update{}
}
