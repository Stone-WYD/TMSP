package com.njxnet.service.tmsp.design.core7_reactor.worker;

/**
 * @program: TMSP
 * @description: 线程类
 * @author: Stone
 * @create: 2023-07-31 11:48
 **/
public abstract class Worker {

    public abstract void subTask(Runnable runnable);

}
