package com.njxnet.service.tmsp.design.core3_pipeline.pipeline.base.notice.impl;

import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.base.notice.NoticeValve;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.base.notice.NoticeValveContext;
import org.springframework.stereotype.Component;

/**
 * @program: TMSP
 * @description: 将通知输出到控制台
 * @author: Stone
 * @create: 2023-07-12 21:30
 **/
@Component
public class SoutNoticeValve extends NoticeValve {
    @Override
    public void invoke(NoticeValveContext context) {

    }

    @Override
    public Integer getPriprity() {
        return null;
    }
}
