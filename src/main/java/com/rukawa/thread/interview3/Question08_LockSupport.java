package com.rukawa.thread.interview3;

import java.util.concurrent.locks.LockSupport;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-17 10:28
 * @Version：1.0
 */
public class Question08_LockSupport {

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        char[] chsA = "ABCDEFG".toCharArray();
        char[] chsB = "1234567".toCharArray();

        t1 = new Thread(() -> {
            for (char c : chsA) {
                System.out.print(c);
                LockSupport.unpark(t2); // 叫醒t2
                LockSupport.park(); // t1阻塞
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (char c : chsB) {
                LockSupport.park(); // t2阻塞
                System.out.print(c);
                LockSupport.unpark(t1); // t1唤醒
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
