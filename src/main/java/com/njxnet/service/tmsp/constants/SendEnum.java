package com.njxnet.service.tmsp.constants;

public enum SendEnum {

    SINGLE(1),
    GROUP(2),
    ;

    private Integer type;

    SendEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
