package com.njxnet.service.tmsp.core2.send;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.constants.MessageSendStatusEnum;
import com.njxnet.service.tmsp.core.send.SendMessageWay;
import com.njxnet.service.tmsp.entity.MessagesSingleSend;
import com.njxnet.service.tmsp.model.dto.PhoneSendMsgDTO;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessagesSingleSendService;
import com.njxnet.service.tmsp.service.impl.MessageSendServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.njxnet.service.tmsp.common.ResultStatusCode.SEND_DTO_EMPTY_ERROR;
import static com.njxnet.service.tmsp.common.ResultStatusCode.SUCCESS;

/**
 * @program: TMSP
 * @description: 模板方法模式实现短信发送，单条短信发送
 * @author: Stone
 * @create: 2023-07-10 11:37
 **/
@Slf4j
@Service(value = "singleMessageSendService")
@ConditionalOnBean(value = MessageSendServiceImpl2.class)
public class MessageSendServiceImpl2ForSingle extends MessageSendServiceImpl2 {

    @Resource
    private MessagesSingleSendService messagesSingleSendService;

    @Resource
    private SendMessageWay sendMessageWay;

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
    * @Description: 参数准备
    * @Author: Stone
    * @Date: 2023/7/10
    */
    protected void paramPostProcessor() {
        SendInfo sendInfo = context.getT();
        // 准备参数给调用组件调用
        PhoneSendMsgDTO dto = BeanUtil.copyProperties(sendInfo, PhoneSendMsgDTO.class);
        List<PhoneSendMsgDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        sendInfo.setPhoneSendMsgDTOList(dtoList);
    }

    /**
    * @Description: 发送短信
    * @Author: Stone
    * @Date: 2023/7/10
    */
    protected void theMessageSend() {
        SendInfo sendInfo =  context.getT();
        List<PhoneSendMsgDTO> phoneSendMsgDTOList = sendInfo.getPhoneSendMsgDTOList();
        // 调用接口
        if (CollectionUtil.isEmpty(phoneSendMsgDTOList)) {
            throw new BaseException(SEND_DTO_EMPTY_ERROR.getCode(), SEND_DTO_EMPTY_ERROR.getName());
        }
        sendMessageWay.sendMessage(sendInfo, phoneSendMsgDTOList);
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
