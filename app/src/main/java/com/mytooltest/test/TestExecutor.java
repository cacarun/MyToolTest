package com.mytooltest.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * RejectedExecutionException
 *
 */
public class TestExecutor {

    public ExecutorService fixedExecutorService = Executors.newFixedThreadPool(5);
    public ExecutorService cachedExecutorService = Executors.newCachedThreadPool();
    public ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();

    public ExecutorService customerExecutorService = new ThreadPoolExecutor(3,
            5, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());

    public void testExecutorException() {
        for (int i = 0; i < 10; i ++) {
            // 增加isShutdown()判断
            if (!fixedExecutorService.isShutdown()) {
                fixedExecutorService.execute(new SayHelloRunnable());
            }
            fixedExecutorService.shutdown();
        }
    }

    public void testCustomerExecutorException() {
        for (int i = 0; i < 100; i ++) {
            customerExecutorService.execute(new SayHelloRunnable());
        }
    }

    private class SayHelloRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("run!!!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("hello world!");
            }

        }
    }

    public static void main(String[] args) {
        TestExecutor testExecutor = new TestExecutor();
        // 1. 不要显示的调用shutdown方法，例如Android里，只有你在Destroy方法里cancel掉AsyncTask，则线程池里没有活跃线程会自己回收自己。
        // 2. 调用线程池时，判断是否已经shutdown，通过API方法isShutDown方法判断，
        testExecutor.testExecutorException();

        // 1. 尽量调大maximumPoolSize，例如设置为Integer.MAX_VALUE
        // 或者
        // 2. 使用其他排队策略，例如LinkedBlockingQueue
//        testExecutor.testCustomerExecutorException();
    }

}
