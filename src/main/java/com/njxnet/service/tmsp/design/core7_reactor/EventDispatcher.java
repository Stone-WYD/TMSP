package com.njxnet.service.tmsp.design.core7_reactor;

import com.njxnet.service.tmsp.design.core7_reactor.worker.AppWorker;
import com.njxnet.service.tmsp.design.core7_reactor.worker.NetWorker;
import lombok.Data;

/**
 * @program: TMSP
 * @description: 事件分发器
 * @author: Stone
 * @create: 2023-07-30 18:25
 **/
@Data
public class EventDispatcher {
    private AppWorker appWorker;

    private NetWorker netWorker;


}
