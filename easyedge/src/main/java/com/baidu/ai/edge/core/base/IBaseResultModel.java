package com.baidu.ai.edge.core.base;

public interface IBaseResultModel {
    float getConfidence();

    String getLabel();

    int getLabelIndex();

    void setConfidence(float f);

    void setLabel(String str);

    void setLabelIndex(int i);
}
