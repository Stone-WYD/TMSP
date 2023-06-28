package com.njxnet.service.tmsp.config.datasource.init;

import com.google.common.collect.Maps;
import com.njxnet.service.tmsp.config.datasource.context.DsAspect;
import com.njxnet.service.tmsp.config.datasource.context.DsEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "spring.dynamic", name = "primary")
@EnableConfigurationProperties(DsProperties.class)
public class DataSourceConfig {

    public DataSourceConfig(){
        log.info("动态数据源初始化！");
    }

    @Bean
    public DsAspect dsAspect(){
        return new DsAspect();
    }

    /*
    * 整合主从数据源
    * */
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
}
