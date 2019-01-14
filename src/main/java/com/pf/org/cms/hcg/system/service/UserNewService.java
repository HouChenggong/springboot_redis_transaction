package com.pf.org.cms.hcg.system.service;



import com.pf.org.cms.hcg.system.bean.UserDO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author huihui
 * @create 2017-11-21 10:57
 * @desc 用户管理模块接口
 **/
public interface UserNewService {
    //    int deleteByPrimaryKey(Integer id);
//
//    int insertSelective(UserDO record);

//    UserDO selectByPrimaryKey(Integer id);



    UserDO getUserByLoginName(String loginName);

//    //获取用户id和user_name
//    List<UserDO> selectAllUserName();
//
//    int updateByPrimaryKeySelective(UserDO record);


}
