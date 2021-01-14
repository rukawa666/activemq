package com.rukawa.thread.modeule02;

import java.io.IOException;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-24 8:28
 * @Version：1.0
 */
public class TestNormalReference {

    // 强引用，当对象为null 回收
    public static void main(String[] args) throws IOException {
        M m = new M();
        m = null;

        System.gc();

        System.in.read();  // 阻塞当前线程
    }
}
