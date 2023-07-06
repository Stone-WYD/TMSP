package com.njxnet.service.tmsp.service.impl;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.common.AjaxResultUtil;
import com.njxnet.service.tmsp.entity.TmspSysUser;
import com.njxnet.service.tmsp.service.TmspSysUserNewService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@ConditionalOnProperty(name = "wyd.new.usersvervice", havingValue = "true")
public class TmspSysUserNewServiceImpl extends TmspSysUserServiceImpl implements TmspSysUserNewService {


    @Override
    public AjaxResult newDesign(String test) {
        // 测试继承
        AjaxResult result = new AjaxResult<>();
        TmspSysUser user = getById(test);
        if (user != null) result.setData(user);
        else result.setData(test);
        return AjaxResultUtil.getTrueAjaxResult(result);
    }
}
