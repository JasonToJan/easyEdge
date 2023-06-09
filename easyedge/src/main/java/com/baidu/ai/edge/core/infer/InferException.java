package com.baidu.ai.edge.core.infer;

import com.baidu.ai.edge.core.base.BaseException;

public class InferException extends BaseException {
    public InferException(int i, String str) {
        super(i, str);
    }

    public InferException(int i, String str, Throwable th) {
        super(i, str, th);
    }
}
