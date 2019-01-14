package com.pf.org.cms.hcg.system.service.impl;


import com.pf.org.cms.hcg.system.bean.UserDO;
import com.pf.org.cms.hcg.system.mapper.UserMapper;
import com.pf.org.cms.hcg.system.service.UserNewService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 * @author huihui
 * @create 2017-11-21 11:38
 * @desc 用户管理模块实现
 **/
@Service
public class UserNewServiceImpl implements UserNewService {

    @Autowired
    private UserMapper userMapper;





    @Override
    public UserDO getUserByLoginName(String loginName) {
        return userMapper.selectUserByLoginName(loginName);
    }
}
