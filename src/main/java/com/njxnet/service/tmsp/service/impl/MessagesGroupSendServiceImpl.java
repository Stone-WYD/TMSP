package com.njxnet.service.tmsp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.service.tmsp.dao.MessagesGroupSendDao;
import com.njxnet.service.tmsp.entity.MessagesGroupSend;
import com.njxnet.service.tmsp.service.MessagesGroupSendService;
import org.springframework.stereotype.Service;

/**
 * 群发短信发送情况(MessagesGroupSend)表服务实现类
 *
 * @author Stone
 * @since 2023-07-07 13:55:54
 */
@Service("messagesGroupSendService")
public class MessagesGroupSendServiceImpl extends ServiceImpl<MessagesGroupSendDao, MessagesGroupSend> implements MessagesGroupSendService {

}

