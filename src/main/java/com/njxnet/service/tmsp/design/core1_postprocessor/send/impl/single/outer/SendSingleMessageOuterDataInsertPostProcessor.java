package com.njxnet.service.tmsp.design.core1_postprocessor.send.impl.single.outer;

import com.njxnet.service.tmsp.constant.MessageSendStatusEnum;
import com.njxnet.service.tmsp.constant.SendEnum;
import com.njxnet.service.tmsp.design.core1_postprocessor.PostContext;
import com.njxnet.service.tmsp.design.core1_postprocessor.send.SendMessageOuterPostProcessor;
import com.njxnet.service.tmsp.entity.MessagesSingleSend;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessagesSingleSendService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class SendSingleMessageOuterDataInsertPostProcessor implements SendMessageOuterPostProcessor {

    @Resource
    private MessagesSingleSendService messagesSingleSendService;

    @Override
    public boolean support(PostContext<SendInfo> postContext) {
        // 单发短信时才支持
        SendInfo sendInfo = postContext.getT();
        return SendEnum.SINGLE.getType().equals(sendInfo.getSendWay());
    }

    @Override
    public boolean handleBefore(PostContext<SendInfo> postContext) {
        // 插入一条记录
        SendInfo sendInfo = postContext.getT();
        MessagesSingleSend singleSend = createSingleSend(sendInfo, new MessagesSingleSend());
        messagesSingleSendService.save(singleSend);
        sendInfo.setSingleId(singleSend.getId());
        return false;
    }

    // 单发短信发送记录的创建
    private MessagesSingleSend createSingleSend(SendInfo message, MessagesSingleSend messagesSingleSend) {
        messagesSingleSend.setTitle(message.getTitle());
        messagesSingleSend.setUserName(message.getUserName());
        messagesSingleSend.setContent(message.getContent());
        messagesSingleSend.setCreateTime(new Date());
        messagesSingleSend.setPhoneNumber(message.getMobile());
        messagesSingleSend.setStatus(MessageSendStatusEnum.UNKNOW);
        return messagesSingleSend;
    }
}
