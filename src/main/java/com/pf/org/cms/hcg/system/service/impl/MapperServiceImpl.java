package com.pf.org.cms.hcg.system.service.impl;

import com.pf.org.cms.hcg.system.bean.MapperDO;
import com.pf.org.cms.hcg.system.service.BaseServiceClient;
import com.pf.org.cms.hcg.system.service.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "mapperCommonService")
public class MapperServiceImpl implements MapperService {

    @Autowired
    private BaseServiceClient baseServiceClient;
    @Override
    public void add(MapperDO mapperDO) {
        baseServiceClient.insert(mapperDO);
    }
}
