package com.baidu.ai.edge.core.snpe;

import com.baidu.ai.edge.core.base.IBaseConfig;

public interface ISnpeConfig extends IBaseConfig {
    int[] getSnpeRuntimesOrder();

    boolean isAutocheckQcom();
}
