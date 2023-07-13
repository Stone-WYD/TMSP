package com.njxnet.service.tmsp.design.core4_aware;

/**
 * @program: TMSP
 * @description: 检测内容获取注入注解
 * @author: Stone
 * @create: 2023-07-13 10:40
 **/
public interface ValidateContentAware extends Aware{
    void setValidateContent(ValidateContent validateContent);
}
