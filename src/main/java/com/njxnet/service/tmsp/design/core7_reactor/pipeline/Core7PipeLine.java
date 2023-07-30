package com.njxnet.service.tmsp.design.core7_reactor.pipeline;

import com.njxnet.service.tmsp.design.core3_pipeline.ValveContext;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.base.BasePipeLine;
import com.njxnet.service.tmsp.model.info.SendInfo;

/**
 * @program: TMSP
 * @description: 当前模块的管道类
 * @author: Stone
 * @create: 2023-07-30 18:29
 **/
public class Core7PipeLine extends BasePipeLine<Core7Valve, ChannelContext<SendInfo>> {
    @Override
    public void invoke(ValveContext context) {

    }
}
