package com.njxnet.service.tmsp.core3.pipeline;

import com.njxnet.service.tmsp.core3.Valve;
import com.njxnet.service.tmsp.core3.ValveContext;
import com.njxnet.service.tmsp.core3.base.BasePipeLine;

/**
 * @program: TMSP
 * @description: 内容校验管道类
 * @author: Stone
 * @create: 2023-07-11 22:00
 **/
public abstract class ValidatePipeLine<T extends Valve, C extends ValveContext> extends BasePipeLine<T, C> {

}
