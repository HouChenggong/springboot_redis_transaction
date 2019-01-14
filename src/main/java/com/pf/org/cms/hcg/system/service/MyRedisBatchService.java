package com.pf.org.cms.hcg.system.service;

import com.pf.org.cms.hcg.system.bean.MyRedisDO;

import java.util.List;

public interface MyRedisBatchService {


    int insert(List<MyRedisDO> myRedisDOList);

}
