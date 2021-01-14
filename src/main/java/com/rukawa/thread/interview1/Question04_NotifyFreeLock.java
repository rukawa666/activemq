package com.rukawa.thread.interview1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with Intellij IDEA
 * 面试题1：实现一个容器，提供两个方法，add，size，写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5的时候，线程2给出提示并结束
 * @Author：SuperHai
 * @Date：2021-01-14 7:53
 * @Version：1.0
 */
public class Question04_NotifyFreeLock {

    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(0);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Question04_NotifyFreeLock obj = new Question04_NotifyFreeLock();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2启动");
                if (obj.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
                // 通知t1继续执行
                lock.notify();
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1启动");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    obj.add(new Object());
                    System.out.println("add " + i);

                    if (obj.size() == 5) {
                        lock.notify();      // notify() 不释放锁

                        // 释放锁，让t2执行
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();
    }
}
