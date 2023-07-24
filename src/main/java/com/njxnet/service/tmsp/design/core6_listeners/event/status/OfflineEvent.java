package com.njxnet.service.tmsp.design.core6_listeners.event.status;

import com.njxnet.service.tmsp.design.core6_listeners.event.Event;
import com.njxnet.service.tmsp.design.core6_listeners.model.UserInfo;

/**
 * @program: TMSP
 * @description: 处于下线状态的事件
 * @author: Stone
 * @create: 2023-07-24 20:45
 **/
public class OfflineEvent implements Event {

    private String contentKey;

    private UserInfo publishUser;
}
