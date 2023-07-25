package com.njxnet.service.tmsp.design.core6_listeners.listener;

import com.njxnet.service.tmsp.design.core6_listeners.event.Event;

public interface EventListener<T extends Event>{

    void handleEvent(T event);
}
