package com.baidu.ai.edge.core.classify;

import com.baidu.ai.edge.core.base.BaseException;

public class ClassifyException extends BaseException {
    public ClassifyException(int i, String str) {
        super(i, str);
    }

    public ClassifyException(int i, String str, Throwable th) {
        super(i, str, th);
    }
}
