package com.njxnet.service.tmsp.design.core3_pipeline.pipeline.base;

import cn.hutool.core.collection.CollectionUtil;
import com.njxnet.service.tmsp.design.core3_pipeline.PipeLine;
import com.njxnet.service.tmsp.design.core3_pipeline.Valve;
import com.njxnet.service.tmsp.design.core3_pipeline.ValveContext;
import com.njxnet.service.tmsp.utils.ApplicationContextUtil;
import lombok.Data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @program: TMSP
 * @description: 管道基类,做一些管道初始化通用步骤的封装
 * @author: Stone
 * @create: 2023-07-11 21:37
 **/
@Data
public abstract class BasePipeLine<T extends Valve, C extends ValveContext> implements PipeLine {

    private T firstValve;

    public void init(Class<T> valveClazz){
        List<T> valveList = ApplicationContextUtil.getBeansOfType(valveClazz);

        if (CollectionUtil.isEmpty(valveList)) {
            throw new RuntimeException(this.getClass() + "没有对应的Valve");
        }

        // 排序
        Collections.sort(valveList, Comparator.comparing((T o) -> o.getPriprity()));

        // 拼接 valve 链条
        for (int i = 0; i < valveList.size()-1; i++) {
            valveList.get(i).setNext(valveList.get(i+1));
        }
        // 设置最后一个 valve，此处不必设置某种具体类型的 valve，因为 lastvalve 一般是做一些通用处理
        LastValve lastValve = new LastValve<>();
        valveList.get(valveList.size()-1).setNext(lastValve);

        firstValve = valveList.get(0);

    }

}
