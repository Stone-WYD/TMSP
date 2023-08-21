package com.njxnet.service.tmsp.constant;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.njxnet.service.tmsp.config.constant.mvc.IEnumJsonDeserializer;
import com.njxnet.service.tmsp.config.constant.mvc.IEnumJsonSerializer;

@JsonSerialize(using = IEnumJsonSerializer.class)
@JsonDeserialize(using = IEnumJsonDeserializer.class)
public enum FreezeIEnum implements IEnum{
    USING(1, "使用中"),
    FREEZING(0, "冻结中")
    ;

    private final Integer code;
    private final String desc;

    FreezeIEnum(Integer code, String desc) {
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
