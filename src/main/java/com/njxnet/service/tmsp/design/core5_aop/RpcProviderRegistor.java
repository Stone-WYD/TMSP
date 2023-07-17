package com.njxnet.service.tmsp.design.core5_aop;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: TMSP
 * @description: 注解扫描与AOP注册
 * @author: Stone
 * @create: 2023-07-17 09:43
 **/
@Component
public class RpcProviderRegistor implements BeanFactoryPostProcessor, ApplicationContextAware,
        ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;
    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
    * @Description: 后工厂处理，此处只是给beanFactory赋值，因为这个方法执行的时机最早
    * @Author: Stone
    * @Date: 2023/7/17
    */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        configurableListableBeanFactory = beanFactory;
    }

    /**
     * @Description: 当刷新容器事件完成时才注入proxy，此时大多数bean已经被创建并加入Spring容器中了
     * @Author: Stone
     * @Date: 2023/7/17
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            List<ProxyInfo> proxyInfoList = scanRpcProvider();

            for (ProxyInfo proxyInfo : proxyInfoList) {
                // 创建工厂Bean对象
                RpcProviderFactoryBean rpcProviderFactoryBean = createProxyBean(proxyInfo.clientClass);
                rpcProviderFactoryBean.setProxyBeanName(proxyInfo.beanName);
                rpcProviderFactoryBean.setBeanFactory(configurableListableBeanFactory);

                configurableListableBeanFactory.registerSingleton(proxyInfo.clientClass.getName(), rpcProviderFactoryBean);
            }
        } catch (Exception e){
            throw new RuntimeException("RpcProviderRegistor error:" + e.getMessage(), e);
        }
    }

    /**
     * @Description: 扫描所有RpcProvicer注解类，封装成ProxyInfo
     * @Author: Stone
     * @Date: 2023/7/17
     */
    private List<ProxyInfo> scanRpcProvider() {
        List<ProxyInfo> result = new ArrayList<>();
        applicationContext.getBeansWithAnnotation(RpcProvider.class).forEach((name, instance)->{
            ProxyInfo proxyInfo = new ProxyInfo();
            proxyInfo.beanName = name;
            RpcProvider rpcProvider = AnnotatedElementUtils.getMergedAnnotation(instance.getClass(), RpcProvider.class);
            Class<?> clientClass = rpcProvider.clientClass();
            if (clientClass == null) {
                throw new RuntimeException("@RpcProvider注解的clientClass属性不能为空！");
            }
            proxyInfo.clientClass = clientClass;
            result.add(proxyInfo);
        });
        return result;
    }

    /**
    * @Description: 根据RpcProvicer注解上的类信息，创建某接口的bean工厂
    * @Author: Stone
    * @Date: 2023/7/17
    */
    private RpcProviderFactoryBean createProxyBean(Class interfaceClazz) {
        RpcProviderFactoryBean factoryBean = new RpcProviderFactoryBean();
        factoryBean.setInterfaces(interfaceClazz);
        // 代理类不只增强目标类的public方法
        factoryBean.setProxyTargetClass(false);
        factoryBean.setInterceptorNames("rpcProviderMethodInterceptor");
        return factoryBean;
    }

    private static class ProxyInfo{
        public Class clientClass;
        public String beanName;
    }
}
