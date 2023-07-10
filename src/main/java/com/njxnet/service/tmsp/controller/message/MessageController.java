package com.njxnet.service.tmsp.controller.message;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.model.dto.TmspPhoneSendDTO;
import com.njxnet.service.tmsp.service.MessageSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(value = "短信发送", protocols = "http/https", tags = "短信发送")
@RequestMapping("/message")
public class MessageController {

    @Resource
    private MessageSendService messageSendService;


    @PostMapping("/send")
    @ApiOperation(value = "短信发送", notes = "短信发送接口")
    public AjaxResult messageSend(@RequestBody TmspPhoneSendDTO dto){
        return  messageSendService.messageSend(dto);
    }

}
