package com.njxnet.service.tmsp.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.njxnet.service.tmsp.utils.MyThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class MyConfig {

    @Bean
    public ThreadPoolExecutor myThreadPoolExecutor(){
        // 给线程命名
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("myThreadPoolExecutor-worker-%d").build();
        // 通过核心数确定线程数
        int processors = Runtime.getRuntime().availableProcessors();
        log.info("processors:{}", processors);
        MyThreadPoolExecutor myThreadPoolExecutor = new MyThreadPoolExecutor(processors,
                processors * 2,
                0L,
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(1000),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        return myThreadPoolExecutor;
    }
}
