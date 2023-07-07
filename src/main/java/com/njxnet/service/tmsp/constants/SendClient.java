package com.njxnet.service.tmsp.constants;

public enum SendClient {
    WEB_CLIENT("webclient"),
    REST_TEMPLATE("rest")
            ;

    private String name;

    public String getName() {
        return name;
    }

     SendClient(String name) {
        this.name = name;
    }
}
