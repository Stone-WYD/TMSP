package com.njxnet.service.tmsp.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class SysUserQuery implements Serializable {

    @ApiModelProperty(notes = "用户名", name = "username")
    private String username;

    @ApiModelProperty(notes = "页码", name = "page")
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小为1")
    private Long page;

    @ApiModelProperty(notes = "每页数量", name = "size")
    @NotNull(message = "每页数量不能为空")
    @Min(value = 1, message = "每页数量最少为1")
    private Long size;

    public SysUserQuery() {
    }

    public SysUserQuery(Long page, Long size) {
        this.page = page;
        this.size = size;
    }

    public SysUserQuery(String username, Long page, Long size) {
        this.username = username;
        this.page = page;
        this.size = size;
    }
}
