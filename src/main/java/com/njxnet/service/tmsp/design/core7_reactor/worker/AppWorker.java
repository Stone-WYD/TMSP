package com.njxnet.service.tmsp.design.core7_reactor.worker;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.njxnet.service.tmsp.utils.MyThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: TMSP
 * @description:
 * @author: Stone
 * @create: 2023-07-30 18:26
 **/
@Component
@Slf4j
public class AppWorker extends Worker {

    private static ThreadPoolExecutor appExecutor;

    static{
        // 给线程命名
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("AppWorker-worker-%d").build();
        // 通过核心数确定线程数
        int processors = Runtime.getRuntime().availableProcessors();
        log.info("AppWorker:processors:{}", processors);
        appExecutor = new MyThreadPoolExecutor(processors,
                processors * 2,
                0L,
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(1000),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    public void subTask(Runnable runnable) {
        appExecutor.submit(runnable);
    }
}
