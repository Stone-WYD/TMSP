package com.njxnet.service.tmsp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njxnet.framework.common.model.AjaxResult;
import com.njxnet.service.tmsp.config.datasource.context.*;
import com.njxnet.service.tmsp.entity.PhoneMsgDict;
import com.njxnet.service.tmsp.model.dto.TmspSysUserDTO;
import com.njxnet.service.tmsp.model.query.SysUserQuery;
import com.njxnet.service.tmsp.service.PhoneMsgDictService;
import com.njxnet.service.tmsp.service.TmspSysUserService;
import com.njxnet.service.tmsp.testComponent.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class ApplicationTest {

    @Resource
    private TmspSysUserService sysUserService;

    @Resource
    private PhoneMsgDictService phoneMsgDictService;

    @Resource
    private TestService testService;

    @Test
    public void testTmspDataSource(){
        SysUserQuery sysUserQuery = new SysUserQuery();
        sysUserQuery.setPage(1L);
        sysUserQuery.setSize(10L);
        AjaxResult<Page<TmspSysUserDTO>> result = sysUserService.queryUsers(sysUserQuery);
        for (TmspSysUserDTO record : result.getData().getRecords()) {
            System.out.println(record);
        }
    }

    @Test
    public void testInterfaceDataSource(){
        PhoneMsgDict phoneMsgDict = DsSelectExecutor.submit(DsEnum.SECOND, () -> phoneMsgDictService.getById(4));
        System.out.println(phoneMsgDict);
    }

    @Test
    public void testInterfaceDataSourceUsingAnnotate(){
        testService.test();
    }
}
