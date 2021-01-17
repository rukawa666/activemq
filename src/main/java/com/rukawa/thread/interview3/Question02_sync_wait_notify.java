package com.rukawa.thread.interview3;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-17 10:24
 * @Version：1.0
 */
public class Question02_sync_wait_notify {

    public static void main(String[] args) {
        final Object o = new Object();

        char[] chsA = "ABCDEFG".toCharArray();
        char[] chsB = "1234567".toCharArray();

        new Thread(() -> {
            synchronized (o) {
                for (char chs : chsA) {
                    System.out.print(chs);
                    try {
                        // 唤醒其他线程
                        o.notify();
                        // 让出锁
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify(); // 必须，否则无法停止程序
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (o) {
                for (char c : chsB) {
                    System.out.print(c);
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }, "t2").start();



    }
}
