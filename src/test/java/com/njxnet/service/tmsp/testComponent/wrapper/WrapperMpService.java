package com.njxnet.service.tmsp.testComponent.wrapper;

import com.njxnet.service.tmsp.config.datasource.context.DsAno;
import com.njxnet.service.tmsp.config.datasource.context.DsEnum;
import com.njxnet.service.tmsp.entity.PhoneMsgDict;
import com.njxnet.service.tmsp.service.PhoneMsgDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WrapperMpService {

    @Resource
    private PhoneMsgDictService phoneMsgDictService;

    @Resource
    private PlatformTransactionManager transactionManager;

    @DsAno(value = DsEnum.SECOND)
    public PhoneMsgDict queryById(int i) {
       return phoneMsgDictService.getById(i);
    }

    @DsAno(value = DsEnum.SECOND)
    public boolean update(String mouldId, List<RuntimeException> exceptionList){
        // 编程式事务
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 事务操作
            phoneMsgDictService.update().set("mould_id", mouldId).eq("id", 4).update();
            // 出错回滚
            // System.out.println(1/0);
            // 事务提交
            transactionManager.commit(status);
            return true;
        } catch (RuntimeException re) {
            // 事务提交
            transactionManager.rollback(status);
            exceptionList.add(re);
            return false;
        }
    }
}
