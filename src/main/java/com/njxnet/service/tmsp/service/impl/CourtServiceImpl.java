package com.njxnet.service.tmsp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njxnet.service.tmsp.dao.CourtDao;
import com.njxnet.service.tmsp.entity.Court;
import com.njxnet.service.tmsp.service.CourtService;
import org.springframework.stereotype.Service;

/**
 * 法院表(Court)表服务实现类
 *
 * @author Stone
 * @since 2023-06-26 17:42:20
 */
@Service("courtService")
public class CourtServiceImpl extends ServiceImpl<CourtDao, Court> implements CourtService {

}

