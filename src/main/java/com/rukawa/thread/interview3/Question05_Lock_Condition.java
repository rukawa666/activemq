package com.rukawa.thread.interview3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2021-01-17 10:53
 * @Version：1.0
 */
public class Question05_Lock_Condition {

    public static void main(String[] args) {
        char[] chsA = "ABCDEFG".toCharArray();
        char[] chsB = "1234567".toCharArray();

        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        new Thread(() -> {
            try {
                lock.lock();

                for (char c : chsA) {
                    System.out.print(c);
                    condition2.signal();
                    condition1.wait();
                }
                condition2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();


        new Thread(() -> {
            try {
                lock.lock();
                for (char c : chsB) {
                    System.out.print(c);
                    condition1.signal();
                    condition2.wait();
                }
                condition1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}
