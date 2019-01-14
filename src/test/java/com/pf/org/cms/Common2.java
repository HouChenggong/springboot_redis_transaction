package com.pf.org.cms;

import com.pf.org.cms.hcg.system.bean.MapperDO;
import com.pf.org.cms.hcg.system.bean.MyRedisDO;
import com.pf.org.cms.hcg.system.service.MapperService;
import com.pf.org.cms.hcg.system.service.MyRedisBatch2Service;
import com.pf.org.cms.hcg.system.service.MyRedisBatchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Common2 {
    @Autowired
    MapperService mapperService;

    @Autowired
    private MyRedisBatch2Service myRedisBatchService;

    @Test
    public void addUserInfos() throws Exception {
        MapperDO mapperDO = new MapperDO();
        //不用设置ID，因为我们数据库自增了
        mapperDO.setName("小22米");
        mapperDO.setSex(2);
        //虽然我们数据库sex字段默认为0，但是这里如果不设置的话会报错，问题暂时没有解决
        mapperService.add(mapperDO);
    }


    @Test
    public void co() {
        MyRedisDO mapperDO = new MyRedisDO();
       mapperDO.setName("2a");
        MyRedisDO mapperDO1 = new MyRedisDO();
        mapperDO1.setName("2b");
        MyRedisDO mapperDO2 = new MyRedisDO();
        MyRedisDO mapperDO3 = new MyRedisDO();
        mapperDO3.setName("2d");
        MyRedisDO mapperDO4 = new MyRedisDO();
        mapperDO4.setName("2e");
        List<MyRedisDO> mapperDOList = new ArrayList<>();
        mapperDOList.add(mapperDO);
        mapperDOList.add(mapperDO1);
        mapperDOList.add(mapperDO2);
        mapperDOList.add(mapperDO3);
        mapperDOList.add(mapperDO4);
        int count = myRedisBatchService.insertList2(mapperDOList);
        System.out.println(count);

    }

}
