package com.njxnet.service.tmsp.core3.pipeline.base.validate;

import com.njxnet.service.tmsp.common.BaseException;
import com.njxnet.service.tmsp.core3.ValveContext;
import com.njxnet.service.tmsp.core3.pipeline.base.BasePipeLine;
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
@Component("validatePipeLine")
public class ValidatePipeLine extends BasePipeLine<ValidateValve, ValidateValveContext> {


    @PostConstruct
    public void init(){
        super.init(ValidateValve.class);
    }

    @Override
    public void invoke(ValveContext context) {
        ValidateValveContext validateValveContext = new ValidateValveContext();
        SendInfo sendInfo = (SendInfo) context.getContextMap().get("sendInfo");

        if (sendInfo == null) {
            throw new BaseException(NO_SENDINFO.getCode(), NO_SENDINFO.getName());
        }

        validateValveContext.setContent(sendInfo.getContent());
        super.getFirstValve().invoke(validateValveContext);
        // 最后将结果设置到 sendInfo 中
        sendInfo.setValidatePass(validateValveContext.isViolence());
    }
}