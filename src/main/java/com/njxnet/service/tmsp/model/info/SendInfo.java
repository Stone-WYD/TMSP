package com.njxnet.service.tmsp.model.info;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SendInfo {

    private static final long serialVersionUID = -532388861282783204L;

    @ApiModelProperty(notes = "操作人姓名", name = "userName")
    private String userName;

    @ApiModelProperty(notes = "短信标题", name = "title")
    private String title;

    @ApiModelProperty(notes = "短信类型", name = "type")
    @NotBlank(message = "短信类型不能为空")
    private String type;

    @ApiModelProperty(notes = "接口平台调用的参数", name = "data")
    @NotBlank(message = "发送数据data不能为空")
    private String data;

    @ApiModelProperty(notes = "发送的短信内容", name = "content")
    @NotBlank(message = "发送数据content不能为空")
    private String content;

    @ApiModelProperty(notes = "手机号，单发接口不能为空", name = "mobile")
    private String mobile;

    @ApiModelProperty(notes = "群发手机号，群发接口不能为空", name = "mobiles")
    private String mobiles;

    @ApiModelProperty(notes = "短信发送类型 1--云片平台发送，2--创蓝平台", name = "sendType")
    private String sendType;

    @ApiModelProperty(notes = "法院代码",name = "courtCode")
    private String courtCode;

    @ApiModelProperty(notes = "发送方式，1.单发，2.群发",name = "sendWay")
    private String sendWay;

    private boolean canSend = true;

    private Long singleId;

    private Long groupId;

}
