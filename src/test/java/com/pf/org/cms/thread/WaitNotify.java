package com.pf.org.cms.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNotify {
    /*
    创建了两个线程——WaitThread和NotifyThread，前者检查
    flag值是否为false，如果符合要求，进行后续操作，否则在lock上等待，后者在睡眠了一段时间
    后对lock进行通知，
     */
    static boolean falg = true;
    static Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread waitThread = new Thread(new Wait(), "WWW...waitThreadA");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "NNN....notifyThread");
        notifyThread.start();
    }


    static class Wait implements Runnable {

        @Override
        public void run() {
            //加锁


            synchronized (lock) {
                //不满足的时候，继续wait，同时释放lock的锁
                while (falg) {
                    try {
                        System.out.println(Thread.currentThread() + "flag is true waitting ....." + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //条件满足的时候，完成工作
                System.out.println(Thread.currentThread() + "flag is false  running ....." + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable {

        @Override
        public void run() {
            //加锁
            synchronized (lock) {
                //获取lock的锁，然后进行通知，通知的时候不会释放lock的锁
                //直到当前线程释放lock后，waitThread才能从wait（）方法中返回
                System.out.println(Thread.currentThread() + "hold lock notify......" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                falg = false;
                SleepUtils.second(5);
            }
            //再次加锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + "hold lock again. sleep......" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(3);

            }
        }
    }


}
