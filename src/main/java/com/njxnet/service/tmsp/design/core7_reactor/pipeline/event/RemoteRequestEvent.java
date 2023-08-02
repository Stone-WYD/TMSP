package com.njxnet.service.tmsp.design.core7_reactor.pipeline.event;

import com.njxnet.service.tmsp.design.core7_reactor.core.ChannelContext;

/**
 * @program: TMSP
 * @description: 远程调用事件
 * @author: Stone
 * @create: 2023-07-31 11:42
 **/
public class RemoteRequestEvent extends BaseEvent{


    public RemoteRequestEvent(ChannelContext channelContext) {
        super(channelContext);
    }
}
