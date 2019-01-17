package com.pf.org.cms.hcg.system.miaosha;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Long flashSellByLuaScript(String skuCode,int num) {
        String luaScript ="local buyNum = ARGV[1]\n" +
                "local goodsKey = KEYS[1]  \n" +
                "local goodsNum = redis.call('get',goodsKey) \n" +
                "if goodsNum >= buyNum \n" +
                "then redis.call('decrby',goodsKey,buyNum) \n" +
                "return buyNum \n" +
                "else \n" +
                "return '0'\n" +
                "end\n" +
                "\n" ;

        DefaultRedisScript<String> re = new DefaultRedisScript<String>();
        //设置脚本
        re.setScriptText(luaScript);
        //定义返回值类型，注意，如果没有这个定义，Spring不会返回结果
        re.setResultType(String.class);
        RedisSerializer<String> stringRedisSerializer = stringRedisTemplate.getStringSerializer();
        //执行LUA脚本
        String result = (String) stringRedisTemplate.execute(re, stringRedisSerializer, stringRedisSerializer, null);
        return Long.valueOf(result);
    }

    @Override
    public Long flashSellByRedisWatch(String skuCode,int num){

        SessionCallback<Long> sessionCallback = new SessionCallback<Long>() {
            @Override
            public Long execute(RedisOperations operations) throws DataAccessException {
                int result = num;
                //redis 乐观锁
                operations.watch(skuCode);
                ValueOperations<String, String> valueOperations = operations.opsForValue();
                String goodsNumStr = valueOperations.get(skuCode);
                Integer goodsNum = Integer.valueOf(goodsNumStr);
                //标记一个事务块的开始。
                //事务块内的多条命令会按照先后顺序被放进一个队列当中，
                //最后由 EXEC 命令原子性(atomic)地执行。
                operations.multi();
                if (goodsNum >= num) {
                    valueOperations.increment(skuCode, 0 - num);
                } else {
                    result = 0;
                }
                //多条命令执行的结果集合
                List exec = operations.exec();
                if(exec.size()>0){
                    System.out.println(exec);
                }
                return (long) result;
            }
        };
        return stringRedisTemplate.execute(sessionCallback);
    }
//省略 其他的方法


}
