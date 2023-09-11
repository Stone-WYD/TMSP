package com.njxnet.service.tmsp.design.core6_listeners;

import cn.hutool.core.collection.CollectionUtil;
import com.njxnet.service.tmsp.design.core6_listeners.event.Event;
import com.njxnet.service.tmsp.design.core6_listeners.listener.EventListener;
import com.njxnet.service.tmsp.autil.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: TMSP
 * @description: 事件发布器
 * @author: Stone
 * @create: 2023-07-24 20:52
 **/
@Slf4j
@Component
public class EventMulticaster {

    public void publishEvent(Event event){
        List<EventListener> eventListeners = ApplicationContextUtil.getBeansOfType(EventListener.class);

        if (CollectionUtil.isEmpty(eventListeners)){
            return;
        }

        eventListeners.forEach(eventListener -> {
            try {
                eventListener.handleEvent(event);
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        });
    }
}
