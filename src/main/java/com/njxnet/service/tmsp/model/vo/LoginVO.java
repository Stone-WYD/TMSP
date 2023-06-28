package com.njxnet.service.tmsp.model.vo;

import com.njxnet.service.tmsp.model.info.ResourceNodeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginVO implements Serializable {

    @ApiModelProperty(notes = "userId", name = "userId")
    private Long id;

    @ApiModelProperty(notes = "所属法院", name = "courtCode")
    private String courtCode;

    @ApiModelProperty(notes = "法院名称", name = "courtName")
    private String courtName;

    @ApiModelProperty(notes = "用户权限列表", name = "list")
    private List<ResourceNodeInfo> list;
}
