package com.njxnet.service.tmsp.design.core6_listeners.model;

import lombok.Data;

/**
 * @program: TMSP
 * @description: 发布数据的用户信息，带有生命周期的监听者模式使用
 * @author: Stone
 * @create: 2023-07-24 20:37
 **/
@Data
public class UserInfo {

    private String id;

    private String userName;

    private String group;
}
