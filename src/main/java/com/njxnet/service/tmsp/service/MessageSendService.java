package com.njxnet.service.tmsp.service;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.model.dto.TmspPhoneSendDTO;

public interface MessageSendService {


    AjaxResult messageSend(TmspPhoneSendDTO dto);
}
