package com.njxnet.service.tmsp.design.core5_aop;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RpcProvider {

    Class<?> clientClass();
}
