package com.njxnet.service.tmsp.design.core3_pipeline.pipeline.concrete.validate;

import com.njxnet.service.tmsp.design.core3_pipeline.ValveContext;
import com.njxnet.service.tmsp.model.info.SendInfo;
import lombok.Data;

/**
 * @program: TMSP
 * @description: 内容校验上下文
 * @author: Stone
 * @create: 2023-07-11 22:01
 **/
@Data
public class ValidateValveContext extends ValveContext<SendInfo> {

    private String content;

}
