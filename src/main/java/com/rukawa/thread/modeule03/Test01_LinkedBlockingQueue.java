package com.rukawa.thread.modeule03;

import java.sql.Time;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-25 7:52
 * @Version：1.0
 */
public class Test01_LinkedBlockingQueue {

    static BlockingQueue<String> strS = new LinkedBlockingQueue<>(); // 无界队列

    static Random r = new Random();

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    strS.put("a" + i);  // 如果满了就会等待
                    TimeUnit.MILLISECONDS.sleep(r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "p1").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (;;) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " take - " + strS.take()); // 如果空了，阻塞
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "c" + i).start();
        }
    }
}
