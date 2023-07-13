package com.njxnet.service.tmsp.design.core1_postprocessor.send;

import com.njxnet.service.tmsp.model.dto.PhoneSendMsgDTO;
import com.njxnet.service.tmsp.model.info.SendInfo;

import java.util.List;

/**
 * @program: TMSP
 * @description: 短信发送方式
 * @author: Stone
 * @create: 2023-07-08 20:38
 **/
public interface SendMessageWay {

    void sendMessage(SendInfo sendInfo, List<PhoneSendMsgDTO> phoneSendMsgDTOList);
}
