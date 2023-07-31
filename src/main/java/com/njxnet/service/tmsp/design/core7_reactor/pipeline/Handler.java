package com.njxnet.service.tmsp.design.core7_reactor.pipeline;

/**
 * @program: TMSP
 * @description: 处理类
 * @author: Stone
 * @create: 2023-07-31 10:54
 **/
@FunctionalInterface
public interface Handler {
    void handle(ChannelContext channelContext);
}
