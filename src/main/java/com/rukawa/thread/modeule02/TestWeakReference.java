package com.rukawa.thread.modeule02;

import java.lang.ref.WeakReference;

/**
 * Created with Intellij IDEA
 *
 * @Author：SuperHai
 * @Date：2020-12-24 8:28
 * @Version：1.0
 */
public class TestWeakReference {

    // 弱引用，只要gc立马回收
    // 只要强引用指向它，强引用消失，不用去管。  一般用在容器中
    public static void main(String[] args) {
        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        ThreadLocal<M> tl = new ThreadLocal<>();
        tl.set(new M());
        tl.remove();

    }
}
