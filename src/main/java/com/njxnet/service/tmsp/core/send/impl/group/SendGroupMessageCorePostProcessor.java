package com.njxnet.service.tmsp.core.send.impl.group;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.common.ResultStatusCode;
import com.njxnet.service.tmsp.constants.SendEnum;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.send.SendMessagePostProcessor;
import com.njxnet.service.tmsp.core.send.SendMessageWay;
import com.njxnet.service.tmsp.model.dto.PhoneSendMsgDTO;
import com.njxnet.service.tmsp.model.info.SendInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.njxnet.service.tmsp.common.ResultStatusCode.SEND_DTO_EMPTY_ERROR;

@Slf4j
@Component
public class SendGroupMessageCorePostProcessor implements SendMessagePostProcessor {

    @Resource
    private SendMessageWay sendMessageWay;

    @Override
    public boolean support(PostContext<SendInfo> postContext) {
        SendInfo sendInfo = postContext.getT();
        return SendEnum.GROUP.getType().equals(sendInfo.getSendWay());
    }

    @Override
    public boolean handleBefore(PostContext<SendInfo> postContext) {
        // 参数准备
        SendInfo sendInfo = postContext.getT();
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
        return false;
    }

    @Override
    public void handleAfter(PostContext<SendInfo> postContext) {
        SendInfo sendInfo = postContext.getT();
        List<PhoneSendMsgDTO> phoneSendMsgDTOList = sendInfo.getPhoneSendMsgDTOList();
        // 调用接口
        if (CollectionUtil.isEmpty(phoneSendMsgDTOList)) {
            throw new BaseException(SEND_DTO_EMPTY_ERROR.getCode(), SEND_DTO_EMPTY_ERROR.getName());
        }
        sendMessageWay.sendMessage(sendInfo, phoneSendMsgDTOList);
    }



    @Override
    public int getPriprity() {
        return Integer.MAX_VALUE;
    }
}
