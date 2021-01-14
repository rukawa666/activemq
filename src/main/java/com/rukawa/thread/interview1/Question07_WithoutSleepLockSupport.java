package com.rukawa.thread.interview1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * Created with Intellij IDEA
 * 面试题1：实现一个容器，提供两个方法，add，size，写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5的时候，线程2给出提示并结束
 *
 * @Author：SuperHai
 * @Date：2021-01-14 7:36
 * @Version：1.0
 */
public class Question07_WithoutSleepLockSupport {

    List lists = new ArrayList();

    public void add(Object o) {
        lists.add(0);
    }

    public int size() {
        return lists.size();
    }

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        Question07_WithoutSleepLockSupport obj = new Question07_WithoutSleepLockSupport();

        t2 = new Thread(() -> {
            System.out.println("t2启动");

            if (obj.size() != 5) {
                LockSupport.park();
            }

            System.out.println("t2结束");
            LockSupport.unpark(t1);
        }, "t2");

        t1 = new Thread(() -> {
            System.out.println("t1启动");
            for (int i = 0; i < 10; i++) {
                obj.add(new Object());
                System.out.println("add " + i);

                if (obj.size() == 5) {
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        }, "t1");

        t2.start();
        t1.start();
    }

}
