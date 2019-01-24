package com.pf.org.cms.thread;

public class Xian {

    /*
        让三个线程安装顺序执行，如t1,t2,t3 想 t1执行，t2然后执行，t3最后执行
        只需要在线程创建的时候，t2里面写 t1.join();
        t3里面写t2.join();
        而不在乎 t1.start();不在乎他们的启动书写顺序
     */

    private static final long count = 10000l;

    public static void main(String[] args) throws InterruptedException {
        concurrency();

    }

    private static void concurrency() throws InterruptedException {



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开始执行线程："+Thread.currentThread().getName());
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a += 5;
                }
                System.out.println("a:" + a);
            }
        },"线程1");


        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("开始执行线程："+Thread.currentThread().getName());
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a += 5;
                }
                System.out.println("a222:" + a);
            }
        },"线程2");


        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("开始执行线程："+Thread.currentThread().getName());
                int a = 0;
                for (long i = 0; i < count; i++) {
                    a += 5;
                }

                System.out.println("a3333:" + a);
            }
        },"线程3");
        Runnable runner4 = ()->{

            StringBuffer s = new StringBuffer();
            System.out.println("这是自定义的lambda线程");
            for(int i= 0;i < 10;i++) {
                System.out.println(s.append("haha"));
            }

        };

        new Thread(runner4,"线程4").start();
        new Thread(() -> System.out.println("这最简单的lambda线程")).start();
        thread.start();
        thread3.start();
        thread2.start();

    }


}