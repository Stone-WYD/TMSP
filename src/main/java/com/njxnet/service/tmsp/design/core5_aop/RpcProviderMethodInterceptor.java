package com.njxnet.service.tmsp.design.core5_aop;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.common.BaseException;
import lombok.extern.slf4j.Slf4j;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * @program: TMSP
 * @description: aop拦截类，远程结果封装代码
 * @author: Stone
 * @create: 2023-07-17 11:51
 **/
@Service("rpcProviderMethodInterceptor")
@Slf4j
public class RpcProviderMethodInterceptor implements MethodInterceptor, Advice, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 获取代理工厂
        RpcProviderFactoryBean rpcProviderFactoryBean = (RpcProviderFactoryBean) applicationContext
                .getBean("&" + invocation.getMethod().getDeclaringClass().getName());

        // 获取被代理的实例
        Object proxyBean = applicationContext.getBean(rpcProviderFactoryBean.getProxyBeanName());

        Method currentMethod = invocation.getMethod();
        Method proxyMethod = proxyBean.getClass().getMethod(currentMethod.getName(), currentMethod.getParameterTypes());


        try{
            Object data = proxyMethod.invoke(proxyBean, invocation.getArguments());
            AjaxResult<Object> result = new AjaxResult<>();
            result.setData(data);
            return AjaxResultUtil.getTrueAjaxResult(result);
        } catch (BaseException be){
            // 返回可预见的错误
            return AjaxResultUtil.getBussiseFalseAjaxResult(new AjaxResult<>(), be.getMessage(), be.getCode());
        } catch (Exception e){
            // 返回不可预见的错误
            Throwable throwable = ExceptionUtil.getRootCause(e);
            log.error(throwable.getMessage(), e);
            return AjaxResultUtil.getFalseAjaxResult(new AjaxResult<>());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RpcProviderMethodInterceptor.applicationContext = applicationContext;
    }


}
