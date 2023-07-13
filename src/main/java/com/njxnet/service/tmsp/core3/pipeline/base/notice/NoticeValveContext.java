package com.njxnet.service.tmsp.core3.pipeline.base.notice;

import com.njxnet.service.tmsp.core3.ValveContext;
import com.njxnet.service.tmsp.model.info.SendInfo;
import lombok.Data;

/**
 * @program: TMSP
 * @description: 内容校验上下文
 * @author: Stone
 * @create: 2023-07-11 22:01
 **/
@Data
public class NoticeValveContext extends ValveContext<SendInfo> {

    private String content;

    private boolean isViolence;

    private boolean isNotice;
}
