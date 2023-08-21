package com.njxnet.service.tmsp.design.core1_postprocessor.send.impl.single;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.constant.SendEnum;
import com.njxnet.service.tmsp.design.core1_postprocessor.PostContext;
import com.njxnet.service.tmsp.design.core1_postprocessor.send.SendMessagePostProcessor;
import com.njxnet.service.tmsp.design.core1_postprocessor.send.SendMessageWay;
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
public class SendSingleMessageCorePostProcessor implements SendMessagePostProcessor {

    @Resource
    private SendMessageWay sendMessageWay;

    @Override
    public boolean support(PostContext<SendInfo> postContext) {
        SendInfo sendInfo = postContext.getT();
        return SendEnum.SINGLE.getType().equals(sendInfo.getSendWay());
    }

    @Override
    public boolean handleBefore(PostContext<SendInfo> postContext) {
        // 参数准备
        SendInfo sendInfo = postContext.getT();
        // 准备参数给调用组件调用
        PhoneSendMsgDTO dto = BeanUtil.copyProperties(sendInfo, PhoneSendMsgDTO.class);
        List<PhoneSendMsgDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
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
