package com.njxnet.service.tmsp.config.datasource.context;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class DsAspect {

    /*
    * 切入点，拦截类上、方法上有注解的方案，用于切换数据源
    * */
    @Pointcut("@annotation(com.njxnet.service.tmsp.config.datasource.context.DsAno) || @within(com.njxnet.service.tmsp.config.datasource.context.DsAno)")
    public void pointcut(){
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        DsAno ds = getDsAno(joinPoint);
        try {
            if ((ds != null && StringUtils.isNotBlank(ds.ds())) || ds.value()!=null){
                // 决定写入哪个DB
                DsContextHolder.set(StringUtils.isNotBlank(ds.ds())? ds.ds() : ds.value().getName() );
            }
            return joinPoint.proceed();
        }finally {
            // 清空上下文
            if (ds != null) {
                DsContextHolder.reset();
            }
        }
    }

    private DsAno getDsAno(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DsAno ds = method.getAnnotation(DsAno.class);
        if (ds == null) {
            // 类上获取注解
            ds = (DsAno) joinPoint.getSignature().getDeclaringType().getAnnotation(DsAno.class);
        }
        return ds;
    }

}
