package com.njxnet.service.tmsp.design.core6_listeners;

import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.validate.ValidateValve;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.validate.ValidateValveContext;
import com.njxnet.service.tmsp.design.core6_listeners.event.status.PublishEvent;
import com.njxnet.service.tmsp.design.core6_listeners.model.UserInfo;
import com.njxnet.service.tmsp.model.info.SendInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: TMSP
 * @description: 发出处于发布态事件的管道阀门
 * @author: Stone
 * @create: 2023-07-24 20:56
 **/
@Component
public class ResultEventPublishValidateValve extends ValidateValve {

    @Resource
    private EventMulticaster eventMulticaster;

    @Override
    public void invoke(ValidateValveContext context) {
        SendInfo sendInfo = context.getSendInfo();

        if (context.isPass()) {
            // 创建事件
            PublishEvent publishEvent = new PublishEvent();
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(sendInfo.getUserName());
            userInfo.setId(sendInfo.getType());
            publishEvent.setPublishUser(userInfo);

            // 发送事件
            eventMulticaster.publishEvent(publishEvent);
        }

    }

    @Override
    public Integer getPriprity() {
        // 确保最后被调用
        return Integer.MAX_VALUE - 1;
    }
}
