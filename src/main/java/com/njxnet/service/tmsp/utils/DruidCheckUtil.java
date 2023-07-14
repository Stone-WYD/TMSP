package com.njxnet.service.tmsp.utils;


import com.njxnet.service.tmsp.config.datasource.init.DataSourceConfig;
import org.springframework.util.ClassUtils;

/**
 * @program: BE
 * @description: druid相关工具类
 * @author: Stone
 * @create: 2023-07-13 11:59
 **/
public class DruidCheckUtil {

    /**
    * @Description: 判断是否包含durid相关数据包
    * @Author: Stone
    * @Date: 2023/7/13
    */
    public static boolean hasDuridPkg(){
        return ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource",
                DataSourceConfig.class.getClassLoader());
    }
}
