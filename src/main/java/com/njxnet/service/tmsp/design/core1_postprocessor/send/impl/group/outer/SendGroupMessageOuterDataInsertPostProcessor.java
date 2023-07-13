package com.njxnet.service.tmsp.design.core1_postprocessor.send.impl.group.outer;

import com.njxnet.service.tmsp.constants.SendEnum;
import com.njxnet.service.tmsp.design.core1_postprocessor.PostContext;
import com.njxnet.service.tmsp.design.core1_postprocessor.send.SendMessageOuterPostProcessor;
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

    /**
    * @Description: 群发短信时才支持
    * @Author: Stone
    * @Date: 2023/7/8
    */
    @Override
    public boolean support(PostContext<SendInfo> postContext) {
        SendInfo sendInfo = postContext.getT();
        return SendEnum.GROUP.getType().equals(sendInfo.getSendWay());
    }

    /**
    * @Description: 插入一条记录
    * @Author: Stone
    * @Date: 2023/7/8
    */
    @Override
    public boolean handleBefore(PostContext<SendInfo> postContext) {
        SendInfo sendInfo = postContext.getT();
        MessagesGroupSend groupSend = createGroupSend(sendInfo, new MessagesGroupSend());
        groupSendService.save(groupSend);
        sendInfo.setGroupId(groupSend.getId());
        return false;
    }

    /**
    * @Description: 群发短信发送记录的创建
    * @Author: Stone
    * @Date: 2023/7/8
    */
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
