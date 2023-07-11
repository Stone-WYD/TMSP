package com.njxnet.service.tmsp.core3.pipeline.base.validate.impl;

import cn.hutool.core.util.StrUtil;
import com.njxnet.service.tmsp.core3.pipeline.base.validate.ValidateValve;
import com.njxnet.service.tmsp.core3.pipeline.base.validate.ValidateValveContext;
import org.springframework.stereotype.Component;

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
                context.setViolence(true);
            }
        }
        context.setViolence(false);
        super.getNext().invoke(context);
    }

    @Override
    public Integer getPriprity() {
        return null;
    }
}
