package com.njxnet.service.tmsp.design.core7_reactor.pipeline;

import com.njxnet.service.tmsp.design.core3_pipeline.Valve;
import com.njxnet.service.tmsp.design.core3_pipeline.ValveContext;

/**
 * @program: TMSP
 * @description: 当前模块管道中的阀门
 * @author: Stone
 * @create: 2023-07-30 18:30
 **/
public class Core7Valve implements Valve {
    @Override
    public void invoke(ValveContext context) {

    }

    @Override
    public void setNext(Valve valve) {

    }

    @Override
    public Valve getNext() {
        return null;
    }

    @Override
    public Integer getPriprity() {
        return null;
    }
}
