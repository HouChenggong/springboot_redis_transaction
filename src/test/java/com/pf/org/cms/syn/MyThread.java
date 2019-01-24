package com.pf.org.cms.syn;



public class MyThread extends Thread {

    public void run() {
        Sync sync = new Sync();
        sync.pu();
        sync.fei();
        sync.test();
    }




    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            Thread thread = new MyThread();
            thread.setName("线程"+i);
            thread.start();
        }
    }
}


class Sync {

    public void test() {
        synchronized (Sync.class) {
            System.out.println(Thread.currentThread().getName()+"静态锁A开始..");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"静态锁A结束..");
        }
    }

    public void pu(){
        System.out.println(Thread.currentThread().getName()+"---------   一般的方法");
    }


    public synchronized void fei() {
            System.out.println(Thread.currentThread().getName()+"========普通锁B开始..");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"=========普通锁B结束..");
        }

}