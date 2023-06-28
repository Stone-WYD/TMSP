package com.njxnet.service.tmsp.testComponent;

import com.njxnet.service.tmsp.config.datasource.context.DsAno;
import com.njxnet.service.tmsp.entity.PhoneMsgDict;
import com.njxnet.service.tmsp.service.PhoneMsgDictService;
import com.njxnet.service.tmsp.service.TmspSysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@DsAno(ds = "interface")
@Service
public class TestService {

    @Resource
    private PhoneMsgDictService phoneMsgDictService;

    public void test(){
        PhoneMsgDict phoneMsgDict = phoneMsgDictService.getById(4);
        System.out.println(phoneMsgDict);
    }
}
