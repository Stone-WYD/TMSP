package com.njxnet.service.tmsp.core.send.impl.single;

import com.njxnet.service.tmsp.constants.MessageSendStatusEnum;
import com.njxnet.service.tmsp.constants.SendEnum;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.send.SendMessageOuterPostProcessor;
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
    public boolean handleBefore(PostContext<SendInfo> postContext) {
        // 插入一条记录
        SendInfo sendInfo = postContext.getT();
        if (SendEnum.SINGLE.getType().equals(sendInfo.getSendWay())) {
            MessagesSingleSend singleSend = createSingleSend(sendInfo, new MessagesSingleSend());
            messagesSingleSendService.save(singleSend);
            sendInfo.setSingleId(singleSend.getId());
        }
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
