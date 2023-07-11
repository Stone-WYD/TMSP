package com.njxnet.service.tmsp.core3;

/**
 * @program: TMSP
 * @description: 管道接口类
 * @author: Stone
 * @create: 2023-07-11 21:19
 **/
public interface Pipeline {

    void invoke(ValveContext context);
}
