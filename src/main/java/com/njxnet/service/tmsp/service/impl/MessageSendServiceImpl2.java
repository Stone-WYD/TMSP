package com.njxnet.service.tmsp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.PostProcessorContainer;
import com.njxnet.service.tmsp.core2.send.SendMessageOuterPostProcessor2;
import com.njxnet.service.tmsp.core2.send.SendMessagePostProcessor2;
import com.njxnet.service.tmsp.model.dto.TmspPhoneSendDTO;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessageSendService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

import static com.njxnet.service.tmsp.common.ResultStatusCode.NO_OUTHANDLER;

/**
 * @program: TMSP
 * @description: 使用模板方法实现发送短信需求
 * @author: Stone
 * @create: 2023-07-10 10:44
 **/
@Service("messageSendServiceImpl2")
@ConditionalOnProperty(name = "config.core.version", havingValue = "2")
public class MessageSendServiceImpl2 implements MessageSendService {

    @Resource
    private ThreadPoolExecutor poolExecutor;

    protected PostContext<SendInfo> context;

    @Override
    /**
    * @Description: 模板方法，具体实现在子类中
    * @Author: Stone
    * @Date: 2023/7/10
    */
    public AjaxResult messageSend(TmspPhoneSendDTO dto) {
        PostProcessorContainer outPostProcessorContainer = PostProcessorContainer.getInstance(SendMessageOuterPostProcessor2.class);
        PostProcessorContainer postProcessorContainer = PostProcessorContainer.getInstance(SendMessagePostProcessor2.class);
        SendInfo sendInfo = BeanUtil.copyProperties(dto, SendInfo.class);
        context = new PostContext(sendInfo);

        // 1.发送前的操作（黑名单校验等）
        if (outPostProcessorContainer.handleBefore(context)) {
            // 没有进行发送前的操作，则直接返回空结果
            return AjaxResultUtil.getBussiseFalseAjaxResult(new AjaxResult<>(), NO_OUTHANDLER.getName(), NO_OUTHANDLER.getCode());
        }
        // 2. 保存发送记录到数据库
        dataRecordSave();
        // 3.异步操作
        poolExecutor.submit(() -> {
            // 3.1.发送短信前的操作
            postProcessorContainer.handleBefore(context);
            // 3.2.参数准备
            paramPostProcessor();
            // 3.4.发送短信
            theMessageSend();
            // 3.5.解析结果，更新记录
            messageAnalysis();
            // 3.6.发送短信后的操作
            postProcessorContainer.handleAfter(context);
        });

        // 4.发送后的操作（数据库）
        outPostProcessorContainer.handleAfter(context);

        // 5.返回结果
        return AjaxResultUtil.getTrueAjaxResult(new AjaxResult<>());
    }

    protected void dataRecordSave() {

    }

    protected void paramPostProcessor() {

    }

    protected void theMessageSend() {

    }

    protected void messageAnalysis() {

    }
}
