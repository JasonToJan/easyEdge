package com.baidu.ai.edge.core.snpe;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.ISDKJni;
import com.baidu.ai.edge.core.base.JniParam;
import java.io.IOException;
import java.util.ArrayList;

public class SnpeJni implements ISDKJni, SnpeRuntimeInterface {
    private static CallException a;

    public static long a(Context context, AssetManager assetManager, JniParam jniParam) throws BaseException {
        try {
            return init(context, assetManager, jniParam);
        } catch (OutOfMemoryError e) {
            throw BaseException.transform(e);
        } catch (Exception e2) {
            throw BaseException.transform(e2, "");
        }
    }

    public static ArrayList<Integer> a() {
        int availableRuntimeInt = getAvailableRuntimeInt();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("runtime Int");
        stringBuilder.append(availableRuntimeInt);
        Log.i("SnpeJni", stringBuilder.toString());
        ArrayList<Integer> arrayList = new ArrayList();
        for (int i : SnpeRuntimeInterface.RUNTIMES) {
            if (((1 << i) & availableRuntimeInt) != 0) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        return arrayList;
    }

    public static float[] a(long j, float[] fArr, int i) throws BaseException {
        return execute(j, fArr, i, 0.0f);
    }

    public static native String activate(Context context, AssetManager assetManager, JniParam jniParam) throws BaseException, IOException;

    public static boolean b() throws CallException {
        try {
            System.loadLibrary("edge-snpe");
            return true;
        } catch (Throwable th) {
            a = new CallException(3000, "加载snpe so文件失败", th);
            CallException callException = a;
        }
        return false;
    }

    public static native void deactivateInstance(Context context);

    public static native void destory(long j);

    public static native float[] execute(long j, float[] fArr, int i, float f) throws BaseException;

    private static native int getAvailableRuntimeInt();

    public static native float[] getPixels(Bitmap bitmap, float[] fArr, float[] fArr2, boolean z, boolean z2);

    public static native long init(Context context, AssetManager assetManager, JniParam jniParam);

    public static native boolean setDspRuntimePath(String str);

    public native String getStatJson(String str);
}
