package com.pf.org.cms.hcg.system.service.impl;

import com.pf.org.cms.hcg.system.bean.PermissionDO;
import com.pf.org.cms.hcg.system.bean.UserDO;
import com.pf.org.cms.hcg.system.mapper.PermissionMapper;
import com.pf.org.cms.hcg.system.service.PermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {//====ok

	@Autowired
	PermissionMapper permissionMapper; //报错编译器问题


	@Override
	public List<PermissionDO> findByUser(UserDO user) {
		return permissionMapper.findByUser(user.getId());
	}

	@Override
	public List<PermissionDO> findByRoleId(Integer roleId) {
		return permissionMapper.findByRoleId(roleId);
	}
}
