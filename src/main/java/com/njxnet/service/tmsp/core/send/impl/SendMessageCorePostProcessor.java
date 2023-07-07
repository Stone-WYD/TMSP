package com.njxnet.service.tmsp.core.send.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.common.ResultStatusCode;
import com.njxnet.service.tmsp.constants.SendEnum;
import com.njxnet.service.tmsp.core.PostContext;
import com.njxnet.service.tmsp.core.send.SendMessagePostProcessor;
import com.njxnet.service.tmsp.model.dto.PhoneSendMsgDTO;
import com.njxnet.service.tmsp.model.info.SendInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.njxnet.service.tmsp.common.ResultStatusCode.SEND_DTO_EMPTY_ERROR;
import static com.njxnet.service.tmsp.constants.SendClient.REST_TEMPLATE;
import static com.njxnet.service.tmsp.constants.SendClient.WEB_CLIENT;

@Slf4j
@Component
public class SendMessageCorePostProcessor implements SendMessagePostProcessor {

    @Resource
    private RestTemplate restTemplate;

    @Value("${config.interface.baseurl}")
    private String baseUrl;

    @Resource
    private WebClient webClient;

    @Value("${config.url.send}")
    private String sendurl;

    @Value("${config.send.type}")
    private String sendType;

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
        SendInfo sendInfo = postContext.getT();
        List<PhoneSendMsgDTO> phoneSendMsgDTOList = new ArrayList<>();
        // 调用接口
        if (SendEnum.SINGLE.getType().equals(sendInfo.getSendWay())){
            phoneSendMsgDTOList.add(sendInfo.getPhoneSendMsgDTO());
        } else if (SendEnum.GROUP.getType().equals(sendInfo.getSendWay())){
            phoneSendMsgDTOList = sendInfo.getPhoneSendMsgDTOList();
        }
        if (CollectionUtil.isEmpty(phoneSendMsgDTOList)) {
            throw new BaseException(SEND_DTO_EMPTY_ERROR.getCode(), SEND_DTO_EMPTY_ERROR.getName());
        }
        // 选择不同的方式调用接口
        Map<String, AjaxResult> resultMap = new ConcurrentHashMap<>();
        Map<String, Mono<AjaxResult>> monoResultMap = new ConcurrentHashMap<>(phoneSendMsgDTOList.size());
        if (WEB_CLIENT.getName().equals(sendType)){
            // 调用接口 此处可异步，for循环执行完再获取结果
            for (PhoneSendMsgDTO phoneSendMsgDTO : phoneSendMsgDTOList) {
                Mono<AjaxResult> resultMono = webClient.post().uri(sendurl).bodyValue(phoneSendMsgDTO).retrieve().bodyToMono(AjaxResult.class);
                monoResultMap.put(phoneSendMsgDTO.getMobile(), resultMono);
            }
            for (String mobile : monoResultMap.keySet()) {
                resultMap.put(mobile, monoResultMap.get(mobile).block());
            }
            sendInfo.setResultMap(resultMap);
        } else if (REST_TEMPLATE.getName().equals(sendType)){
            // 调用接口
            for (PhoneSendMsgDTO phoneSendMsgDTO : phoneSendMsgDTOList) {
                ResponseEntity<AjaxResult> resultEntity = restTemplate.postForEntity(baseUrl + sendurl, phoneSendMsgDTO, AjaxResult.class);
                AjaxResult result = resultEntity.getBody();
                resultMap.put(phoneSendMsgDTO.getMobile(), result);
            }
        }
    }

    @Override
    public int getPriprity() {
        return Integer.MAX_VALUE;
    }
}
