package com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.notice;

import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.common.ResultStatusCode;
import com.njxnet.service.tmsp.design.core3_pipeline.ValveContext;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.base.BasePipeLine;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.validate.ValidateValveContext;
import com.njxnet.service.tmsp.model.info.SendInfo;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.njxnet.service.tmsp.common.ResultStatusCode.NO_SENDINFO;
import static com.njxnet.service.tmsp.common.ResultStatusCode.NO_VALVE_CONTEXT;

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
        NoticeValveContext noticeValveContext;
        if (context instanceof NoticeValveContext){
            noticeValveContext = ((NoticeValveContext) context);
        } else if (context instanceof ValidateValveContext){
            noticeValveContext = new NoticeValveContext();
            ValidateValveContext validateValveContext = (ValidateValveContext) context;
            noticeValveContext.setSendInfo(validateValveContext.getSendInfo());
        } else throw new BaseException(NO_VALVE_CONTEXT.getCode(), NO_VALVE_CONTEXT.getName());
        SendInfo sendInfo = noticeValveContext.getSendInfo();
        if (sendInfo == null) {
            throw new BaseException(NO_SENDINFO.getCode(), NO_SENDINFO.getName());
        }
        super.getFirstValve().invoke(noticeValveContext);
    }
}
