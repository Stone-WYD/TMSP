package com.njxnet.service.tmsp.core.send.impl;

import cn.hutool.core.bean.BeanUtil;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.common.ResultStatusCode;
import com.njxnet.service.tmsp.constants.SendEnum;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.send.SendMessagePostProcessor;
import com.njxnet.service.tmsp.model.dto.PhoneSendMsgDTO;
import com.njxnet.service.tmsp.model.info.SendInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class SendMessageCorePostProcessor implements SendMessagePostProcessor {

    @Resource
    private RestTemplate restTemplate;

    @Value("${config.interface.baseurl}")
    private String baseUrl;

    @Resource
    private WebClient webClient;

    @Value("${config.interface.sendurl}")
    private String sendurl;

    @Override
    public boolean handleBefore(PostContext<SendInfo> postContext) {
        // 参数准备
        SendInfo sendInfo = postContext.getT();
        // 准备参数给调用组件调用
        if (SendEnum.SINGLE.getType().equals(sendInfo.getSendWay())){
            PhoneSendMsgDTO dto = BeanUtil.copyProperties(sendInfo, PhoneSendMsgDTO.class);
            sendInfo.setPhoneSendMsgDTO(dto);
        }
        if (SendEnum.GROUP.getType().equals(sendInfo.getSendWay())){
            // 解析手机号封装接口传参
            List<String> mobileList = Arrays.asList(sendInfo.getMobiles().split(","));
            PhoneSendMsgDTO dto = BeanUtil.copyProperties(sendInfo, PhoneSendMsgDTO.class);
            // 封装传参
            List<PhoneSendMsgDTO> dtoList = new ArrayList<>(mobileList.size());
            for (String mobile : mobileList) {
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
        return false;
    }

    @Override
    public void handleAfter(PostContext<SendInfo> postContext) {
        // 调用接口
        SendMessagePostProcessor.super.handleAfter(postContext);
    }

    @Override
    public int getPriprity() {
        return Integer.MAX_VALUE;
    }
}
