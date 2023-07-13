package com.njxnet.service.tmsp.design.core3_pipeline;

/**
 * @program: TMSP
 * @description: 阀门，在管道中的处理类
 * @author: Stone
 * @create: 2023-07-11 21:33
 **/

public interface Valve<C extends ValveContext> {

    void invoke(C context);

    void setNext(Valve valve);

    Valve getNext();

    Integer getPriprity();


}
