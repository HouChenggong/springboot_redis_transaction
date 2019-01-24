package com.pf.org.cms.thread;

public class Test {

    /*
    死锁，可以用Java bin里面的jconsole.exe来查看是否有死锁
     */

    public static void main(String[] args) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (B.class) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (A.class) {

                    }
                }
            }
        }).start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                synchronized (A.class) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    synchronized (B.class) {

                    }
                }

            }
        }).start();
    }

}

class A {

}

class B {

}
