package com.njxnet.service.tmsp.design.core3_pipeline;

/**
 * @program: TMSP
 * @description: 管道接口类
 * @author: Stone
 * @create: 2023-07-11 21:19
 **/
public interface PipeLine {

    /**
     * @Description: 启动pipeline, 管道的 context 应该是通用的类型，
     * 此时是要做一些管道专属处理，具体管道 valve 的 context 可以是具体类型的
     * @Author: Stone
     * @Date: 2023/7/11
     */
    void invoke(ValveContext context);
}
