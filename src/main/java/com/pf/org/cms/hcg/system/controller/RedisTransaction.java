package com.pf.org.cms.hcg.system.controller;


import com.pf.org.cms.manage.RedisManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("用户管理的控制层")
@Controller
@RequestMapping("/redis/*")
public class RedisTransaction {
    @Autowired
    RedisManager redisManager;

    @Autowired
    private RedisTemplate redisTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @ApiOperation(value = "multi测试接口", notes = "redis事务测试接口")
    @RequestMapping(value = "/multi", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> testmulti() {
        redisManager.setStr("wanwan1", "wd小兔兔");
        List list = (List) redisTemplate.execute((RedisOperations res) ->
        {
            //设置监控key
            res.watch("wanwan");
            //开启事务，在exec执行前
            res.multi();
//            res.opsForValue().increment("wanwan",1);
            res.opsForValue().set("wanwan2", "我的小兔兔1");
            Object value2 = res.opsForValue().get("wanwan2");
            System.out.println("命令在队列，所以取值为空" + value2 + "----");
            res.opsForValue().set("wanwan3", "我的小兔兔3");
            Object value3 = res.opsForValue().get("wanwan3");
            System.out.println("命令在队列，所以取值为空" + value3 + "----");
            return res.exec();
        });
        System.out.println(list);

        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        System.out.println(";;;" + map.toString());
        return map;
    }

    @ApiOperation(value = "pipline测试接口", notes = "redis流水线测试接口")
    @RequestMapping(value = "/pipline", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pipline() {
        Long start = System.currentTimeMillis();
        List list = (List) redisTemplate.executePipelined((RedisOperations res) ->
        {
            for (int i = 0; i < 100; i++) {
                res.opsForValue().set("wanwan" + i, "我的小兔兔" + i);
                Object value2 = res.opsForValue().get("wanwan" + i);
                if (i == 99) {
                    System.out.println("value is null " + value2);
                }
            }

            return null;
        });
        Long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start) + "毫秒");
        System.out.println("list" + list.toString());
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        System.out.println(";;;" + map.toString());
        return map;
    }

}
