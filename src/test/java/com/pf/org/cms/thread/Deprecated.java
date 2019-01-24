package com.pf.org.cms.thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Deprecated {
    /*
    线程的打断和中断，原始的方法，但是这几种方法不建议用
     */


    public static void main(String[] args) throws Exception {
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        Thread printThread = new Thread(new Runner(), "PrintThread");
        printThread.setDaemon(true);
        printThread.start();
        TimeUnit.SECONDS.sleep(3);
// 将PrintThread进行暂停，输出内容工作停止
        printThread.suspend();
        System.out.println("main suspend PrintThread（暂停） at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);
// 将PrintThread进行恢复，输出内容继续
        printThread.resume();
        System.out.println("main resume（恢复） PrintThread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);
// 将PrintThread进行终止，输出内容停止
        printThread.stop();
        System.out.println("main stop （停止）PrintThread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);
    }

    static class Runner implements Runnable {
        @Override
        public void run() {
            DateFormat format = new SimpleDateFormat("HH:mm:ss");
            while (true) {
                System.out.println(Thread.currentThread().getName() + " Run at " +
                        format.format(new Date()));
                SleepUtils.second(1);
            }
        }
    }
}
