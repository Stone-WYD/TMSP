package com.njxnet.service.tmsp.design.core5_aop.check;

import com.njxnet.service.tmsp.design.core5_aop.RpcProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @program: TMSP
 * @description: 项目启动时，校验@ForRpc对应的方法与接口中的方法是否有相同的签名
 * @author: Stone
 * @create: 2023-07-17 16:37
 **/
@Component
public class RpcCheckPostProcessor implements BeanPostProcessor {



    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        RpcProvider rpcProvider = AnnotatedElementUtils.findMergedAnnotation(bean.getClass(), RpcProvider.class);
        if (rpcProvider != null) {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                // 这里只看public方法即可，给外部调用的方法不能是private或者protected
                ForRpc forRpc = AnnotatedElementUtils.findMergedAnnotation(method, ForRpc.class);
                if (forRpc != null){
                    try {
                        rpcProvider.clientClass().getMethod(method.getName(), method.getParameterTypes());
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException( "@ForRpc标注的方法" + bean.getClass() + method.getName() + ": 没有与注解@RpcProvider标注的接口的方法对应，请检查代码！");
                    }
                }
            }
            return bean;
        }
        return bean;
    }
}
