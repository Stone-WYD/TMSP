package com.njxnet.service.tmsp.util;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class ApplicationContextUtil implements ApplicationContextAware, EnvironmentAware {

    public static ApplicationContext applicationContext;

    private static Environment environment;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        ApplicationContextUtil.environment = environment;
    }

    /**
     * 向Spring中注入bean
     */
    public static void autowiredBean(Object instance){

        applicationContext.getAutowireCapableBeanFactory().autowireBean(instance);
    }

    /**
     * 根据名字获取 bean
     */
    public static Object getBeanByName(String name){
        try {
            return applicationContext.getBean(name);
        } catch (Exception e){
            return null;
        }
    }

    /**
     * 获取某种bean
     */
    public static <T> T getBeanOfType(Class<T> type){
        List<T> beansOfType = getBeansOfType(type);
        if (CollectionUtil.isEmpty(beansOfType)) {
            return null;
        }
        return beansOfType.get(0);
    }

    /**
     * 获取某种bean的list
     */
    public static <T> List<T> getBeansOfType(Class<T> type){
        List<T> result = new ArrayList<>();
        Map<String, T> beansOfType = applicationContext.getBeansOfType(type);
        if (beansOfType == null) {
            return null;
        }
        for (Map.Entry<String, T> b : beansOfType.entrySet()) {
            result.add(b.getValue());
        }
        return result;
    }

    /**
     * 查找某种bean的所有名字
     */
    public static List<String> getBeanNamesForType(Class type){
        String[] tem = applicationContext.getBeanNamesForType(type);
        if (tem == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(tem);

    }

    /**
     * 发布事件消息
     *
     */
    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    /**
     * 获取配置
     *
     */
    public static String getConfig(String key) {
        return environment.getProperty(key);
    }

}
