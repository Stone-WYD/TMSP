package com.njxnet.service.tmsp.design.core3_pipeline.apply;

import com.njxnet.service.tmsp.design.core3_pipeline.PipeLine;
import com.njxnet.service.tmsp.util.ApplicationContextUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: TMSP
 * @description: 管道模式配置类
 * @author: Stone
 * @create: 2023-08-03 09:45
 **/
@Configuration
public class PipeLineConfig {

    @Bean
    @ConditionalOnMissingBean(ValidatePipeLineTemplate.class)
    public ValidatePipeLineTemplate validatePipeLineTemplate(){
        ValidatePipeLineTemplate template = new ValidatePipeLineTemplate();

        // 添加管道
        template.getValidatePipeLineList().add((PipeLine) ApplicationContextUtil.getBeanByName("validatePipeLine"));
        template.getValidatePipeLineList().add((PipeLine) ApplicationContextUtil.getBeanByName("noticePipeLine"));

        return template;
    }
}
