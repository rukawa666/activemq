package com.rukawa.thread.modeule03;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-28 7:47
 * @Version：1.0
 */
public class Test06_SynchronousQueue {

    // 同步Queue,保证传递
    // 容量为0，两个线程交换数据
    // 只能用put，put之前必须有个消费者等待消费
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strS = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(strS.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

         strS.put("aaa");    // 阻塞等待消费者消费
//        strS.add("aaa");    // 阻塞等待消费者消费

        System.out.println(strS.size());

    }
}
