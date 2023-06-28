package com.njxnet.service.tmsp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.service.tmsp.dao.PhoneMsgDictDao;
import com.njxnet.service.tmsp.entity.PhoneMsgDict;
import com.njxnet.service.tmsp.service.PhoneMsgDictService;
import org.springframework.stereotype.Service;

/**
 * (PhoneMsgDict)表服务实现类
 *
 * @author Stone
 * @since 2023-06-28 11:09:01
 */
@Service("phoneMsgDictService")
public class PhoneMsgDictServiceImpl extends ServiceImpl<PhoneMsgDictDao, PhoneMsgDict> implements PhoneMsgDictService {

}

