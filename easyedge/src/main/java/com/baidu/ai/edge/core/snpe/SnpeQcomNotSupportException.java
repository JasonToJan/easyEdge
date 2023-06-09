package com.baidu.ai.edge.core.snpe;

import com.baidu.ai.edge.core.base.CallException;

public class SnpeQcomNotSupportException extends CallException {
    public SnpeQcomNotSupportException(int i, String str) {
        super(i, str);
    }

    public SnpeQcomNotSupportException(int i, String str, Throwable th) {
        super(i, str, th);
    }
}
