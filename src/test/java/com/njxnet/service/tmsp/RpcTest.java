package com.njxnet.service.tmsp;

import com.njxnet.service.tmsp.common.AjaxResult;
import com.njxnet.service.tmsp.design.core5_aop_proxy.test.RpcTestService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @program: TMSP
 * @description: 测试对应com.njxnet.service.tmsp.design.core5_aop包下的内容
 * @author: Stone
 * @create: 2023-07-17 14:09
 **/
@SpringBootTest
public class RpcTest{

    @Resource
    private RpcTestService rpcTestService;


    @Test
    public void test(){
        AjaxResult wyd = rpcTestService.test("wyd");
        System.out.println(wyd);
    }


}
