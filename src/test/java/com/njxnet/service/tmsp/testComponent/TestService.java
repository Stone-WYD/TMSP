package com.njxnet.service.tmsp.testComponent;

import cn.hutool.core.collection.CollectionUtil;
import com.njxnet.service.tmsp.config.datasource.context.DsAno;
import com.njxnet.service.tmsp.config.datasource.context.DsEnum;
import com.njxnet.service.tmsp.entity.PhoneMsgDict;
import com.njxnet.service.tmsp.entity.TmspPhoneMsgDict;
import com.njxnet.service.tmsp.service.PhoneMsgDictService;
import com.njxnet.service.tmsp.service.TmspPhoneMsgDictService;
import com.njxnet.service.tmsp.service.impl.TmspPhoneMsgDictServiceImpl;
import com.njxnet.service.tmsp.testComponent.wrapper.WrapperMpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    @Resource
    private PhoneMsgDictService phoneMsgDictService;

    @Resource
    private TmspPhoneMsgDictService tmspPhoneMsgDictService;

    @Resource
    private TmspPhoneMsgDictServiceImpl tmspPhoneMsgDictServiceImpl;


    @Resource
    private WrapperMpService wrapperMpService;

    @DsAno(value = DsEnum.SECOND)
    public void testInterfaceConnection(){
        // 测试能否连接接口平台数据库
        PhoneMsgDict phoneMsgDict = phoneMsgDictService.getById(4);
        System.out.println(phoneMsgDict);

        // 结论：切换数据源的注解不能加在mp的service接口上，无法生效
        // 原因：注解加在接口上没什么意义，运行中真正运行方法的是 mybatis-plus 生成的代理类对象
        // 解决方法：把用到的内容再封装一层到一个类中
    }

    @Transactional(rollbackFor = Exception.class)
    public void testTransactionalUsingTwoDataSource() {
        // 测试事务是否可用
        tmspPhoneMsgDictService.update().set("mould_id", 123456).eq("id", 4).update();
        System.out.println(1/0);

        // 结论：单独用事务没问题
    }

    @DsAno(value = DsEnum.SECOND)
    @Transactional(rollbackFor = Exception.class)
    public void testTransactionalWithDsAno(){
        // 测试能否将事务注解和 @DsAno 注解一起使用
        phoneMsgDictService.update().set("mould_id", 123456).eq("id", 4).update();
        System.out.println(1/0);

        // 1.结论：两个注解无法一起用
        // 2.原因：事务管理器需要使用数据源，Spring 中事务生成代理的时机比 @DsAno 的时机早
        // 3.解决方法：在 @DsAno 上加入 @Order(-1) ，使其执行时机早于 @Transactional
    }

    public void testWithTwoDataSource(){
        // 测试能否方法内连接两个数据库
        // 连接本地库
        TmspPhoneMsgDict tmspPhoneMsgDict = tmspPhoneMsgDictService.getById(4);
        System.out.println(tmspPhoneMsgDict);
        System.out.println("=====================================");
        // 连接其他库
        PhoneMsgDict phoneMsgDict = wrapperMpService.queryById(4);
        System.out.println(phoneMsgDict);
    }

    public void testWithTwoDataSourceAsynchronous() throws InterruptedException {
        // 测试能否异步连接两个数据库
        // 连接本地库
        TmspPhoneMsgDict tmspPhoneMsgDict = tmspPhoneMsgDictService.getById(4);
        System.out.println(tmspPhoneMsgDict);
        System.out.println("=====================================");
        Thread thread = new Thread(() -> {
            // 异步连接其他库
            PhoneMsgDict phoneMsgDict = wrapperMpService.queryById(4);
            System.out.println(phoneMsgDict);
        });
        thread.start();
        thread.join();
    }

    @Transactional(rollbackFor = Exception.class)
    public void tesTransactionalWithTwoDataSource() throws InterruptedException {
        // 测试操作两个数据库，且要保证一起成功，失败一起回滚
        // 因为是操作的两个数据库，所以这里确保一致性无法只是使用声明式事务，后执行的第二个数据库的操作，需要使用编程式事务
        tmspPhoneMsgDictService.update().set("mould_id", "3372416").eq("id", 4).update();
        List<RuntimeException> exceptionList = new ArrayList<>();
        Thread thread = new Thread(() -> {
            wrapperMpService.update("3372416", exceptionList);
        });
        thread.start();
        thread.join();
        if (CollectionUtil.isNotEmpty(exceptionList)) {
            throw exceptionList.get(0);
        }

        // 1.结论：在一个线程中，只靠 @DsAno 注解和 编程式事务无法解决问题
        // 2.原因：事务管理器在 @Transactional 切面处理的时候就确定了一个数据源
        // 3.解决方案：开启线程异步处理，此时可以修改数据源，生成另外一个事务管理器，但是两个线程为了实现通信，需要通过一个 exceptionList
        //            此时其实不需要再用编程式事务了
    }

    @DsAno(value = DsEnum.SECOND)
    public void testMyMethodInMpService() {
        // 测试自己在mybatisplus service中定义的方法
        TmspPhoneMsgDict tmspPhoneMsgDict = tmspPhoneMsgDictServiceImpl.queryForTest(4L);
        System.out.println(tmspPhoneMsgDict);
    }
}
