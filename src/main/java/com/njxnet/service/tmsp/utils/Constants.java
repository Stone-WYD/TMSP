package com.njxnet.service.tmsp.utils;


import com.njxnet.service.tmsp.model.CaptchaModel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 常量定义
 * @ClassName Constants
 * @Author 模板
 * @Date 2019/12/12 15:53
 * @Version 1.0
 **/
public interface Constants {

    String secretKey = "nanjingxnet";

    /**
     * 验证码超时时间 10分钟
     */
    long CAP_TIMEOUT = 1000 * 60 * 10L;

    /**
     * 验证码
     */
    Map<String, CaptchaModel> capMap = new ConcurrentHashMap<>();

}



