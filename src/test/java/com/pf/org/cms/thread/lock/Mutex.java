package com.pf.org.cms.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


/*
                1.独占锁
                    独占锁就是在同一时刻只能有一个线程获取到锁，而其他获取锁的线程只能
                     处于同步队列中等待，只有获取锁的线程释放了锁，后继的线程才能够获取锁
         */

/*
Mutex中定义了一个静态内部类，该内部类继承了同步器并实现了独占式获取和释放同步
状态。在tryAcquire(int acquires)方法中，如果经过CAS设置成功（同步状态设置为1），则代表获
取了同步状态，而在tryRelease(int releases)方法中只是将同步状态重置为0。用户使用Mutex时
并不会直接和内部同步器的实现打交道，而是调用Mutex提供的方法，在Mutex的实现中，以获
取锁的lock()方法为例，只需要在方法实现中调用同步器的模板方法acquire(int args)即可，当
前线程调用该方法获取同步状态失败后会被加入到同步队列中等待，这样就大大降低了实现
一个可靠自定义同步组件的门槛。
 */
public class Mutex implements Lock {

    //静态内部类，自定义同步器
    private static class Sync extends AbstractQueuedSynchronizer {
        //是否是处于占用状态
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        //当状态时0得时候获取锁
        public boolean tryAcquire(int acquires) {
            if (compareAndSetState(1, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //释放锁，将状态设置为0
        protected boolean tryRelease(int releases) {
            if (getState() == 0) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        //返回一个Condition，每个condition都包含了一个condition队列
        Condition newCondition() {
            return new ConditionObject();
        }

    }

    // 仅需要将操作代理到Sync上即可
    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean isLocked() {
        return sync.isHeldExclusively();
    }

    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }


}
