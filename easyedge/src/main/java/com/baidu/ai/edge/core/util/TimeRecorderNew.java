package com.baidu.ai.edge.core.util;

public class TimeRecorderNew {
    private long a;

    public TimeRecorderNew() {
        restart();
    }

    public long checkpoint() {
        return checkpoint(System.currentTimeMillis());
    }

    public long checkpoint(long j) {
        long j2 = j - this.a;
        this.a = j;
        return j2;
    }

    public long end() {
        return System.currentTimeMillis() - this.a;
    }

    public void restart() {
        this.a = System.currentTimeMillis();
    }
}
