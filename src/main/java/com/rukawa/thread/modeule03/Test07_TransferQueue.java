package com.rukawa.thread.modeule03;

import java.util.concurrent.LinkedTransferQueue;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-28 8:12
 * @Version：1.0
 */
public class Test07_TransferQueue {


    public static void main(String[] args) throws InterruptedException {

        LinkedTransferQueue<String> strS = new LinkedTransferQueue<>();

        // 添加完，等待结果，在继续执行
        strS.transfer("aaa");

        // 装完直接走
        strS.put("aaa");

        new Thread(() -> {
            try {
                System.out.println(strS.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
