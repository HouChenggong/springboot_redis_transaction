package com.pf.org.cms.hcg.system.service.impl;

import com.pf.org.cms.hcg.system.bean.MyRedisDO;
import com.pf.org.cms.hcg.system.mapper.MyRedisMapper;
import com.pf.org.cms.hcg.system.service.MyRedisBatch2Service;
import com.pf.org.cms.hcg.system.service.MyRedisBatchService;
import com.pf.org.cms.hcg.system.service.MyRedisService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyRedisBatch2ServiceImpl implements MyRedisBatch2Service {


    @Autowired
    private MyRedisMapper myRedisMapper;

    @Autowired
    private MyRedisBatch2Service myRedisBatch2Service;



    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int insertList2(List<MyRedisDO> myRedisDOList) {
        int count = 0;
        MyRedisDO jihe = new MyRedisDO();
        jihe.setName("这是集合插入的");
        jihe = myRedisBatch2Service.insert2(jihe);
        for (MyRedisDO myRedisDO : myRedisDOList) {
            MyRedisDO myRedisDO1 = myRedisBatch2Service.insert2(myRedisDO);
            if (myRedisDO1 != null) {
                count++;
            }
        }

        return count;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public MyRedisDO insert2(MyRedisDO myRedisDO) {
        myRedisMapper.insert(myRedisDO);
        return myRedisDO;
    }
}
