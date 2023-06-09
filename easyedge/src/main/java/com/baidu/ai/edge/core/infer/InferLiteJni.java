package com.baidu.ai.edge.core.infer;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.ISDKJni;
import com.baidu.ai.edge.core.base.JniParam;
import java.io.IOException;
import java.util.ArrayList;

public class InferLiteJni implements ISDKJni {
    public static boolean a = false;
    private static CallException b;

    public static void a(int i) throws CallException {
        if (i == 0) {
            System.loadLibrary("edge-infer");
        } else if (i == 3) {
            try {
                if (!a) {
                    System.loadLibrary("edge-infer-gpu");
                    a = true;
                }
            } catch (Throwable th) {
                b = new CallException(3000, "加载paddle so文件失败", th);
                CallException callException = b;
            }
        }
    }

    public static boolean a() throws CallException {
        a(3);
        return checkOpenclSupport();
    }

    public static native String activate(Context context, AssetManager assetManager, JniParam jniParam) throws BaseException, IOException;

    public static native boolean checkOpenclSupport();

    public static native boolean clear(long j);

    public static native void deactivateInstance(Context context);

    public static native float[] getPixels(Bitmap bitmap, float[] fArr, float[] fArr2, boolean z, boolean z2, int i);

    public static native long loadCombinedMemoryNB(Context context, AssetManager assetManager, JniParam jniParam);

    public static native long loadCombinedMemoryUC(Context context, AssetManager assetManager, JniParam jniParam);

    public static native float[] predictImage(long j, float[] fArr, float[] fArr2, int i) throws BaseException;

    public static native float[] predictImageOcr(long j, float[] fArr, float[] fArr2, Bitmap bitmap) throws BaseException;

    public static native float[] predictImageOcrNew(long j, Bitmap bitmap, JniParam jniParam) throws BaseException;

    public static native ArrayList predictImageSegment(long j, float[] fArr, float[] fArr2, float f, float f2) throws BaseException;

    public static native ArrayList predictImageSegmentNew(long j, Bitmap bitmap, JniParam jniParam) throws BaseException;

    public static native float[] predictNew(long j, Bitmap bitmap, JniParam jniParam) throws BaseException;

    public native String getStatJson(String str);
}
