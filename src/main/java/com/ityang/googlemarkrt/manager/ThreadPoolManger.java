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
     * �̳߳ش�����
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
         * ִ������
         *
         * @param runnable
         */
        public void execute(Runnable runnable) {
            if (pool == null) {
                // �����̳߳�
                /*
                 * 1. �̳߳����������ٸ��߳�2. ����Ŷ�����, ����Ŀ����߳���3. ����̳߳�û��Ҫִ�е����� �����4.
				 * ʱ��ĵ�λ 5 ��� �̳߳��������̶߳��Ѿ�����,ʣ�µ����� ��ʱ�浽LinkedBlockingQueue������ �Ŷ�
				 */
                pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                        time, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<Runnable>(10));
            }
            pool.execute(runnable); // �����̳߳� ִ���첽����
        }

        /**
         * ȡ������
         *
         * @param runnable
         */
        public void cancel(Runnable runnable) {
            if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
                pool.remove(runnable); // ȡ���첽����
            }
        }

    }

}
