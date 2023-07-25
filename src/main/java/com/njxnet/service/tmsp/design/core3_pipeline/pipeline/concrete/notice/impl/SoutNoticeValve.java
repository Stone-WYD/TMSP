package com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.notice.impl;

import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.notice.NoticeValve;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.notice.NoticeValveContext;
import com.njxnet.service.tmsp.design.core4_aware.ValidateContent;
import com.njxnet.service.tmsp.design.core4_aware.ValidateContentAware;
import org.springframework.stereotype.Component;

/**
 * @program: TMSP
 * @description: 将通知输出到控制台
 * @author: Stone
 * @create: 2023-07-12 21:30
 **/
@Component
public class SoutNoticeValve extends NoticeValve implements ValidateContentAware {

    private ValidateContent validateContent;

    @Override
    public void invoke(NoticeValveContext context) {
        // 此处不需要 NoticeValveContext 有什么处理
        if (validateContent != null) {
            System.out.println("==========发送的短信内容不太好，需要把一些内容打印到控制台==========");
            System.out.println("短信内容为：" + validateContent.getContent());
            System.out.println("短信不良内容为：" + validateContent.getIllegalContent());
            System.out.println("提示信息为：" + validateContent.getCause());
            System.out.println("==========发送的短信内容不太好，内容打印到控制台了==========");
        }
    }

    @Override
    public Integer getPriprity() {
        return 1;
    }

    @Override
    public void setValidateContent(ValidateContent validateContent) {
        this.validateContent = validateContent;
    }
}
