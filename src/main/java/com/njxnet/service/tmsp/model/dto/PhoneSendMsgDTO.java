package com.njxnet.service.tmsp.model.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @program: interface-platform
 * @description: 短信发送dto
 * @author: xk
 * @create: 2019-12-20 13:40
 **/
public class PhoneSendMsgDTO implements Serializable, Cloneable{


    private static final long serialVersionUID = -532388861282783204L;

    @ApiModelProperty(notes = "短信类型", name = "type")
    @NotBlank(message = "短信类型不能为空")
    private String type;

    @ApiModelProperty(notes = "data", name = "发送的数据")
    @NotBlank(message = "发送数据data不能为空")
    private String data;

    @ApiModelProperty(notes = "手机号", name = "mobile")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @ApiModelProperty(notes = "短信发送类型 1--云片平台发送，2--创蓝平台", name = "sendType")
    private String sendType;

    @ApiModelProperty(notes = "法院代码",name = "courtCode")
    private String courtCode;


    public PhoneSendMsgDTO(String type, String data, String mobile) {
        this.type = type;
        this.data = data;
        this.mobile = mobile;
    }

    public PhoneSendMsgDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getCourtCode() {
        return courtCode;
    }

    public void setCourtCode(String courtCode) {
        this.courtCode = courtCode;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
            return super.clone();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"data\":\"")
                .append(data).append('\"');
        sb.append(",\"mobile\":\"")
                .append(mobile).append('\"');
        sb.append(",\"sendType\":\"")
                .append(sendType).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
