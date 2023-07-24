package com.njxnet.service.tmsp.design.core6_listeners.listener.lifecycle;

import com.njxnet.service.tmsp.design.core6_listeners.event.status.BookEvent;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.ErrorEvent;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.OfflineEvent;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.PublishEvent;

public interface LifeCycle {
    // 发布、订阅、异常、下线
    void onPublish(PublishEvent publishEvent);

    void onBook(BookEvent bookEvent);

    void onError(ErrorEvent errorEvent);

    void onOffline(OfflineEvent offlineEvent);
}
