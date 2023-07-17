package com.njxnet.service.tmsp.design.core5_aop;

import lombok.Data;
import org.springframework.aop.framework.ProxyFactoryBean;

/**
 * @program: TMSP
 * @description: aop的bean工厂，用于在后工厂处理器中创建Proxy bean放入Spring容器中
 * @author: Stone
 * @create: 2023-07-17 09:37
 **/
@Data
public class RpcProviderFactoryBean extends ProxyFactoryBean {
    private String proxyBeanName;
}
