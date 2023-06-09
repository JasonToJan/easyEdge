package com.baidu.ai.edge.core.base;

public class BaseException extends Exception {
    protected int a;

    public BaseException(int i, String str) {
        super(str);
        this.a = i;
    }

    public BaseException(int i, String str, Throwable th) {
        super(str, th);
        this.a = i;
    }

    public static BaseException transform(Exception exception, String str) {
        String message = exception.getMessage();
        int indexOf = message.indexOf(58);
        if (indexOf > 0) {
            int parseInt = Integer.parseInt(message.substring(0, indexOf));
            message = message.substring(indexOf + 1);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(message);
            return new BaseException(parseInt, stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str);
        stringBuilder2.append(exception.getMessage());
        return new BaseException(Consts.EC_BASE_JNI_UNKNOWN, stringBuilder2.toString(), exception);
    }

    public static BaseException transform(OutOfMemoryError outOfMemoryError) {
        return new BaseException(Consts.EC_BASE_JNI_OUT_OF_MEMORY, "SDKException: out of memory:", outOfMemoryError);
    }

    public int getErrorCode() {
        return this.a;
    }
}
