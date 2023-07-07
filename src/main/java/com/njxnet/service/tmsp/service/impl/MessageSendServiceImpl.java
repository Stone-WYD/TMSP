package com.njxnet.service.tmsp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.common.ResultStatusCode;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.PostProcessorContainer;
import com.njxnet.service.tmsp.core.send.SendMessageOuterPostProcessor;
import com.njxnet.service.tmsp.core.send.SendMessagePostProcessor;
import com.njxnet.service.tmsp.model.dto.TmspPhoneSendDTO;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessageSendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

import static com.njxnet.service.tmsp.common.ResultStatusCode.NO_OUTHANDLER;

@Service
public class MessageSendServiceImpl implements MessageSendService {

    @Resource
    private ThreadPoolExecutor poolExecutor;

    @Override
    public AjaxResult messageSend(TmspPhoneSendDTO dto) {
        PostProcessorContainer postProcessorContainer = PostProcessorContainer.getInstance(SendMessagePostProcessor.class);
        PostProcessorContainer outPostProcessorContainer = PostProcessorContainer.getInstance(SendMessageOuterPostProcessor.class);

        SendInfo sendInfo = BeanUtil.copyProperties(dto, SendInfo.class);
        PostContext<SendInfo> context = new PostContext(sendInfo);


        // 1.发送前的操作（黑名单校验、数据库等）
        if (outPostProcessorContainer.handleBefore(context)) {
            // 没有进行发送前的操作，则直接返回空结果
            return AjaxResultUtil.getBussiseFalseAjaxResult(new AjaxResult<>(), NO_OUTHANDLER.getName(), NO_OUTHANDLER.getCode());
        }

        // 2.异步操作
        poolExecutor.submit(() -> {
            // 发送短信前的操作
            postProcessorContainer.handleBefore(context);
            // 实际发送动作已经包含在上下两步中
            // 发送短信后的操作
            postProcessorContainer.handleAfter(context);
        });

        // 3.发送后的操作（数据库）
        outPostProcessorContainer.handleAfter(context);

        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }
}
