package com.njxnet.service.tmsp.core2.send;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.constants.MessageSendStatusEnum;
import com.njxnet.service.tmsp.entity.MessagesSingleSend;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessagesSingleSendService;
import com.njxnet.service.tmsp.service.impl.MessageSendServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

import static com.njxnet.service.tmsp.common.ResultStatusCode.SUCCESS;

/**
 * @program: TMSP
 * @description: 模板方法模式实现短信发送，单条短信发送
 * @author: Stone
 * @create: 2023-07-10 11:37
 **/
@Slf4j
@Service(value = "singleMessageSendService")
@ConditionalOnBean(name = "messageSendServiceImpl2")
public class MessageSendServiceImpl2ForSingle extends MessageSendServiceImpl2 {

    @Resource
    private MessagesSingleSendService messagesSingleSendService;

    /**
    * @Description: 插入一条记录
    * @Author: Stone
    * @Date: 2023/7/10
    */
    protected void dataRecordSave() {
        SendInfo sendInfo =  context.getT();
        MessagesSingleSend singleSend = createSingleSend(sendInfo, new MessagesSingleSend());
        messagesSingleSendService.save(singleSend);
        sendInfo.setSingleId(singleSend.getId());
    }


    /**
    * @Description: 分析结果，更新记录
    * @Author: Stone
    * @Date: 2023/7/10
    */
    protected void messageAnalysis() {
        SendInfo sendInfo =  context.getT();
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
        messagesSingleSendService.updateById(updateStatus);
    }

    /**
     * @Description: 单发短信发送记录的创建
     * @Author: Stone
     * @Date: 2023/7/10
     */
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
