package com.njxnet.service.tmsp.design.core5_aop_proxy;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RpcProvider {

    Class<?> clientClass();
}
