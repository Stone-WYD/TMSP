package com.njxnet.service.tmsp.design.core3_pipeline;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: TMSP
 * @description: 管道上下文基类
 * @author: Stone
 * @create: 2023-07-11 21:29
 **/
public class  ValveContext<T> {

    private Map<String, T> contextMap = new ConcurrentHashMap<>();

    public Map<String, T> getContextMap() {
        return contextMap;
    }
}
