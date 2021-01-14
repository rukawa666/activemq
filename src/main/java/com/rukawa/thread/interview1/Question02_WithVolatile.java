package com.rukawa.thread.interview1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with Intellij IDEA
 * 面试题1：实现一个容器，提供两个方法，add，size，写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5的时候，线程2给出提示并结束
 *
 * @Author：SuperHai
 * @Date：2021-01-14 7:36
 * @Version：1.0
 */
public class Question02_WithVolatile {

//    volatile List lists = new ArrayList();

    volatile List lists =  Collections.synchronizedList(new ArrayList<>());

    public void add(Object o) {
        lists.add(0);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Question02_WithVolatile obj = new Question02_WithVolatile();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                obj.add(new Object());
                System.out.println("add " + i);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
           while (true) {
               if (obj.size() == 5) {
                   break;
               }
           }
           System.out.println("t2结束");
        }, "t2").start();
    }

}
