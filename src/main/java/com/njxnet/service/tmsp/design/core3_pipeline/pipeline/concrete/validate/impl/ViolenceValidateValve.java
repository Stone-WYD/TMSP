package com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.validate.impl;

import cn.hutool.core.util.StrUtil;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.validate.ValidateValve;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.validate.ValidateValveContext;
import com.njxnet.service.tmsp.design.core4_aware.ValidateContent;
import com.njxnet.service.tmsp.design.core4_aware.ValidateContentAware;
import com.njxnet.service.tmsp.utils.ApplicationContextUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: TMSP
 * @description: 具体的检测阀：暴力内容检测阀
 * @author: Stone
 * @create: 2023-07-11 22:37
 **/
@Component
public class ViolenceValidateValve extends ValidateValve {


    @Override
    public void invoke(ValidateValveContext context) {
        // 暴力内容检测
        String content = context.getContent();
        if (StrUtil.isNotBlank(content)){
            if (content.contains("woc")) {
                ValidateContent validateContent = new ValidateContent();
                validateContent.setContent(content);
                validateContent.setIllegalContent("woc");
                validateContent.setCause("发送的短信中不应该包含woc这类脏话！！");
                invokeWare(validateContent);
            }
        }
        super.getNext().invoke(context);
    }

    @Override
    public Integer getPriprity() {
        return 1;
    }

    private void invokeWare(ValidateContent validateContent){
        // 获取 contentAwareList set content值
        List<ValidateContentAware> contentAwareList = ApplicationContextUtil.getBeansOfType(ValidateContentAware.class);

        for (ValidateContentAware validateContentAware : contentAwareList) {
            validateContentAware.setValidateContent(validateContent);
        }
    }


}
