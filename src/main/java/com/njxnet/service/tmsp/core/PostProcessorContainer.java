package com.njxnet.service.tmsp.core;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.context.ApplicationContext;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PostProcessorContainer<T>{

    private Class<BasePostProcessor> monitorPostProcessorClass;

    // 用于获取后处理器
    private ApplicationContext applicationContext;

    private PostProcessorContainer() {
    }

    public static <T> PostProcessorContainer getInstance(Class<T> monitorPostProcessorClass, ApplicationContext applicationContext){
        PostProcessorContainer postProcessorContainer = new PostProcessorContainer();
        postProcessorContainer.monitorPostProcessorClass = monitorPostProcessorClass;
        postProcessorContainer.applicationContext = applicationContext;
        // 使用者无法 new 对象，只能通过该方法获取实例，给方法扩展留空间
        return postProcessorContainer;
    }

    public boolean handleBefore(PostContext<T> postContext){
        List<? extends BasePostProcessor> postProcessors
                = applicationContext.getBeansOfType(monitorPostProcessorClass).values().stream().collect(Collectors.toList());
        if (CollectionUtil.isEmpty(postProcessors)) return true; // 没有操作则返回true

        // 优先级越高，执行时越靠近核心
        Collections.sort(postProcessors, Comparator.comparing((BasePostProcessor o) -> Integer.valueOf(o.getPriprity())));

        for (BasePostProcessor postProcessor : postProcessors) {
            // 如果支持处理，才会处理
            if (postProcessor.support(postContext)) {
                postProcessor.handleBefore(postContext);
            }
        }
        return false; // 有操作则返回false
    }

    public void handleAfter(PostContext<T> postContext){
        List<? extends BasePostProcessor> postProcessors
                = applicationContext.getBeansOfType(monitorPostProcessorClass).values().stream().collect(Collectors.toList());
        if (CollectionUtil.isEmpty(postProcessors)) return ;

        // 优先级越高，执行时越靠近核心
        Collections.sort(postProcessors, Comparator.comparing((BasePostProcessor o) -> Integer.valueOf(o.getPriprity())).reversed());

        for (BasePostProcessor postProcessor : postProcessors) {
            // 如果支持处理，才会处理
            if (postProcessor.support(postContext)) {
                postProcessor.handleAfter(postContext);
            }
        }
    }
}
