package com.njxnet.service.tmsp.design.core6_listeners.listener.lifecycle.impl;

import com.njxnet.service.tmsp.design.core6_listeners.event.status.BookEvent;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.ErrorEvent;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.OfflineEvent;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.PublishEvent;
import com.njxnet.service.tmsp.design.core6_listeners.listener.lifecycle.LifeCycle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: TMSP
 * @description: 事件各个状态发生时进行通知
 * @author: Stone
 * @create: 2023-07-24 22:11
 **/
@Slf4j
@Component
public class NoticeLifeCycle implements LifeCycle {
    @Override
    public void onPublish(PublishEvent publishEvent) {
        log.info("有事件发布了！发布人信息：{};发布内容key：{}", publishEvent.getPublishUser(), publishEvent.getContentKey());
    }

    @Override
    public void onBook(BookEvent bookEvent) {
        log.info("有订阅事件发生了！订阅人信息：{};订阅内容key：{}", bookEvent.getBookUser(), bookEvent.getContentKey());
    }

    @Override
    public void onError(ErrorEvent errorEvent) {
        log.info("事件出现了异常！异常信息为：{}", errorEvent);
    }

    @Override
    public void onOffline(OfflineEvent offlineEvent) {
        log.info("事件下线了！事件发布人信息为: {};内容key: {}", offlineEvent.getPublishUser(), offlineEvent.getContentKey());
    }
}
