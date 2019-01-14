package com.pf.org.cms.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyStaticTask {


    private static final Logger log = LoggerFactory.getLogger(MyStaticTask.class);
    /**
     * 固定cron配置定时任务
     */
    @Scheduled(cron = "0 0/5 10-20 * * ?")
    public void doTask(){
        log.info("定时任务0：执行了MyStaticTask,时间为:"+new Date(System.currentTimeMillis()));
    }
    /**
     * 定时任务
     */
    @Scheduled(cron = "${scheduller.task1.cron}")
    public void task1() {
        log.info("定时任务1 开始执行...");
        // TODO do something
    }

    /**
     * 定时任务
     */
    @Scheduled(cron = "${scheduller.task2.cron}")
    public void task2() {
        log.info("定时任务2 开始执行...");
        // TODO do something
    }
}
