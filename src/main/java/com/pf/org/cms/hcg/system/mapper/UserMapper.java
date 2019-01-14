package com.pf.org.cms.hcg.system.mapper;



import com.pf.org.cms.hcg.system.bean.UserDO;

import java.util.List;

public interface UserMapper {


//    int deleteByPrimaryKey(Integer id);
//
//    int insertSelective(UserDO record);

//    UserDO selectByPrimaryKey(Integer id);

    UserDO selectUserByLoginName(String loginName);



//    //获取用户id和user_name
//    List<UserDO> selectAllUserName();
//
//    int updateByPrimaryKeySelective(UserDO record);


}