package com.baidu.ai.edge.core.base;

import java.util.List;

public class e implements IStatisticsResultModel {
    private List<? extends IBaseResultModel> a;
    private long b = 0;
    private long c = 0;
    private long d = 0;

    public long getForwardTime() {
        return this.c;
    }

    public long getPostprocessTime() {
        return this.d;
    }

    public long getPreprocessTime() {
        return this.b;
    }

    public List<? extends IBaseResultModel> getResultModel() {
        return this.a;
    }

    public void setForwardTime(long j) {
        this.c = j;
    }

    public void setPostprocessTime(long j) {
        this.d = j;
    }

    public void setPreprocessTime(long j) {
        this.b = j;
    }

    public void setResultModel(List<? extends IBaseResultModel> list) {
        this.a = list;
    }
}
