package com.njxnet.service.tmsp.design.core6_listeners.listener;

import cn.hutool.core.collection.CollectionUtil;
import com.njxnet.service.tmsp.design.core6_listeners.event.Event;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.BookEvent;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.ErrorEvent;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.OfflineEvent;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.PublishEvent;
import com.njxnet.service.tmsp.design.core6_listeners.listener.lifecycle.LifeCycle;
import com.njxnet.service.tmsp.utils.ApplicationContextUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: TMSP
 * @description: 事件生命周期监听发布者
 * @author: Stone
 * @create: 2023-07-24 21:50
 **/
@Component
public class LifeCycleDispatcher implements EventListener{
    @Override
    public void handleEvent(Event event) {
        List<LifeCycle> lifeCycleList = ApplicationContextUtil.getBeansOfType(LifeCycle.class);

        if (CollectionUtil.isEmpty(lifeCycleList)) {
            return;
        }

        if (event instanceof PublishEvent) {
            PublishEvent publishEvent = (PublishEvent) event;
            lifeCycleList.forEach(lifeCycle -> {
                lifeCycle.onPublish(publishEvent);
            });
        }

        if (event instanceof BookEvent) {
            BookEvent bookEvent = (BookEvent) event;
            lifeCycleList.forEach(lifeCycle -> {
                lifeCycle.onBook(bookEvent);
            });
        }

        if (event instanceof ErrorEvent) {
            ErrorEvent errorEvent = (ErrorEvent) event;
            lifeCycleList.forEach(lifeCycle -> {
                lifeCycle.onError(errorEvent);
            });
        }

        if (event instanceof OfflineEvent) {
            OfflineEvent offlineEvent = (OfflineEvent) event;
            lifeCycleList.forEach(lifeCycle -> {
                lifeCycle.onOffline(offlineEvent);
            });
        }

    }
}
