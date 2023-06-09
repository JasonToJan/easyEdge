package com.baidu.ai.edge.core.util;

public class TimerRecorder {
    private static long a;

    public static synchronized long end() {
        long currentTimeMillis;
        synchronized (TimerRecorder.class) {
            currentTimeMillis = System.currentTimeMillis() - a;
        }
        return currentTimeMillis;
    }

    public static synchronized void start() {
        synchronized (TimerRecorder.class) {
            a = System.currentTimeMillis();
        }
    }
}
