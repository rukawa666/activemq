package com.rukawa.thread.modeule03;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-25 7:52
 * @Version：1.0
 */
public class Test04_ConcurrentQueue {

    public static void main(String[] args) {
        Queue<String> strS = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            strS.offer("a" + i);    // 添加，且有返回值
        }
        System.out.println(strS);
        System.out.println(strS.size());

        System.out.println(strS.poll());    // 出队
        System.out.println(strS.size());

        System.out.println(strS.peek());    // 查看队列头部元素
        System.out.println(strS.size());
    }
}
