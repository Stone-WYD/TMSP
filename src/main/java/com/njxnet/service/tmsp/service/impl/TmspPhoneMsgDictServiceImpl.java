package com.njxnet.service.tmsp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.framework.common.model.AjaxResult;
import com.njxnet.service.tmsp.dao.TmspPhoneMsgDictDao;
import com.njxnet.service.tmsp.entity.TmspPhoneMsgDict;
import com.njxnet.service.tmsp.service.PhoneMsgDictService;
import com.njxnet.service.tmsp.service.TmspPhoneMsgDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (TmspPhoneMsgDict)表服务实现类
 *
 * @author Stone
 * @since 2023-06-28 14:26:10
 */
@Service("tmspPhoneMsgDictService")
public class TmspPhoneMsgDictServiceImpl extends ServiceImpl<TmspPhoneMsgDictDao, TmspPhoneMsgDict> implements TmspPhoneMsgDictService {

    @Resource
    private PhoneMsgDictService interfaceMsgDictService;

    @Override
    public AjaxResult newModule(TmspPhoneMsgDict phoneMsgDict) {
        return null;
    }
}

