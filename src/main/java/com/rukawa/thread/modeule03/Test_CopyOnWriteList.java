package com.rukawa.thread.modeule03;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-25 7:37
 * @Version：1.0
 */
public class Test_CopyOnWriteList {

    public static void main(String[] args) {
        List<String> lists =
//                new ArrayList<>();  // 这个出现并发问题
//                new Vector<>();
                new CopyOnWriteArrayList<>();

        Random r = new Random();
        Thread[] ths = new Thread[100];

        for (int i = 0; i < ths.length; i++) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        lists.add("a" + r.nextInt(10000));
                    }
                }
            };
            ths[i] = new Thread(task);
        }

        runAndComputeTime(ths);
        System.out.println(lists.size());
    }

    static void runAndComputeTime(Thread[] ths) {
        long start = System.currentTimeMillis();
        Arrays.asList(ths).forEach(t -> t.start());
        Arrays.asList(ths).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
