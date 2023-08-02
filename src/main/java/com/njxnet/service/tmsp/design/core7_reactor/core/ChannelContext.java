package com.njxnet.service.tmsp.design.core7_reactor.core;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.design.core3_pipeline.ValveContext;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: TMSP
 * @description: channel 上下文
 * @author: Stone
 * @create: 2023-07-30 18:34
 **/
@Data
public class ChannelContext<T>{
    /**
    * 驱动服务的参数
    * */
    private Map<String, Object> paramMap = new HashMap<>();

    /**
     * 上下文参数
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
