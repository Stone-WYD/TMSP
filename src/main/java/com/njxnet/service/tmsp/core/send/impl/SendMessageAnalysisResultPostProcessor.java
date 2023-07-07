package com.njxnet.service.tmsp.core.send.impl;

import cn.hutool.core.util.StrUtil;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.ResultStatusCode;
import com.njxnet.service.tmsp.constants.MessageSendStatusEnum;
import com.njxnet.service.tmsp.constants.SendEnum;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.send.SendMessagePostProcessor;
import com.njxnet.service.tmsp.entity.MessagesGroupSend;
import com.njxnet.service.tmsp.entity.MessagesSingleSend;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessagesGroupSendService;
import com.njxnet.service.tmsp.service.MessagesSingleSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

import static com.njxnet.service.tmsp.common.ResultStatusCode.SUCCESS;

@Component
@Slf4j
public class SendMessageAnalysisResultPostProcessor implements SendMessagePostProcessor {


    @Resource
    private MessagesSingleSendService singleSendService;

    @Resource
    private MessagesGroupSendService groupSendService;



    @Override
    public void handleAfter(PostContext<SendInfo> postContext) {
        // 分析结果
        SendInfo sendInfo = postContext.getT();
        if (SendEnum.SINGLE.getType().equals(sendInfo.getSendWay())){
            // 更新单条发送记录
            MessagesSingleSend updateStatus = new MessagesSingleSend();
            updateStatus.setId(sendInfo.getSingleId());
            // 解析结果
            AjaxResult ajaxResult = sendInfo.getResult();
            if (ajaxResult.getCode() == SUCCESS.getCode()){
                updateStatus.setStatus(MessageSendStatusEnum.SUCCESS);
            } else updateStatus.setStatus(MessageSendStatusEnum.FAIL);
            log.info("调用接口平台短信接口返回结果message:{}, code:{}, data:{}",
                    ajaxResult.getMessage(), ajaxResult.getCode(), ajaxResult.getData());
            // 2.3.更新数据库记录
            updateStatus.setFinishTime(new Date());
            singleSendService.updateById(updateStatus);
        }
        if (SendEnum.GROUP.getType().equals(sendInfo.getSendWay())){
            Map<String, AjaxResult> resultMap = sendInfo.getResultMap();
            // 解析结果
            MessagesGroupSend resultRecord = analysisResultEntity(sendInfo.getMobileList(), resultMap);
            resultRecord.setId(sendInfo.getGroupId());
            resultRecord.setFinishTime(new Date());
            // 3.3.更新记录表
            groupSendService.updateById(resultRecord);
        }
    }

    private MessagesGroupSend analysisResultEntity(List<String> mobileList, Map<String, AjaxResult> resultMap) {
        MessagesGroupSend messagesGroupSend = new MessagesGroupSend();
        // 发送成功的手机号
        List<String> successList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        for (String mobile : mobileList) {
            AjaxResult ajaxResult = resultMap.get(mobile);
            if (ResultStatusCode.SUCCESS.getCode() == ajaxResult.getCode()){
                successList.add(mobile);
            } else failList.add(mobile);
            log.info("调用接口平台短信接口返回结果message:{}, code:{}, data:{}",
                    ajaxResult.getMessage(), ajaxResult.getCode(), ajaxResult.getData());
        }
        String failPhoneNumbers = StrUtil.join(",", failList);
        messagesGroupSend.setFailPhoneNumbers(failPhoneNumbers);
        messagesGroupSend.setSendCount(successList.size());
        messagesGroupSend.setUnknownCount(mobileList.size() - failList.size() - successList.size());
        return messagesGroupSend;
    }

}
