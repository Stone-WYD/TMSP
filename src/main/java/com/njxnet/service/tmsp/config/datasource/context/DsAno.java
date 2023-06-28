package com.njxnet.service.tmsp.config.datasource.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DsAno {
    /*
    * 启用的数据源
    * */
    String ds() default "";

    DsEnum value() default DsEnum.MASTER;
}
