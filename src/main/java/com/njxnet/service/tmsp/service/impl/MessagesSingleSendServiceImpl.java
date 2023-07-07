package com.njxnet.service.tmsp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.service.tmsp.dao.MessagesSingleSendDao;
import com.njxnet.service.tmsp.entity.MessagesSingleSend;
import com.njxnet.service.tmsp.service.MessagesSingleSendService;
import org.springframework.stereotype.Service;

/**
 * (MessagesSingleSend)表服务实现类
 *
 * @author Stone
 * @since 2023-07-07 13:56:18
 */
@Service("messagesSingleSendService")
public class MessagesSingleSendServiceImpl extends ServiceImpl<MessagesSingleSendDao, MessagesSingleSend> implements MessagesSingleSendService {

}

