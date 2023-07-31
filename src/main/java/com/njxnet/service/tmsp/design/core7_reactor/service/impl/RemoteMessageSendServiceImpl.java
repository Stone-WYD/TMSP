package com.njxnet.service.tmsp.design.core7_reactor.service.impl;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.design.core7_reactor.AsynReceptResult;
import com.njxnet.service.tmsp.design.core7_reactor.service.RemoteMessageSendService;
import com.njxnet.service.tmsp.model.info.SendInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: TMSP
 * @description: RemoteMessageSendService的实现类
 * @author: Stone
 * @create: 2023-07-31 10:12
 **/
@Service
public class RemoteMessageSendServiceImpl implements RemoteMessageSendService {

    @Override
    public AjaxResult<String> send(SendInfo sendInfo) {
        return null;
    }

    @Override
    public AsynReceptResult<Map<String, AjaxResult<Boolean>>> getResultList() {
        return null;
    }
}
