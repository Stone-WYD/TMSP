package com.njxnet.service.tmsp.core3.pipeline.impl;

import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core2.send.SendMessageOuterPostProcessor2;
import com.njxnet.service.tmsp.model.info.SendInfo;
import org.springframework.stereotype.Component;

/**
 * @program: TMSP
 * @description: 发送短信前对短信内容进行校验，内部会使用Pipeline的设计模式
 * @author: Stone
 * @create: 2023-07-11 21:12
 **/
@Component
public class SendMessageOuterCheckValidatePostProcessor implements SendMessageOuterPostProcessor2 {



    @Override
    public boolean handleBefore(PostContext<SendInfo> postContext) {
        return SendMessageOuterPostProcessor2.super.handleBefore(postContext);
    }

    @Override
    public void handleAfter(PostContext<SendInfo> postContext) {
        SendMessageOuterPostProcessor2.super.handleAfter(postContext);
    }

    @Override
    public int getPriprity() {
        return SendMessageOuterPostProcessor2.super.getPriprity();
    }


}
