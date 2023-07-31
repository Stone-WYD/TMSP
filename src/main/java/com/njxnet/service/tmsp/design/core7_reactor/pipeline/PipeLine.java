package com.njxnet.service.tmsp.design.core7_reactor.pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: TMSP
 * @description: reactor设计模式中的pipeLine
 * @author: Stone
 * @create: 2023-07-31 10:52
 **/
public class PipeLine {

    private List<Handler> handlerList = new ArrayList<>();

    private Handler defaultHandler;

    public List<Handler> getHandlerList(){
        return handlerList;
    }

    public void addhandler(Handler handler) {
        this.handlerList.add(handler);
    }

    public Handler getDefaultHandler() {
        return defaultHandler;
    }

    public void setDefaultHandler(Handler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }
}
