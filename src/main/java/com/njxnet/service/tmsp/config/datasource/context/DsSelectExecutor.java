package com.njxnet.service.tmsp.config.datasource.context;

import java.util.function.Supplier;

/*
* 手动指定数据源的用法
* */
public class DsSelectExecutor {
    /*
    * 返回结果
    * */
    public static <T> T submit(DsEnum dsEnum, Supplier<T> supplier){
        DsContextHolder.set(dsEnum.getName());
        try {
            return supplier.get();
        }finally {
            DsContextHolder.reset();
        }
    }

    /*
    * 无返回结果
    * */
    public static void execute(DsEnum dsEnum, Runnable call){
        DsContextHolder.set(dsEnum.getName());
        try {
            call.run();
        }finally {
            DsContextHolder.reset();
        }
    }

}
