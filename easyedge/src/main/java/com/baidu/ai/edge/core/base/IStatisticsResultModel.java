package com.baidu.ai.edge.core.base;

import java.util.List;

public interface IStatisticsResultModel {
    long getForwardTime();

    long getPostprocessTime();

    long getPreprocessTime();

    List<? extends IBaseResultModel> getResultModel();

    void setForwardTime(long j);

    void setPostprocessTime(long j);

    void setPreprocessTime(long j);

    void setResultModel(List<? extends IBaseResultModel> list);
}
