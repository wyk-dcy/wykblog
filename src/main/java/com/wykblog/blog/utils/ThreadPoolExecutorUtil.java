package com.wykblog.blog.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiazhengyue
 * @since 2020-03-24
 */
public class ThreadPoolExecutorUtil {

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(2 * Runtime.getRuntime().availableProcessors(),
            20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), new MyThreadFactory());

    private static class MyThreadFactory implements ThreadFactory {

        private static AtomicInteger num = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "ThreadPoolExecutorUtil-" + num.getAndIncrement());
        }
    }

    public static void execute(Runnable runnable) {
        EXECUTOR.execute(runnable);
    }
}
