package com.njxnet.service.tmsp.controller.message;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.model.dto.TmspPhoneSendDTO;
import com.njxnet.service.tmsp.service.MessageSendService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageSendService messageSendService;

    @Resource(name = "groupMessageSendService")
    private MessageSendService groupMessageSendService;

    @Resource(name = "singleMessageSendService")
    private MessageSendService singleMessageSendService;

    @PostMapping("/send")
    @ApiOperation(value = "短信发送", notes = "短信发送接口")
    public AjaxResult messageSend(@RequestBody TmspPhoneSendDTO dto){
        return  messageSendService.messageSend(dto);
    }

    @PostMapping("single/send")
    @ApiOperation(value = "单条短信发送", notes = "短信发送接口")
    public AjaxResult singleMessageSend(@RequestBody TmspPhoneSendDTO dto){
        if (singleMessageSendService!=null){
            return  singleMessageSendService.messageSend(dto);
        }
        return AjaxResultUtil.getFalseAjaxResult(new AjaxResult<>());
    }

    @PostMapping("group/send")
    @ApiOperation(value = "短信发送", notes = "短信发送接口")
    public AjaxResult groupMessageSend(@RequestBody TmspPhoneSendDTO dto){
        if (groupMessageSendService!=null){
            return  groupMessageSendService.messageSend(dto);
        }
        return AjaxResultUtil.getFalseAjaxResult(new AjaxResult<>());
    }

}
