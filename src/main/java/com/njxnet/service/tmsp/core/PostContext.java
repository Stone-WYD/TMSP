package com.njxnet.service.tmsp.core;

public class PostContext<T> {

    private final T t;

    public PostContext(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }


}
