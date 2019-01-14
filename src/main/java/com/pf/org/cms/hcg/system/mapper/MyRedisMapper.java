package com.pf.org.cms.hcg.system.mapper;


import com.pf.org.cms.hcg.system.bean.MyRedisDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyRedisMapper {


    int del(Integer id);

    int insert(MyRedisDO record);

    MyRedisDO selectById(Integer id);


//    //获取用户id和user_name
    List<MyRedisDO> selectAll();
//
    int update(MyRedisDO record);


}