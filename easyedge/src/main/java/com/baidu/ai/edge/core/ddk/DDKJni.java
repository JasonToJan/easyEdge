package com.baidu.ai.edge.core.ddk;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.ISDKJni;
import com.baidu.ai.edge.core.base.JniParam;
import com.baidu.ai.edge.core.classify.ClassificationResultModel;
import java.io.IOException;
import java.util.List;

public class DDKJni implements ISDKJni {
    private static CallException a;

    public static CallException a() {
        return a;
    }

    public static native String activate(Context context, AssetManager assetManager, JniParam jniParam) throws BaseException, IOException;

    public static boolean b() throws CallException {
        try {
            System.loadLibrary("edge-ddk");
            return true;
        } catch (Throwable th) {
            a = new CallException(3000, "加载DDK so文件失败", th);
            CallException callException = a;
        }
        return false;
    }

    public static native void deactivateInstance(Context context);

    public static native float[] getPixels(Bitmap bitmap, float[] fArr, float[] fArr2, boolean z, boolean z2);

    public static native int loadMixModelSync(Context context, AssetManager assetManager, JniParam jniParam);

    public static native float[] runMixModelDetectSync(DDKModelInfo dDKModelInfo, float[] fArr) throws BaseException;

    public static native List<ClassificationResultModel> runMixModelSync(DDKModelInfo dDKModelInfo, float[] fArr) throws BaseException;

    public static native int unloadMixModelSync();

    public native String getStatJson(String str);
}
