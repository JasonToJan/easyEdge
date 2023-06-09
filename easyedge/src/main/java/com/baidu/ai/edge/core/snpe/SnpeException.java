package com.baidu.ai.edge.core.snpe;

import com.baidu.ai.edge.core.base.BaseException;

public class SnpeException extends BaseException {
    public SnpeException(int i, String str) {
        super(i, str);
    }

    public SnpeException(int i, String str, Throwable th) {
        super(i, str, th);
    }
}
