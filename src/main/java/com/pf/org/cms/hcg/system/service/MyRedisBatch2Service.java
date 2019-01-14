package com.pf.org.cms.hcg.system.service;

import com.pf.org.cms.hcg.system.bean.MyRedisDO;

import java.util.List;

public interface MyRedisBatch2Service {


    int insertList2(List<MyRedisDO> myRedisDOList);

    MyRedisDO insert2(MyRedisDO myRedisDO);

}
