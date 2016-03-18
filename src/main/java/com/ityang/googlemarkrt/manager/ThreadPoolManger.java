package com.ityang.googlemarkrt.manager;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/9/17.
 */
public class ThreadPoolManger {

    private static ThreadPoolManger threadPoolManger = new ThreadPoolManger();

    private ThreadPoolManger() {

    }

    public static ThreadPoolManger getInstance() {
        return threadPoolManger;
    }

    private ThreadPoolProxy longThreadPool;
    private ThreadPoolProxy shortThreadPool;

    public ThreadPoolProxy creatLongPool() {
        if (longThreadPool == null) {
            longThreadPool = new ThreadPoolProxy(5, 5, 5000L);
        }
        return longThreadPool;
    }

    public ThreadPoolProxy creatShortPool() {
        if (shortThreadPool == null) {
            shortThreadPool = new ThreadPoolProxy(3, 3, 5000L);
        }
        return shortThreadPool;
    }

    /**
     * 线程池代理类
     */
    public class ThreadPoolProxy {
        private ThreadPoolExecutor pool;
        private int corePoolSize;
        private int maximumPoolSize;
        private long time;

        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.time = time;
        }

        /**
         * 执行任务
         *
         * @param runnable
         */
        public void execute(Runnable runnable) {
            if (pool == null) {
                // 创建线程池
                /*
                 * 1. 线程池里面管理多少个线程2. 如果排队满了, 额外的开的线程数3. 如果线程池没有要执行的任务 存活多久4.
				 * 时间的单位 5 如果 线程池里管理的线程都已经用了,剩下的任务 临时存到LinkedBlockingQueue对象中 排队
				 */
                pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                        time, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(10));
            }
            pool.execute(runnable); // 调用线程池 执行异步任务
        }

        /**
         * 取消任务
         *
         * @param runnable
         */
        public void cancel(Runnable runnable) {
            if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
                pool.remove(runnable); // 取消异步任务
            }
        }

    }

}
