package com.njxnet.service.tmsp.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 开启注解
        /* registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**"); */
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
/*        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns("/manager/login",
                        "/manager/getCaptcha",
                        "/webjars/**",
                        "/swagger-resources",
                        "/favicon.ico",
                        "/doc.html");*/
    }

}
