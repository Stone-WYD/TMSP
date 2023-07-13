package com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.notice;

import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.design.core3_pipeline.ValveContext;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.base.BasePipeLine;
import com.njxnet.service.tmsp.model.info.SendInfo;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.njxnet.service.tmsp.common.ResultStatusCode.NO_SENDINFO;

/**
 * @program: TMSP
 * @description: 内容校验管道类
 * @author: Stone
 * @create: 2023-07-11 22:00
 **/
@Component("noticePipeLine")
public class NoticePipeLine extends BasePipeLine<NoticeValve, NoticeValveContext> {


    @PostConstruct
    public void init(){
        super.init(NoticeValve.class);
    }

    @Override
    public void invoke(ValveContext context) {
        NoticeValveContext validateValveContext = new NoticeValveContext();
        SendInfo sendInfo = (SendInfo) context.getContextMap().get("sendInfo");

        if (sendInfo == null) {
            throw new BaseException(NO_SENDINFO.getCode(), NO_SENDINFO.getName());
        }
        super.getFirstValve().invoke(validateValveContext);
    }
}
