package com.baidu.ai.edge.core.base;

public class CallException extends BaseException {
    public CallException(int i, String str) {
        super(i, str);
    }

    public CallException(int i, String str, Throwable th) {
        super(i, str, th);
    }
}
