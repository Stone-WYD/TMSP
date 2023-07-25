package com.njxnet.service.tmsp.design.core6_listeners.event.status;

import com.njxnet.service.tmsp.design.core6_listeners.model.UserInfo;
import lombok.Data;

/**
 * @program: TMSP
 * @description: 处于订阅状态的事件
 * @author: Stone
 * @create: 2023-07-24 20:43
 **/
@Data
public class BookEvent {

    private UserInfo bookUser;

    private String contentKey;
}
