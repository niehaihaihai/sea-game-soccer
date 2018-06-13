package com.coocaa.ie.core.utils.thread;

import java.util.concurrent.ThreadFactory;

public class CCThread {
    private static final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {

        }
    };
    public static final ThreadFactory threadFactory = new ThreadFactory() {
        private int count = 0;

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "ccthread_" + count++);
            thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            return thread;
        }
    };
}
