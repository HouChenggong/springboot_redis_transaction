package com.pf.org.cms.hcg.system.service.impl;

import com.pf.org.cms.hcg.system.bean.RoleDO;
import com.pf.org.cms.hcg.system.mapper.RoleMapper;
import com.pf.org.cms.hcg.system.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author huihui
 * @create 2017-12-06 11:32
 * @desc
 **/
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;


    @Override
    public RoleDO selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }
}
