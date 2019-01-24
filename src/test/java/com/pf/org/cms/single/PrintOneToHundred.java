package com.pf.org.cms.single;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by szh on 2019/1/17.
 *
 * @author szh
 */
public class PrintOneToHundred {


    static volatile int ct = 1;


    public static void main(String[] args) {

//        Cal lock = new Cal();

        ReentrantLock reentrantLock = new ReentrantLock();


        Thread a = new Thread(() -> {
            try {
                while (!(ct >= 100)) {
                    while ((ct%2==0)){
                        reentrantLock.lock();
                        ct++;
                        System.out.println(Thread.currentThread().getName() + " " + ct);
                        reentrantLock.unlock();
                    }
                }
            } finally {

            }
        }, "odd");

        Thread b = new Thread(() -> {
            try {
                while (!(ct >= 100)) {
                    while ((ct%2==1)){
                        reentrantLock.lock();
                        ct++;
                        System.out.println(Thread.currentThread().getName() + " " + ct);
                        reentrantLock.unlock();
                    }
                }
            } finally {

            }
        }, "even");

        a.start();
        b.start();
    }

}


class Cal {

    int num = 1;

}