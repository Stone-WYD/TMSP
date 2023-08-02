package com.njxnet.service.tmsp.design.core7_reactor.pipeline.event;

import com.njxnet.service.tmsp.design.core7_reactor.core.ChannelContext;

/**
 * @program: TMSP
 * @description: 结果渲染事件
 * @author: Stone
 * @create: 2023-07-31 11:41
 **/
public class ResultRenderEvent extends BaseEvent{

    public ResultRenderEvent(ChannelContext channelContext) {
        super(channelContext);
    }
}
