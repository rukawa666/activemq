package com.rukawa.thread.modeule03;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-25 7:55
 * @Version：1.0
 */
public class Test02_ArrayBlockingQueue {

    static BlockingQueue<String> strS = new ArrayBlockingQueue<>(10);   // 有界队列

    static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            strS.put("a" + i);
        }

//        strS.put("aaa");    // 满了就会等待，程序阻塞
//        strS.add("aaa");    // 满了会抛出异常

        strS.offer("aaa");  // 满了，不在写入，返回
//        strS.offer("aaa",1, TimeUnit.SECONDS);

        System.out.println(strS);
    }
}
