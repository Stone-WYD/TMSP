package com.njxnet.service.tmsp.controller.message;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.model.dto.TmspPhoneSendDTO;
import com.njxnet.service.tmsp.service.MessageSendService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageSendService messageSendService;

    @PostMapping("/send")
    @ApiOperation(value = "短信发送", notes = "短信发送接口")
    public AjaxResult messageSend(@RequestBody TmspPhoneSendDTO dto){
        return  messageSendService.messageSend(dto);
    }

}
