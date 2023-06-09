package com.baidu.ai.edge.core.detect;

import com.baidu.ai.edge.core.base.BaseException;

public class DetectException extends BaseException {
    public DetectException(int i, String str) {
        super(i, str);
    }

    public DetectException(int i, String str, Throwable th) {
        super(i, str, th);
    }
}
