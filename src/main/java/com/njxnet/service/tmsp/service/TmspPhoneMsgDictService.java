package com.njxnet.service.tmsp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njxnet.framework.common.model.AjaxResult;
import com.njxnet.service.tmsp.entity.TmspPhoneMsgDict;

/**
 * (TmspPhoneMsgDict)表服务接口
 *
 * @author Stone
 * @since 2023-06-28 14:26:10
 */
public interface TmspPhoneMsgDictService extends IService<TmspPhoneMsgDict> {

    AjaxResult newModule(TmspPhoneMsgDict phoneMsgDict);
}

