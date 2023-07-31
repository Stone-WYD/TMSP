package com.njxnet.service.tmsp.design.core7_reactor;

import com.njxnet.service.tmsp.design.core7_reactor.pipeline.event.BaseEvent;
import com.njxnet.service.tmsp.design.core7_reactor.pipeline.PipeLine;
import com.njxnet.service.tmsp.design.core7_reactor.pipeline.event.PrepareEvent;
import com.njxnet.service.tmsp.design.core7_reactor.pipeline.event.RemoteRequestEvent;
import com.njxnet.service.tmsp.design.core7_reactor.pipeline.event.ResultRenderEvent;
import com.njxnet.service.tmsp.design.core7_reactor.worker.AppWorker;
import com.njxnet.service.tmsp.design.core7_reactor.worker.NetWorker;

/**
 * @program: TMSP
 * @description: 事件分发器
 * @author: Stone
 * @create: 2023-07-30 18:25
 **/
public class EventDispatcher {

    private AppWorker appWorker;

    private NetWorker netWorker;

    // 预处理通道
    private PipeLine preparePipeLine = new PipeLine();

    // 远程请求通道
    private PipeLine remoteRequestPipeLine = new PipeLine();

    // 结果渲染通道
    private PipeLine resultRenderPipeLine = new PipeLine();

    public EventDispatcher(AppWorker appWorker, NetWorker netWorker) {
        this.appWorker = appWorker;
        this.netWorker = netWorker;
    }

    public EventDispatcher() {
    }

    public PipeLine getPreparePipeLine() {
        return preparePipeLine;
    }

    public PipeLine getRemoteRequestPipeLine() {
        return remoteRequestPipeLine;
    }

    public PipeLine getResultRenderPipeLine() {
        return resultRenderPipeLine;
    }



    private void walkPipeLine(PipeLine pipeLine, BaseEvent baseEvent){
        // 驱动管道开始工作
        pipeLine.getHandlerList().forEach(handler -> {
            handler.handle(baseEvent.getChannelContext());
        });
        // 默认handler最后工作
        if (pipeLine.getDefaultHandler() != null) {
            pipeLine.getDefaultHandler().handle(baseEvent.getChannelContext());
        }
    }

    // TODO: 2023/7/31 扩展性设计
    public void dispatch(BaseEvent baseEvent){
        if (baseEvent instanceof PrepareEvent) {
            // 应用线程分发
            appWorker.subTask(()->{
                walkPipeLine(preparePipeLine, baseEvent);
            });
        }else if (baseEvent instanceof ResultRenderEvent) {
            // 结果线程分发
            appWorker.subTask(()->{
                walkPipeLine(resultRenderPipeLine, baseEvent);
            });
        }else if (baseEvent instanceof RemoteRequestEvent) {
            // 结果线程分发
            netWorker.subTask(()->{
                walkPipeLine(remoteRequestPipeLine, baseEvent);
            });
        }
    }

}
