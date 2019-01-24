package com.pf.org.cms.hcg.system.controller;


import com.pf.org.cms.hcg.system.miaosha.GoodsService;
import com.pf.org.cms.hcg.system.test.VolatileTest;
import com.pf.org.cms.manage.RedisManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Api("用户管理的控制层")
@Controller
@RequestMapping("/redis/*")
public class RedisTransaction {
    @Autowired
    RedisManager redisManager;

    private int value = 0;
    @Autowired
    GoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @ApiOperation(value = "multi测试接口", notes = "redis事务测试接口")
    @RequestMapping(value = "/multi", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> testmulti() {
        redisManager.setStr("wanwan", "wd小兔兔");
        List list = (List) redisTemplate.execute((RedisOperations res) ->
        {
            //设置监控key,在exec执行前如果这个key对应的值，发生了变化，事务bu执行
            //通常监控的key可以是ID，也可以是一个对象
            res.watch("wanwan");
            // 其实watch可以注释掉，或者设置成不监控
            res.unwatch();
            //开启事务，在exec执行前
            res.multi();
            res.opsForValue().increment("wanwan", 1);
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
        redisTemplate.convertAndSend("topic1", "这里是琬琬");
        return map;
    }


    @ApiOperation(value = "lua测试接口", notes = "lua测试接口")
    @RequestMapping(value = "/lua", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> lua() {
        DefaultRedisScript<String> re = new DefaultRedisScript<String>();
        //设置脚本
        re.setScriptText("return 'hello redis'");
        //定义返回值类型，注意，如果没有这个定义，Spring不会返回结果
        re.setResultType(String.class);
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        //执行LUA脚本
        String result = (String) redisTemplate.execute(re, stringRedisSerializer, stringRedisSerializer, null);
        Map<String, Object> map = new HashMap<>();
        map.put("str", result);
        return map;
    }


    @ApiOperation(value = "lua带参数的测试接口", notes = "lua带参数的测试接口")
    @RequestMapping(value = "/luaCan", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> luaCanShu() {
        String lua = "redis.call('set' ,KEYS[1],ARGV[1]  \n)" +
                "redis.call('set', KEYS[2], ARGV[2]  \n) " +
                " local str1 =redis.call('get', KEYS[1]  \n) " +
                " local str2 =redis.call('get', KEYS[2]  \n) " +
                "if str1 == str2 then \n" +
                "return 1 \n" +
                "end \n" +
                "return 0 \n";

        DefaultRedisScript<Long> re = new DefaultRedisScript<Long>();
        //设置脚本
        re.setScriptText(lua);
        //定义返回值类型，注意，如果没有这个定义，Spring不会返回结果
        re.setResultType(Long.class);
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();

        //定义KEY参数
        List<String> keyList = new ArrayList<>();
        keyList.add("22");
        keyList.add("222");
        //执行LUA脚本
        Long result = (Long) redisTemplate.execute(re, stringRedisSerializer, stringRedisSerializer, keyList, "不知道干啥", "也不知道啥用");
        Map<String, Object> map = new HashMap<>();
        map.put("str", result);
        return map;
    }


    @ApiOperation(value = "用事务秒杀测试接口", notes = "用事务秒杀测试接口")
    @RequestMapping(value = "/miaoTransaction", method = RequestMethod.GET)
    @ResponseBody
    public Long miaoTransaction() {

        Long res = goodsService.flashSellByRedisWatch("xiaomi", 1);
        return res;
    }


    @ApiOperation(value = " 秒杀Lua测试接口", notes = "秒杀Lua测试接口")
    @RequestMapping(value = "/miaoLua", method = RequestMethod.GET)
    @ResponseBody
    public Long miaoLua() {

        Long res = goodsService.flashSellByRedisWatch("xiaomi", 1);
        System.out.println(res.toString());
        return res;
    }


    @ApiOperation(value = "i++是否线程安全测试接口", notes = "是否线程安全测试接口")
    @RequestMapping(value = "/i", method = RequestMethod.GET)
    @ResponseBody
    public synchronized int i() {
        System.out.println("-----------之前：" + value);
        value++;
        System.out.println("============I++之后的值：" + value);
        System.out.println();
        System.out.println();

        return value;
    }

    @ApiOperation(value = "把值放到redis或者MySQL里面，i++是否线程安全测试接口", notes = "是否线程安全测试接口")
    @RequestMapping(value = "/redisZZ", method = RequestMethod.GET)
    @ResponseBody
    public synchronized int redisIvalue() {
        int value1 = Integer.parseInt(redisManager.getStr("zizeng"));
        System.out.println("-----------之前：" + value1);
        value1++;
        redisManager.setStr("zizeng", String.valueOf(value1));
        System.out.println("============I++之后的值：" + value1);
        System.out.println();
        System.out.println();

        return value1;
    }



    @ApiOperation(value = "volatileTest", notes = "volatileTest")
    @RequestMapping(value = "/volatileTestClass", method = RequestMethod.GET)
    @ResponseBody
    public long volatileTestClass() {
        VolatileTest v =new VolatileTest();
        v.get();
        v.getAndIncrement();
        return 0L;
    }

}
