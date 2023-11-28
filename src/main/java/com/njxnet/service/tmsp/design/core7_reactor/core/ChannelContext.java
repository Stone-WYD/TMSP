package com.njxnet.service.tmsp.design.core7_reactor.core;

import com.njxnet.service.tmsp.common.AjaxResult;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: TMSP
 * @description: channel 上下文。channelContext 随着事件的发生，在 channel 中流动，但是一个 context 同时只有一个线程会处理，所以其中的 map 使用的 HashMap
 * @author: Stone
 * @create: 2023-07-30 18:34
 **/
@Data
public class ChannelContext<T>{

    /**
     * 驱动服务的参数，用于存放传参
     * */
    private Map<String, Object> paramMap = new HashMap<>();

    /**
     * 上下文参数，用户存放中间处理结果
     * */
    private Map<String, Object> contextMap = new HashMap<>();

    /**
     * 远程调用id
     * */
    private String callId;

    /**
     * 远程调用结果
     * */
    private AjaxResult<T> asynReceptResult;
}
