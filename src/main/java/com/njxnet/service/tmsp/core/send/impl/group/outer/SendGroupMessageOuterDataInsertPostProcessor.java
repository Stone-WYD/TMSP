package com.njxnet.service.tmsp.core.send.impl.group.outer;

import com.njxnet.service.tmsp.constants.SendEnum;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.send.SendMessageOuterPostProcessor;
import com.njxnet.service.tmsp.entity.MessagesGroupSend;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessagesGroupSendService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class SendGroupMessageOuterDataInsertPostProcessor implements SendMessageOuterPostProcessor {

    @Resource
    private MessagesGroupSendService groupSendService;

    @Override
    public boolean support(PostContext<SendInfo> postContext) {
        // 群发短信时才支持
        SendInfo sendInfo = postContext.getT();
        return SendEnum.GROUP.getType().equals(sendInfo.getSendWay());
    }

    @Override
    public boolean handleBefore(PostContext<SendInfo> postContext) {
        // 插入一条记录
        SendInfo sendInfo = postContext.getT();
        MessagesGroupSend groupSend = createGroupSend(sendInfo, new MessagesGroupSend());
        groupSendService.save(groupSend);
        sendInfo.setGroupId(groupSend.getId());
        return false;
    }

    // 群发短信发送记录的创建
    private MessagesGroupSend createGroupSend(SendInfo message, MessagesGroupSend messagesGroupSend) {
        messagesGroupSend.setTitle(message.getTitle());
        messagesGroupSend.setUserName(message.getUserName());
        messagesGroupSend.setContent(message.getContent());
        messagesGroupSend.setCreateDate(new Date());
        messagesGroupSend.setPhoneNumbers(message.getMobiles());

        int count = message.getMobiles().split(",").length;
        messagesGroupSend.setUnknownCount(count);
        messagesGroupSend.setPhonesCount(count);
        return messagesGroupSend;
    }
}
