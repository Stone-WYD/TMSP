package com.njxnet.service.tmsp.core.send.impl.single;

import com.njxnet.service.tmsp.constants.SendEnum;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.send.SendMessageOuterPostProcessor;
import com.njxnet.service.tmsp.entity.MessagesSingleSend;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessagesSingleSendService;
import com.njxnet.service.tmsp.utils.CommonUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SendMessageOuterDataInsertPostProcessor implements SendMessageOuterPostProcessor {

    @Resource
    private CommonUtil commonUtil;

    @Resource
    private MessagesSingleSendService messagesSingleSendService;

    @Override
    public boolean handleBefore(PostContext<SendInfo> postContext) {
        // 插入一条记录
        SendInfo sendInfo = postContext.getT();
        if (SendEnum.SINGLE.getType().equals(sendInfo.getSendWay())) {
            MessagesSingleSend singleSend = commonUtil.createSingleSend(sendInfo, new MessagesSingleSend());
            messagesSingleSendService.save(singleSend);
            sendInfo.setSingleId(singleSend.getId());
        }
        return false;
    }
}
