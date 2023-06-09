package com.baidu.ai.edge.core.base;

public interface IBaseConfig {
    String getAuthDomain();

    long getAuthInterval();

    String getAuthMode();

    String getDeviceLicenseUri();

    int getMid();

    String getModelFileAssetPath();

    String getProduct();

    int getRid();

    String getSoc();

    String getUserDeviceId();

    boolean isAcceleration();

    void setModelFileAssetPath(String str);
}
