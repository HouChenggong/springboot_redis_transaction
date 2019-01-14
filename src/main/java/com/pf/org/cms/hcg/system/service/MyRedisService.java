package com.pf.org.cms.hcg.system.service;

import com.pf.org.cms.hcg.system.bean.MyRedisDO;

import java.util.List;

public interface MyRedisService {
    MyRedisDO getById(Integer id);

    int delById(Integer id);

    MyRedisDO update(MyRedisDO myRedisDO);

    MyRedisDO insert(MyRedisDO myRedisDO);

    List<MyRedisDO> getAll();
}
