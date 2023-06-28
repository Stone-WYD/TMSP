package com.njxnet.service.tmsp.config.datasource.context;

public enum DsEnum {
    MASTER("tmsp"),
    SECOND("interface")
    ,
    ;

    DsEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
