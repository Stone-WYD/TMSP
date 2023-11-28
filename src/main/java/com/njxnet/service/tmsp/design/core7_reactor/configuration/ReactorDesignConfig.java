package com.njxnet.service.tmsp.design.core7_reactor.configuration;

import cn.hutool.json.JSONUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.design.core7_reactor.core.AsynReceptResult;
import com.njxnet.service.tmsp.design.core7_reactor.core.AsynRemoteChannel;
import com.njxnet.service.tmsp.design.core7_reactor.core.ChannelContext;
import com.njxnet.service.tmsp.design.core7_reactor.core.AsynRemoteServiceProxy;
import com.njxnet.service.tmsp.design.core7_reactor.service.RemoteMessageSendService;
import com.njxnet.service.tmsp.design.core7_reactor.worker.AppWorker;
import com.njxnet.service.tmsp.design.core7_reactor.worker.NetWorker;
import com.njxnet.service.tmsp.model.info.SendInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: TMSP
 * @description: reactor设计模式中用到的配置类，进行一些bean的配置
 * @author: Stone
 * @create: 2023-07-31 10:09
 **/
@Configuration
public class ReactorDesignConfig {

    public final static Cache<String, Boolean> localCache = CacheBuilder
            .newBuilder()
            // 设置 cache 的初始大小为 100（要合理设置该值）
            .initialCapacity(100)
            // 设置并发数为5，即同一时间最多只有5个线程可以向cache中写入
            .concurrencyLevel(5)
            // 写入 1 分钟后过期
            .expireAfterWrite(1, TimeUnit.MINUTES)
            // 构建 cache 实例
            .build();

    @Bean
    public AsynRemoteChannel remoteMessageChannel(RemoteMessageSendService remoteMessageSendService,
                                                  NetWorker netWorker, AppWorker appWorker){
        AsynRemoteChannel asynRemoteChannel = new AsynRemoteChannel(netWorker, appWorker);

        // 构建短信发送服务
        AsynRemoteServiceProxy asynRemoteServiceProxy = buildAsynRemoteServiceProxy(remoteMessageSendService);
        asynRemoteChannel.bindRemoteService(asynRemoteServiceProxy);

        // 前置处理中的远程调用参数打印
        asynRemoteChannel.addPrepareHandler(
                channelContext -> System.out.println(JSONUtil.toJsonStr(channelContext.getParamMap()))
        );

        // 后置处理中的远程调用结果打印
        asynRemoteChannel.addResultRenderHandler(
                channelContext -> System.out.println(JSONUtil.toJsonStr(channelContext.getAsynReceptResult()))
        );

        // 后置处理中的将结果放到队列中
        asynRemoteChannel.addResultRenderHandler(
                // TODO: 2023/8/2 这里做演示用，实际情况如果不是单体的，则要用集群缓存
                channelContext -> {
                    // TODO: 2023/10/17  缓存在代码中并没有被使用到，在当前示例中只用作展示。
                    //  具体如果有需要，localCache 的 key 可以不是 callId，而是根据实际传参而定。如果这样做，在调用方法前，就可以先看是否命中缓存来减小服务器压力
                    localCache.put(channelContext.getCallId(), (Boolean) channelContext.getAsynReceptResult().getData());
                }
        );

        // 启动通道反应堆
        asynRemoteChannel.start();
        return asynRemoteChannel;
    }

    private static AsynRemoteServiceProxy buildAsynRemoteServiceProxy
            (RemoteMessageSendService remoteMessageSendService){
        return new AsynRemoteServiceProxy<Boolean>() {
            @Override
            public AjaxResult<String> call(ChannelContext channelContext) {
                Map paramMap = channelContext.getParamMap();
                SendInfo sendInfo = (SendInfo) paramMap.get("sendInfo");
                return remoteMessageSendService.send(sendInfo);
            }

            @Override
            public AsynReceptResult<Map<String, AjaxResult<Boolean>>> requestReceipt() {
                return remoteMessageSendService.getResultList();
            }
        };
    }
}
