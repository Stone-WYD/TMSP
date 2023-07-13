package com.njxnet.service.tmsp.design.core3_pipeline.apply;

import com.njxnet.service.tmsp.design.core1_postprocessor.PostContext;
import com.njxnet.service.tmsp.design.core2_module.send.SendMessageOuterPostProcessor2;
import com.njxnet.service.tmsp.design.core3_pipeline.PipeLine;
import com.njxnet.service.tmsp.design.core3_pipeline.ValveContext;
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

    /**
    * @Description: 这些内容也可以放在发送短信之前，用于做一些处理
    * @Author: Stone
    * @Date: 2023/7/13
    */
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
