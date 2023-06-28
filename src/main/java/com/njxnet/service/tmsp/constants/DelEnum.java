package com.njxnet.service.tmsp.constants;

public enum DelEnum {
    DEL(0),
    EXIST(1)
    ;

    private final Integer code;


    DelEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
