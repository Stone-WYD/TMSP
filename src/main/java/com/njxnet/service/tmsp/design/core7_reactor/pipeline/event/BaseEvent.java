package com.njxnet.service.tmsp.design.core7_reactor.pipeline.event;

import com.njxnet.service.tmsp.design.core7_reactor.pipeline.ChannelContext;

/**
 * @program: TMSP
 * @description:
 * @author: Stone
 * @create: 2023-07-31 11:10
 **/
public class BaseEvent {

    private ChannelContext channelContext;

    public ChannelContext getChannelContext() {
        return channelContext;
    }

    public void setChannelContext(ChannelContext channelContext) {
        this.channelContext = channelContext;
    }
}
