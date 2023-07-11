package com.njxnet.service.tmsp.core3;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: TMSP
 * @description: 管道上下文基类
 * @author: Stone
 * @create: 2023-07-11 21:29
 **/
public class  ValveContext<T> {

    private Map<String, T> contextMap = new HashMap<>();

    public Map<String, T> getContextMap() {
        return contextMap;
    }
}
