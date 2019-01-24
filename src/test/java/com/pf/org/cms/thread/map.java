package com.pf.org.cms.thread;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class map {
    /*
    （1）线程不安全的HashMap
        在多线程环境下，使用HashMap进行put操作会引起死循环，导致CPU利用率接近100%，所
        以在并发情况下不能使用HashMap。例如，执行以下代码会引起死循环。
     */

    /*
        2. HashMap在并发执行put操作时会引起死循环，是因为多线程会导致HashMap的Entry链表
    形成环形数据结构，一旦形成环形数据结构，Entry的next节点永远不为空，就会产生死循环获
    取Entry。
     */
    /*
    （3）效率低下的HashTable
     HashTable容器使用synchronized来保证线程安全，但在线程竞争激烈的情况下HashTable
     的效率非常低下。因为当一个线程访问HashTable的同步方法，其他线程也访问HashTable的同
        步方法时，会进入阻塞或轮询状态。如线程1使用put进行元素添加，线程2不但不能使用put方
        法添加元素，也不能使用get方法来获取元素，所以竞争越激烈效率越低
     */

    /*
    4. ConcurrentHashMap的锁分段技术可有效提升并发访问率
HashTable容器在竞争激烈的并发环境下表现出效率低下的原因是所有访问HashTable的
线程都必须竞争同一把锁，假如容器里有多把锁，每一把锁用于锁容器其中一部分数据，那么
当多线程访问容器里不同数据段的数据时，线程间就不会存在锁竞争，从而可以有效提高并
发访问效率，这就是ConcurrentHashMap所使用的锁分段技术。首先将数据分成一段一段地存
储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数
据也能被其他线程访问。
     */
    public static void main(String[] args) throws Exception {

        ConcurrentHashMap<String,String> a = new ConcurrentHashMap<String, String>();
        ConcurrentLinkedQueue<String> queue =new ConcurrentLinkedQueue<>();
        queue.add("1");
        queue.add("12");
        queue.add("13");
        queue.add("14");
        System.out.println(queue.toArray());
        final HashMap<String, String> map = new HashMap<String, String>(2);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            map.put(UUID.randomUUID().toString(), "");
                            System.out.println(map.toString());
                        }
                    }, "子线程" + i).start();
                }
            }
        }, "线程");
        t.start();
        t.join();


    }
}
