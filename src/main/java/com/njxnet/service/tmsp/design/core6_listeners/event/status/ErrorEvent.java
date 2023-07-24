package com.njxnet.service.tmsp.design.core6_listeners.event.status;

import lombok.Data;

/**
 * @program: TMSP
 * @description: 处于异常状态的事件
 * @author: Stone
 * @create: 2023-07-24 20:47
 **/
@Data
public class ErrorEvent {
    private String contentKey;

    private String errorCode;

    private String errormsg;

    /**
    * @Description: 业务异常阶段
    * @Author: Stone
    * @Date: 2023/7/24
    */
    private String bizStage;
}
