package com.pf.org.cms;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.time.Duration;

@EnableScheduling
@SpringBootApplication
//@MapperScan("com.pf.org.cms.mapper")
@EnableCaching
public class CmsApplication {


    //连接工厂
    @Autowired
    private RedisConnectionFactory connectionFactory = null;
    @Autowired
    private RedisTemplate redisTemplate;

    //任务池
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler = null;
    //Redis消息监听器
    @Autowired
    private MessageListener redisMessageListener = null;

    //自定义初始化
    @PostConstruct
    public void init() {
        initRedisTemplate();
    }

    //改变redistemplate对于键的序列化策略
    private void initRedisTemplate() {
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);

    }


    @Bean(name = "redisCacheManager")
    public RedisCacheManager initRedisCacheManager() {
        //redis加锁的写入器
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
        //启动redis缓存的默认配置
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        //设置JDK序列化器
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()));
        //禁止前缀
        configuration = configuration.disableKeyPrefix();
        //设置10Min超时
        configuration = configuration.entryTtl(Duration.ofMinutes(10));
        //创建redis缓存管理器
        RedisCacheManager redisCacheManager = new RedisCacheManager(writer, configuration);
        return redisCacheManager;

    }

    /*
        创建任务池，运行线程等待处理Redis的消息
     */
    @Bean
    public ThreadPoolTaskScheduler initTaskScheduler(){
        if (threadPoolTaskScheduler !=null){
            return threadPoolTaskScheduler;
        }
        threadPoolTaskScheduler=new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(20);
        return threadPoolTaskScheduler;
    }
    /*
    定义redis的监听容器
     */
@Bean
public RedisMessageListenerContainer initRedisContainer(){
    RedisMessageListenerContainer container =new RedisMessageListenerContainer();
    //redis连接工厂
    container.setConnectionFactory(connectionFactory);
    //设置运行任务池
    container.setTaskExecutor(initTaskScheduler());
    //定义监听渠道，名称位topic1
    Topic topic  =new ChannelTopic("topic1");
    //使用监听器监听REDIS的消息
    container.addMessageListener(redisMessageListener,topic);
    return container;
}

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
