package com.njxnet.service.tmsp.design.core3_pipeline;

/**
 * @program: TMSP
 * @description: 管道接口类
 * @author: Stone
 * @create: 2023-07-11 21:19
 **/
public interface PipeLine {

    /**
    * @Description: 启动pipeline
    * @Author: Stone
    * @Date: 2023/7/11
    */
    void invoke(ValveContext context);
}
