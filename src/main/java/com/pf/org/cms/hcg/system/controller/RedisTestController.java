package com.pf.org.cms.hcg.system.controller;


import com.pf.org.cms.hcg.system.bean.MyRedisDO;
import com.pf.org.cms.hcg.system.service.MyRedisService;
import com.pf.org.cms.manage.RedisManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiyou
 * @create 2017-11-21 17:08
 * @desc 系统管理控制层
 **/
@Api("用户管理的控制层")
@Controller
@RequestMapping("/redis/*")
public class RedisTestController {


    @Autowired
    RedisManager redisManager;

    @Autowired
    MyRedisService myRedisService;


    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @ApiOperation(value = "Redis测试接口", notes = "swagger测试接口")
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    public String testRedis(
            @RequestParam("key") String key) {
        System.out.println("入参key为:" + key);
        String s = "查询结果为:" + redisManager.getStr(key);
        return s;
    }


    @ApiOperation(value = "Redis测试接口,Insert", notes = "Insert")
    @RequestMapping(value = "/demo/insertRedis", method = RequestMethod.GET)
    @ResponseBody
    public String redis1(
            @RequestParam("name") String name, @RequestParam("note") String note) {
        MyRedisDO myRedisDO = new MyRedisDO();
        myRedisDO.setName(name);
        myRedisDO.setNote(note);
        myRedisService.insert(myRedisDO);
        return "ok";
    }

    @ApiOperation(value = "Redis测试接口,get", notes = "get")
    @RequestMapping(value = "/demo/getRedis", method = RequestMethod.GET)
    @ResponseBody
    public String ge(
            @RequestParam("id") Integer id) {

        MyRedisDO redisDO = myRedisService.getById(id);
        MyRedisDO redisDO1 = myRedisService.getById(id);
        return redisDO.toString() + redisDO1.toString();
    }


    @ApiOperation(value = "Redis测试接口,del", notes = "del")
    @RequestMapping(value = "/demo/delRedis", method = RequestMethod.GET)
    @ResponseBody
    public int del(
            @RequestParam("id") Integer id) {

        int success = myRedisService.delById(id);
        return success;
    }

    @ApiOperation(value = "Redis测试接口,update", notes = "update")
    @RequestMapping(value = "/demo/updateRedis", method = RequestMethod.GET)
    @ResponseBody
    public MyRedisDO update(
            @RequestParam("id") Integer id) {
        MyRedisDO myRedisDO = myRedisService.getById(id);
        myRedisDO.setNote("更改");
        myRedisDO.setName("更改");
        myRedisService.update(myRedisDO);
        return myRedisDO;
    }
}

