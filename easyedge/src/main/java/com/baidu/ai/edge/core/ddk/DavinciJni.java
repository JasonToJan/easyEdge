package com.baidu.ai.edge.core.ddk;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.ISDKJni;
import com.baidu.ai.edge.core.base.JniParam;
import java.io.IOException;

public class DavinciJni implements ISDKJni {
    private static CallException a;

    public static void a() throws CallException {
        try {
            System.loadLibrary("hiai-500");
            System.loadLibrary("edge-davinci");
        } catch (Throwable th) {
            a = new CallException(3000, "加载DDK-Davinci so文件失败", th);
            CallException callException = a;
        }
    }

    public static native String activate(Context context, AssetManager assetManager, JniParam jniParam) throws BaseException, IOException;

    public static native void deactivateInstance(Context context);

    public static native long loadModelSync(Context context, AssetManager assetManager, JniParam jniParam) throws BaseException;

    public static native float[] runModelSync(long j, JniParam jniParam, Bitmap bitmap) throws BaseException;

    public static native int unloadModelSync(long j);

    public native String getStatJson(String str);
}
