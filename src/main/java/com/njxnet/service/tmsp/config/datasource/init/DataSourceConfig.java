package com.njxnet.service.tmsp.config.datasource.init;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import com.njxnet.service.tmsp.config.datasource.context.DsAspect;
import com.njxnet.service.tmsp.config.datasource.context.DsEnum;
import com.njxnet.service.tmsp.utils.ApplicationContextUtil;
import com.njxnet.service.tmsp.utils.DruidCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "spring.dynamic", name = "primary")
@EnableConfigurationProperties(DsProperties.class)
public class DataSourceConfig implements EnvironmentAware, DisposableBean {

    public DataSourceConfig(){
        log.info("动态数据源初始化！");
    }

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Bean
    public DsAspect dsAspect(){
        return new DsAspect();
    }

    /**
    * @Description: 整合主从数据源
    * @Author: Stone
    * @Date: 2023/7/14
    */
    @Bean
    @Primary
    public DataSource dataSource(DsProperties dsProperties){
        Map<Object, Object> targetDataSources = Maps.newHashMapWithExpectedSize(dsProperties.getDatasource().size());

        // 根据配置初始化数据源
        dsProperties.getDatasource().forEach((k, v)
                -> targetDataSources.put(k.toLowerCase(), v.initializeDataSourceBuilder().build()));

        // 返回可路由的数据源
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        // 给路由数据源设置默认数据源
        Object key = dsProperties.getPrimary().toUpperCase();
        if (!targetDataSources.containsKey(key)) {
            if (targetDataSources.containsKey(DsEnum.MASTER.getName())){
                // 当没有数据源时，存在MASTER数据源，则将主库作为默认数据源
                key = DsEnum.MASTER.getName();
            } else {
                key = targetDataSources.keySet().iterator().next();
            }
        }
        log.info("路由数据源，默认数据源为：" + key);
        myRoutingDataSource.setDefaultTargetDataSource(targetDataSources.get(key));
        myRoutingDataSource.setTargetDataSources(targetDataSources);

        // 返回路由数据源
        return myRoutingDataSource;
    }

    /**
    * @Description: 根据参数初始化数据库
    * @Author: Stone
    * @Date: 2023/7/14
    */
    public DataSource initDataSource(String prefix, DataSourceProperties properties){

        if (!DruidCheckUtil.hasDuridPkg()) {
            log.info("实例化HikarDataSource:{}", prefix);
            return properties.initializeDataSourceBuilder().build();
        }

        if (properties.getType() == null || !properties.getType().isAssignableFrom(DruidDataSource.class)){
            log.info("实例化HikarDataSource:{}", prefix);
            return properties.initializeDataSourceBuilder().build();
        }

        log.info("实例化DruidDataSource:{}", prefix);
        DruidDataSource druidDataSource = Binder.get(environment).bindOrCreate("spring.dynamic.datasource." + prefix, DruidDataSource.class);
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        return druidDataSource;
    }

    /**
    * @Description: Spring关闭时关闭连接
    * @Author: Stone
    * @Date: 2023/7/14
    */
    @Override
    public void destroy() throws Exception {
        log.info("关闭数据池连接");
        DataSource dataSource = ApplicationContextUtil.getBeanOfType(DataSource.class);
        if (dataSource instanceof MyRoutingDataSource) {
            log.info("关闭动态数据源中各个数据池连接的连接");
            MyRoutingDataSource routingDataSource = (MyRoutingDataSource) dataSource;
            for (DataSource ds : routingDataSource.getResolvedDataSources().values()) {
                // 关闭 druid 数据库连接池
                if (ds instanceof DruidDataSource) {
                    DruidDataSource druidDataSource = (DruidDataSource) ds;
                    if (!druidDataSource.isClosed()) {
                        druidDataSource.close();
                        log.info("关闭掉了一个druid数据库连接池");
                    }
                }
                // 其他连接池关闭
            }
        }
    }

    /**
    * @Description: jvm关闭时关闭数据池连接
    * @Author: Stone
    * @Date: 2023/7/14
    */
    private void addShutHook(MyRoutingDataSource myRoutingDataSource) {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            log.info("关闭数据池连接");
            for (DataSource ds : myRoutingDataSource.getResolvedDataSources().values()) {
                // 关闭 druid 数据库连接池
                if (ds instanceof DruidDataSource) {
                    DruidDataSource druidDataSource = (DruidDataSource) ds;
                    if (!druidDataSource.isClosed()) {
                        druidDataSource.close();
                        log.info("关闭掉了一个druid数据库连接池");
                    }
                }
                // 其他连接池关闭
            }
        }));
    }
}
