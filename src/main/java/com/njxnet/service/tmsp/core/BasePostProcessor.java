package com.njxnet.service.tmsp.core;

public interface BasePostProcessor <T>{

    default  boolean handleBefore(PostContext<T> postContext){
        return true;
    }

    default void handleAfter(PostContext<T> postContext){}

    default int getPriprity(){
        return 0;
    }
}
