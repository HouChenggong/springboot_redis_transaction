package com.pf.org.cms.thread;

import java.util.concurrent.TimeUnit;

public class SleepUtils {
    /*
    线程睡觉的公共类
     */
    public static final void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
        }
    }
}