package com.rukawa.thread.module04;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-28 8:27
 * @Version：1.0
 */
public class Test02_sync_wait_notify {
    public static void main(String[] args) {
        final Object o = new Object();

        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        new Thread(() -> {
            synchronized (o) {
                for (char c : aC) {
                    System.out.println(c);
                    try {
                        o.notify();
                        o.wait();   // 让出锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify(); // 必须，否则无法停止程序
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (o) {
                for (char c : aI) {
                    System.out.println(c);
                    try {
                        o.notify();
                        o.wait();   // 让出锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify(); // 必须，否则无法停止程序
            }
        }, "t2").start();
    }
}
