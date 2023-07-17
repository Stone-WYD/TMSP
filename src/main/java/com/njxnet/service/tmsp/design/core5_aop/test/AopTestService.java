package com.njxnet.service.tmsp.design.core5_aop.test;

import com.njxnet.service.tmsp.design.core5_aop.RpcProvider;
import org.springframework.stereotype.Service;

/**
 * @program: TMSP
 * @description: 测试aop，远程调用代理操作使用
 * @author: Stone
 * @create: 2023-07-17 14:05
 **/
@Service
@RpcProvider(clientClass = RpcTestService.class)
public class AopTestService {

   public String test(String testArg){
       System.out.println("运行到目标类内部方法了，传参为：" + testArg );
       return "target with testArg " + testArg;
   }
}
