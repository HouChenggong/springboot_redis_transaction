package com.pf.org.cms.hcg.system.service.impl;

import com.pf.org.cms.hcg.system.bean.MyRedisDO;
import com.pf.org.cms.hcg.system.service.MyRedisBatchService;
import com.pf.org.cms.hcg.system.service.MyRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyRedisBatchServiceImpl implements MyRedisBatchService {

    @Autowired
    private MyRedisService myRedisService;

    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int insert(List<MyRedisDO> myRedisDOList) {
        int count = 0;
        MyRedisDO jihe = new MyRedisDO();
        jihe.setName("这是集合插入的");
        jihe = myRedisService.insert(jihe);
        try {

            for (MyRedisDO myRedisDO : myRedisDOList) {
                MyRedisDO myRedisDO1 = myRedisService.insert(myRedisDO);
                if (myRedisDO1 != null) {
                    count++;
                }
            }
        } catch (Exception e) {
            System.out.println("子方法发生异常，但是不影响当前方法里面的事务");
        }
        return count;
    }
}
