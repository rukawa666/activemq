package com.rukawa.thread.interview1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created with Intellij IDEA
 * 面试题1：实现一个容器，提供两个方法，add，size，写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5的时候，线程2给出提示并结束
 *
 * 这里使用wait和notify做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是，运用这种方法，必须保证t2先执行，也就是首先让t2监听才行
 *
 * notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续执行，整个通信比较繁琐
 *
 * 使用latch替代wait notify来进行通知
 * 好处是通信方式简单，同时也可以指定等待时间
 * 使用await和countDown方法替代wait和notify
 * CountDownLatch不涉及锁定，当count的值为零时当前线程继续执行
 * 当不涉及同步，只是涉及线程通信的时候，用synchronized+wait/notify就显得繁琐
 * 这时应该考虑countDownLatch/Cyclicbarrier/Sempahore
 * @Author：SuperHai
 * @Date：2021-01-14 8:08
 * @Version：1.0
 */
public class Question05_CountDownLatch {
    volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(0);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Question05_CountDownLatch obj = new Question05_CountDownLatch();

        CountDownLatch latch1 = new CountDownLatch(1);

        CountDownLatch latch2 = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("t2启动");
            if (obj.size() != 5) {
                try {
                    latch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2结束");
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1启动");
            for (int i = 0; i < 10; i++) {
                obj.add(new Object());
                System.out.println("add " + i);

                if (obj.size() == 5) {
                    // 打开门栓，让t2执行
                    latch1.countDown();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();
    }
}
