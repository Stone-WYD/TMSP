package com.njxnet.service.tmsp.design.core1_postprocessor;

public class PostContext<T> {

    private final T t;

    public PostContext(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }


}
