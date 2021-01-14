package com.rukawa.thread.module04;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-28 9:07
 * @Version：1.0
 */
public class Test04_Lock_condition {

    public static void main(String[] args) {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            try {
                lock.lock();

                for (char c : aC) {
                    System.out.println(c);
                    condition.signal();
                    condition.await();
                }

                condition.signal();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();


        new Thread(() -> {
            try {
                lock.lock();

                for (char c : aI) {
                    System.out.println(c);
                    condition.signal();
                    condition.await();
                }

                condition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}
