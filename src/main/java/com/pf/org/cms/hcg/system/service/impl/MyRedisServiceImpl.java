package com.pf.org.cms.hcg.system.service.impl;


import com.pf.org.cms.hcg.system.bean.MyRedisDO;
import com.pf.org.cms.hcg.system.mapper.MyRedisMapper;
import com.pf.org.cms.hcg.system.service.MyRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service

public class MyRedisServiceImpl implements MyRedisService {
    @Autowired
    private MyRedisMapper myRedisMapper;

    @Override
    @Cacheable(value = "redisCache", key = "'redisMyid'+#id")
    public MyRedisDO getById(Integer id) {
        System.out.println("查询"+new Date());
        return myRedisMapper.selectById(id);
    }

    @Override
    @CacheEvict(value = "redisCache",key = "'redisMyid'+#id", beforeInvocation = false)
    public int delById(Integer id) {
        return myRedisMapper.del(id);
    }

    @Override
    @CachePut(value = "redisCache", key = "'redisMyid'+#myRedisDO.id")
    public MyRedisDO update(MyRedisDO myRedisDO) {
        myRedisMapper.update(myRedisDO);
        return myRedisDO;
    }

    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.NESTED)
    @CachePut(value = "redisCache", key = "'redisMyid'+#myRedisDO.id")
    public MyRedisDO insert(MyRedisDO myRedisDO) {
        myRedisMapper.insert(myRedisDO);
        return myRedisDO;
    }

    @Override
    public List<MyRedisDO> getAll() {
        return myRedisMapper.selectAll();
    }
}
