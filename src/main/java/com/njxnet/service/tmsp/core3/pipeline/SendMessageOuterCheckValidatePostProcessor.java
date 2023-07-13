package com.njxnet.service.tmsp.core3.pipeline;

import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core2.send.SendMessageOuterPostProcessor2;
import com.njxnet.service.tmsp.core3.PipeLine;
import com.njxnet.service.tmsp.core3.ValveContext;
import com.njxnet.service.tmsp.model.info.SendInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: TMSP
 * @description: 发送短信前对短信内容进行校验，内部会使用Pipeline的设计模式
 * @author: Stone
 * @create: 2023-07-11 21:12
 **/
@Component
public class SendMessageOuterCheckValidatePostProcessor implements SendMessageOuterPostProcessor2 {


    @Resource
    private ValidatePipeLineTemplate template;

    @Override
    public void handleAfter(PostContext<SendInfo> postContext) {
        SendInfo sendInfo = postContext.getT();

        ValveContext valveContext = new ValveContext();
        valveContext.getContextMap().put("sendInfo", sendInfo);

        for (PipeLine pipeLine : template.getValidatePipeLineList()) {
            pipeLine.invoke(valveContext);
        }
    }

    @Override
    public int getPriprity() {
        return -1;
    }


}
