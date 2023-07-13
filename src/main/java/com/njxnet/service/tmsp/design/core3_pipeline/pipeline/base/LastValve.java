package com.njxnet.service.tmsp.design.core3_pipeline.pipeline.base;

import com.njxnet.service.tmsp.design.core3_pipeline.Valve;
import com.njxnet.service.tmsp.design.core3_pipeline.ValveContext;

/**
 * @program: TMSP
 * @description: 管道中最后一个Valve
 * @author: Stone
 * @create: 2023-07-13 10:53
 **/
public class LastValve<T extends Valve, C extends ValveContext> extends BaseValve<T, C>{
    @Override
    public void invoke(ValveContext context) {

    }

    @Override
    public Integer getPriprity() {
        return Integer.MAX_VALUE;
    }
}
