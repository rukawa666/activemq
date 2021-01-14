package com.rukawa.thread.module04;

import java.util.concurrent.locks.LockSupport;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-28 8:41
 * @Version：1.0
 */
public class Test08_LockSupport {

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEF".toCharArray();


        t1 = new Thread(() -> {
            for (char c : aI) {
                System.out.println(c);
                LockSupport.unpark(t2); // 唤醒t2
                LockSupport.park(); // 阻塞t1
            }
        }, "t1");

        t2 = new Thread(() -> {
            for (char c : aC) {
                System.out.println(c);
                LockSupport.unpark(t1); // 唤醒t1
                LockSupport.park(); // 阻塞t2
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
