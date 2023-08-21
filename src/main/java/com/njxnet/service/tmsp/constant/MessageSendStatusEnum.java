package com.njxnet.service.tmsp.constant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.njxnet.service.tmsp.config.constant.mvc.IEnumJsonDeserializer;
import com.njxnet.service.tmsp.config.constant.mvc.IEnumJsonSerializer;

@JsonSerialize(using = IEnumJsonSerializer.class)
@JsonDeserialize(using = IEnumJsonDeserializer.class)
public enum MessageSendStatusEnum implements IEnum{
    SUCCESS(1, "发送成功"),
    FAIL(2, "发送失败"),
    UNKNOW(3, "未知")
    ;

    private final Integer code;
    private final String desc;

    MessageSendStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return desc;
    }
}
