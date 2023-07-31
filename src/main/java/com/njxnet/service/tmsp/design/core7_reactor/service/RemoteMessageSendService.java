package com.njxnet.service.tmsp.design.core7_reactor.service;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.design.core7_reactor.AsynReceptResult;
import com.njxnet.service.tmsp.model.info.SendInfo;

import java.util.Map;

/**
* @Description: reactor模式示例，短信发送接口更为异步化接口
* @Author: Stone
* @Date: 2023/7/30
*/
public interface RemoteMessageSendService {

    /**
    * @Description: 远程调用，返回callId
    * @Author: Stone
    * @Date: 2023/7/30
    */
    AjaxResult<String> send(SendInfo sendInfo);

    /**
    * @Description: 批量获取当前时间段内处理完成的结果。返回的map中，id对应远程调用的callId
    * @Author: Stone
    * @Date: 2023/7/30
    */
    AsynReceptResult<Map<String, AjaxResult<Boolean>>> getResultList();
}
