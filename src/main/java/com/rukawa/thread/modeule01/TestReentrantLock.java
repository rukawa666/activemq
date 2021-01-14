package com.rukawa.thread.modeule01;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-23 22:40
 * @Version：1.0
 */
public class TestReentrantLock {

    private static volatile int i = 0;

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();

        lock.unlock();
    }
}
