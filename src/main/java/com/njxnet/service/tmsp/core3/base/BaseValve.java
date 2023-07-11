package com.njxnet.service.tmsp.core3.base;

import com.njxnet.service.tmsp.core3.Valve;
import com.njxnet.service.tmsp.core3.ValveContext;

/**
 * @program: TMSP
 * @description: 基础阀类，抽象类，为了让子类实现该有的接口而存在
 * @author: Stone
 * @create: 2023-07-11 21:51
 **/
public abstract class BaseValve<T extends Valve, C extends ValveContext> implements Valve {

    private T nextValve;

    @Override
    public void setNext(Valve valve) {
        nextValve = (T) valve;
    }

    @Override
    public Valve getNext() {
        return nextValve;
    }
}
