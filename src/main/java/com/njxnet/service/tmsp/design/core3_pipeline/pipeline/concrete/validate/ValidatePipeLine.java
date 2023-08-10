package com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.validate;

import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.design.core3_pipeline.ValveContext;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.base.BasePipeLine;
import com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.notice.NoticeValveContext;
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
@Component("validatePipeLine")
public class ValidatePipeLine extends BasePipeLine<ValidateValve, ValidateValveContext> {


    @PostConstruct
    public void init(){
        super.init(ValidateValve.class);
    }

    @Override
    public void invoke(ValveContext context) {
        ValidateValveContext validateValveContext =
                context instanceof ValidateValveContext ? ((ValidateValveContext) context) : null;
        if (validateValveContext == null ) throw new BaseException(NO_VALVE_CONTEXT.getCode(), NO_VALVE_CONTEXT.getName());
        SendInfo sendInfo = validateValveContext.getSendInfo();
        if (sendInfo == null) {
            throw new BaseException(NO_SENDINFO.getCode(), NO_SENDINFO.getName());
        }
        super.getFirstValve().invoke(validateValveContext);
    }
}
