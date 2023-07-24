package com.njxnet.service.tmsp.design.core6_listeners.event.status;

import com.njxnet.service.tmsp.design.core6_listeners.event.Event;
import com.njxnet.service.tmsp.design.core6_listeners.model.UserInfo;
import lombok.Data;

/**
 * @program: TMSP
 * @description: 处于发布状态的事件
 * @author: Stone
 * @create: 2023-07-24 20:35
 **/
@Data
public class PublishEvent implements Event {

    private UserInfo publishUser;

    private String contentKey;

}
