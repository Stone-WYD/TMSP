package com.njxnet.service.tmsp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.service.tmsp.dao.BlackListDao;
import com.njxnet.service.tmsp.entity.BlackList;
import com.njxnet.service.tmsp.service.BlackListService;
import org.springframework.stereotype.Service;

/**
 * (BlackList)表服务实现类
 *
 * @author Stone
 * @since 2023-07-07 16:30:17
 */
@Service("blackListService")
public class BlackListServiceImpl extends ServiceImpl<BlackListDao, BlackList> implements BlackListService {

}

