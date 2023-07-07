package com.njxnet.service.tmsp.core.send;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.njxnet.service.tmsp.constants.SendClient.REST_TEMPLATE;
import static com.njxnet.service.tmsp.constants.SendClient.WEB_CLIENT;

@Component
@Slf4j
public class SendMessageWay {


    @Resource
    private RestTemplate restTemplate;

    @Value("${config.url.base}")
    private String baseUrl;

    @Resource
    private WebClient webClient;

    @Value("${config.url.send}")
    private String sendurl;

    @Value("${config.send.type}")
    private String sendType;

    public void sendMessage(SendInfo sendInfo, List<PhoneSendMsgDTO> phoneSendMsgDTOList) {
        // 选择不同的方式调用接口
        Map<String, AjaxResult> resultMap = new ConcurrentHashMap<>();
        sendInfo.setResultMap(resultMap);
        Map<String, Mono<AjaxResult>> monoResultMap = new ConcurrentHashMap<>(phoneSendMsgDTOList.size());
        if (WEB_CLIENT.getName().equals(sendType)){
            // 调用接口 此处可异步，for循环执行完再获取结果
            for (PhoneSendMsgDTO phoneSendMsgDTO : phoneSendMsgDTOList) {
                try {
                    Mono<AjaxResult> resultMono = webClient.post().uri(sendurl).bodyValue(phoneSendMsgDTO).retrieve().bodyToMono(AjaxResult.class);
                    monoResultMap.put(phoneSendMsgDTO.getMobile(), resultMono);
                } catch (Exception e){
                    log.error(e.getMessage());
                    // 调用失败，返回一个失败的ajaxResult
                    resultMap.put(phoneSendMsgDTO.getMobile(), AjaxResultUtil.getFalseAjaxResult(new AjaxResult<>()));
                }
            }
            for (String mobile : monoResultMap.keySet()) {
                resultMap.put(mobile, monoResultMap.get(mobile).block());
            }
            sendInfo.setResultMap(resultMap);
        } else if (REST_TEMPLATE.getName().equals(sendType)){
            // 调用接口
            for (PhoneSendMsgDTO phoneSendMsgDTO : phoneSendMsgDTOList) {
                try {
                    ResponseEntity<AjaxResult> resultEntity = restTemplate.postForEntity(baseUrl + sendurl, phoneSendMsgDTO, AjaxResult.class);
                    AjaxResult result = resultEntity.getBody();
                    resultMap.put(phoneSendMsgDTO.getMobile(), result);
                } catch (Exception e){
                    log.error(e.getMessage());
                    // 调用失败，返回一个失败的ajaxResult
                    resultMap.put(phoneSendMsgDTO.getMobile(), AjaxResultUtil.getFalseAjaxResult(new AjaxResult<>()));
                }
            }
        }
    }
}
