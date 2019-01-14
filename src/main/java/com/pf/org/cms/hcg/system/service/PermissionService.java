package com.pf.org.cms.hcg.system.service;

import com.pf.org.cms.hcg.system.bean.PermissionDO;
import com.pf.org.cms.hcg.system.bean.UserDO;


import java.util.List;

public interface PermissionService {


	public List<PermissionDO> findByUser(UserDO user);

	public List<PermissionDO> findByRoleId(Integer roleId);


}
