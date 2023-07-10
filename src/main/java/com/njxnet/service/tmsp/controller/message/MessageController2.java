package com.njxnet.service.tmsp.controller.message;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.model.dto.TmspPhoneSendDTO;
import com.njxnet.service.tmsp.service.MessageSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: TMSP
 * @description: 短信发送，使用模板方法模式
 * @author: Stone
 * @create: 2023-07-10 14:48
 **/
@RestController
@Api(value = "短信发送，使用模板方法模式", protocols = "http/https", tags = "短信发送2")
@ConditionalOnBean(name = "messageSendServiceImpl2")
@RequestMapping("/message2")
public class MessageController2 {

    @Resource(name = "groupMessageSendService")
    private MessageSendService groupMessageSendService;

    @Resource(name = "singleMessageSendService")
    private MessageSendService singleMessageSendService;


    @PostMapping("single/send")
    @ApiOperation(value = "单条短信发送", notes = "单条短信发送")
    public AjaxResult singleMessageSend(@RequestBody TmspPhoneSendDTO dto){
        if (singleMessageSendService!=null){
            return  singleMessageSendService.messageSend(dto);
        }
        return AjaxResultUtil.getFalseAjaxResult(new AjaxResult<>());
    }

    @PostMapping("group/send")
    @ApiOperation(value = "群发短信发送", notes = "群发短信发送")
    public AjaxResult groupMessageSend(@RequestBody TmspPhoneSendDTO dto) {
        return groupMessageSendService.messageSend(dto);
    }
}
