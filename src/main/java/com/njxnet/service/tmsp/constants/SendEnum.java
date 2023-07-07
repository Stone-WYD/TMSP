package com.njxnet.service.tmsp.constants;

public enum SendEnum {

    SINGLE("1"),
    GROUP("2"),
    ;

    private String type;

    SendEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
