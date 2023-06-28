package com.njxnet.service.tmsp.config.datasource.init;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "spring.dynamic")
public class DsProperties {
    /*
    * 默认数据源，短信平台数据库 tmsp
    * */
    private String primary;

    /*
    * 多数据源配置
    * */
    private Map<String, DataSourceProperties> datasource;

}
