package com.rukawa.thread.modeule02;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-24 8:27
 * @Version：1.0
 */
public class M {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize...");
    }
}
