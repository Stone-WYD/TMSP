package com.njxnet.service.tmsp.design.core7_reactor;

import com.njxnet.service.tmsp.design.core7_reactor.pipeline.Handler;
import com.njxnet.service.tmsp.design.core7_reactor.worker.AppWorker;
import com.njxnet.service.tmsp.design.core7_reactor.worker.NetWorker;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: TMSP
 * @description:
 * @author: Stone
 * @create: 2023-07-31 10:13
 **/
@Slf4j
public class AsynRemoteChannel {

    private final EventDispatcher eventDispatcher;

    public AsynRemoteChannel(NetWorker netWorker, AppWorker appWorker) {
        eventDispatcher = new EventDispatcher(appWorker, netWorker);
    }

    public void addPrepareHandler(Handler handler) {
        eventDispatcher.getPreparePipeLine().getHandlerList().add(handler);
    }

    public void addResultRenderHandler(Handler handler){
        eventDispatcher.getResultRenderPipeLine().getHandlerList().add(handler);
    }
}
