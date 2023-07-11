package com.njxnet.service.tmsp.core3.pipeline.base.validate;

import com.njxnet.service.tmsp.core3.Valve;
import com.njxnet.service.tmsp.core3.ValveContext;
import com.njxnet.service.tmsp.core3.pipeline.base.BaseValve;

/**
 * @program: TMSP
 * @description: 内容校验基础类
 * @author: Stone
 * @create: 2023-07-11 22:02
 **/
public abstract class ValidateValve<T extends Valve, C extends ValveContext> extends BaseValve<T, C> {

}
