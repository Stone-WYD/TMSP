package com.njxnet.service.tmsp.core2.send;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.common.ResultStatusCode;
import com.njxnet.service.tmsp.core.send.SendMessageWay;
import com.njxnet.service.tmsp.entity.MessagesGroupSend;
import com.njxnet.service.tmsp.model.dto.PhoneSendMsgDTO;
import com.njxnet.service.tmsp.model.info.SendInfo;
import com.njxnet.service.tmsp.service.MessagesGroupSendService;
import com.njxnet.service.tmsp.service.impl.MessageSendServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.njxnet.service.tmsp.common.ResultStatusCode.SEND_DTO_EMPTY_ERROR;

/**
 * @program: TMSP
 * @description: 模板方法实现短信发送，群发短信发送
 * @author: Stone
 * @create: 2023-07-10 13:50
 **/
@Slf4j
@Service(value = "groupMessageSendService")
@ConditionalOnBean(name = "messageSendServiceImpl2")
public class MessageSendServiceImpl2ForGroup extends MessageSendServiceImpl2 {

    @Resource
    private MessagesGroupSendService groupSendService;

    @Resource
    private SendMessageWay sendMessageWay;

    /**
    * @Description: 新增发送记录
    * @Author: Stone
    * @Date: 2023/7/10
    */
    protected void dataRecordSave() {
        SendInfo sendInfo = context.getT();
        MessagesGroupSend groupSend = createGroupSend(sendInfo, new MessagesGroupSend());
        groupSendService.save(groupSend);
        sendInfo.setGroupId(groupSend.getId());
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

    /**
    * @Description: 短信发送前对传参进行处理
    * @Author: Stone
    * @Date: 2023/7/10
    */
    protected void paramPostProcessor() {
        // 参数准备
        SendInfo sendInfo = context.getT();
        // 准备参数给调用组件调用
        // 解析手机号封装接口传参
        List<String> mobileList = sendInfo.getMobileList();
        PhoneSendMsgDTO dto = BeanUtil.copyProperties(sendInfo, PhoneSendMsgDTO.class);
        // 封装传参
        List<PhoneSendMsgDTO> dtoList = new ArrayList<>(mobileList.size());
        for (String mobile : mobileList) {
            // 克隆出只有手机号不同的传参
            PhoneSendMsgDTO clone;
            try {
                clone = (PhoneSendMsgDTO) dto.clone();
            } catch (CloneNotSupportedException e) {
                log.info(e.getMessage());
                throw new BaseException(ResultStatusCode.FAIL.getCode(), ResultStatusCode.FAIL.getName());
            }
            clone.setMobile(mobile);
            dtoList.add(clone);
        }
        sendInfo.setPhoneSendMsgDTOList(dtoList);
    }

    /**
    * @Description: 发送短信
    * @Author: Stone
    * @Date: 2023/7/10
    */
    protected void theMessageSend() {
        SendInfo sendInfo = context.getT();
        List<PhoneSendMsgDTO> phoneSendMsgDTOList = sendInfo.getPhoneSendMsgDTOList();
        // 调用接口
        if (CollectionUtil.isEmpty(phoneSendMsgDTOList)) {
            throw new BaseException(SEND_DTO_EMPTY_ERROR.getCode(), SEND_DTO_EMPTY_ERROR.getName());
        }
        sendMessageWay.sendMessage(sendInfo, phoneSendMsgDTOList);
    }

    /**
    * @Description: 结果分析，更新发送记录
    * @Author: Stone
    * @Date: 2023/7/10
    */
    protected void messageAnalysis() {
        // 分析结果
        SendInfo sendInfo = context.getT();
        Map<String, AjaxResult> resultMap = sendInfo.getResultMap();
        // 解析结果
        MessagesGroupSend resultRecord = analysisResultEntity(sendInfo.getMobileList(), resultMap);
        resultRecord.setId(sendInfo.getGroupId());
        resultRecord.setFinishTime(new Date());
        // 3.3.更新记录表
        groupSendService.updateById(resultRecord);
    }

    /**
    * @Description: 解析结果，内部方法
    * @Author: Stone
    * @Date: 2023/7/10
    */
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
