package com.pf.org.cms.hcg.system.mapper;

import com.pf.org.cms.hcg.system.bean.PermissionDO;


import java.util.List;

public interface PermissionMapper {




    //============permissionDao==start==//sql ok
    List<PermissionDO> findByUser(Integer id);
    List<PermissionDO> findByRoleId(Integer roleId);



}