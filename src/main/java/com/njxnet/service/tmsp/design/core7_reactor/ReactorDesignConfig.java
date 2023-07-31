package com.njxnet.service.tmsp.design.core7_reactor;

import cn.hutool.json.JSONUtil;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.design.core7_reactor.pipeline.ChannelContext;
import com.njxnet.service.tmsp.design.core7_reactor.service.AsynRemoteServiceProxy;
import com.njxnet.service.tmsp.design.core7_reactor.service.RemoteMessageSendService;
import com.njxnet.service.tmsp.design.core7_reactor.worker.AppWorker;
import com.njxnet.service.tmsp.design.core7_reactor.worker.NetWorker;
import com.njxnet.service.tmsp.model.info.SendInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @program: TMSP
 * @description: reactor设计模式中用到的配置类，进行一些bean的配置
 * @author: Stone
 * @create: 2023-07-31 10:09
 **/
@Configuration
public class ReactorDesignConfig {


    @Bean
    public AsynRemoteChannel remoteMessageChannel(RemoteMessageSendService remoteMessageSendService,
                                                  NetWorker netWorker, AppWorker appWorker){
        AsynRemoteChannel asynRemoteChannel = new AsynRemoteChannel(netWorker, appWorker);

        // 前置处理中的远程调用参数打印
        asynRemoteChannel.addPrepareHandler(
                channelContext -> System.out.println(JSONUtil.toJsonStr(channelContext.getParamMap()))
        );

        // 后置处理中的远程调用结果打印
        asynRemoteChannel.addResultRenderHandler(
                channelContext -> System.out.println(JSONUtil.toJsonStr(channelContext.getAsynReceptResult()))
        );

        // 构建短信发送服务
        AsynRemoteServiceProxy asynRemoteServiceProxy = buildAsynRemoteServiceProxy(remoteMessageSendService);
        asynRemoteChannel.bindRemoteService(asynRemoteServiceProxy);



        return asynRemoteChannel;
    }

    private static AsynRemoteServiceProxy buildAsynRemoteServiceProxy
            (RemoteMessageSendService remoteMessageSendService){
        return new AsynRemoteServiceProxy<Boolean>() {
            @Override
            public AjaxResult<String> call(ChannelContext channelContext) {
                Map contextMap = channelContext.getContextMap();
                SendInfo sendInfo = (SendInfo) contextMap.get("sendInfo");
                return remoteMessageSendService.send(sendInfo);
            }

            @Override
            public AsynReceptResult<Map<String, AjaxResult<Boolean>>> requestReceipt() {
                return remoteMessageSendService.getResultList();
            }
        };
    }
}
