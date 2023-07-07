package com.njxnet.service.tmsp.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class MyThreadPoolExecutor extends ThreadPoolExecutor {

    // 这个自定义的线程池主要是为了处理异常，防止线程运行抛出异常无法被捕获处理
    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        FutureTask futureTask = (FutureTask) r;
        try {
            futureTask.get();
        } catch (Exception e) {
            log.error("MyThreadPoolExecutor occur error:{}", e.getLocalizedMessage());
        }
    }
}
