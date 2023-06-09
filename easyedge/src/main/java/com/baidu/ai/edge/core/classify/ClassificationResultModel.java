package com.baidu.ai.edge.core.classify;

import com.baidu.ai.edge.core.base.BaseResultModel;

public class ClassificationResultModel extends BaseResultModel implements IClassificationResultModel {
    public ClassificationResultModel(int i, float f) {
        super("not loaded", f);
        this.a = i;
    }

    public ClassificationResultModel(String str, float f) {
        super(str, f);
    }
}
