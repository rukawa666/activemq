package com.rukawa.thread.modeule03;

import sun.rmi.runtime.Log;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-25 7:22
 * @Version：1.0
 */
public class Test_ConcurrentMap {

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();      // 38s
//        Map<String, String> map = new ConcurrentSkipListMap<>(); // 跳表。高并发且有序  44s
//        Map<String, String> map = new Hashtable<>();    // 39
//        Map<String, String> map = new HashMap<>(); //Collections.synchronizedXXX    // 72
        // TreeMap
        Random  r = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch latch = new CountDownLatch(ths.length);
        long start = System.currentTimeMillis();
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    map.put("a" + r.nextInt(100000), "a" + r.nextInt(100000));
                    latch.countDown();
                }
            });
        }

        Arrays.asList(ths).forEach(t -> t.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);


    }
}
