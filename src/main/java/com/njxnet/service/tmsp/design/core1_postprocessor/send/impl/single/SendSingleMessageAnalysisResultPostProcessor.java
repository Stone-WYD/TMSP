package com.njxnet.service.tmsp.design.core1_postprocessor.send.impl.single;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.constant.MessageSendStatusEnum;
import com.njxnet.service.tmsp.constant.SendEnum;
import com.njxnet.service.tmsp.design.core1_postprocessor.PostContext;
import com.njxnet.service.tmsp.design.core1_postprocessor.send.SendMessagePostProcessor;
import com.njxnet.service.tmsp.entity.MessagesSingleSend;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessagesSingleSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

import static com.njxnet.service.tmsp.common.ResultStatusCode.SUCCESS;

@Component
@Slf4j
public class SendSingleMessageAnalysisResultPostProcessor implements SendMessagePostProcessor {


    @Resource
    private MessagesSingleSendService singleSendService;

    @Override
    public boolean support(PostContext<SendInfo> postContext) {
        SendInfo sendInfo = postContext.getT();
        return SendEnum.SINGLE.getType().equals(sendInfo.getSendWay());
    }

    @Override
    public void handleAfter(PostContext<SendInfo> postContext) {
        // 分析结果
        SendInfo sendInfo = postContext.getT();
        // 更新单条发送记录
        MessagesSingleSend updateStatus = new MessagesSingleSend();
        updateStatus.setId(sendInfo.getSingleId());
        // 解析结果
        String mobile = sendInfo.getMobile();
        AjaxResult ajaxResult = sendInfo.getResultMap().get(mobile);
        if (ajaxResult.getCode() == SUCCESS.getCode()) {
            updateStatus.setStatus(MessageSendStatusEnum.SUCCESS);
        } else updateStatus.setStatus(MessageSendStatusEnum.FAIL);
        log.info("调用接口平台短信接口返回结果message:{}, code:{}, data:{}",
                ajaxResult.getMessage(), ajaxResult.getCode(), ajaxResult.getData());
        // 2.3.更新数据库记录
        updateStatus.setFinishTime(new Date());
        singleSendService.updateById(updateStatus);
    }

}
