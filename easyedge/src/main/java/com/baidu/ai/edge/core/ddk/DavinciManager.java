package com.baidu.ai.edge.core.ddk;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.BaseManager;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.Consts;
import com.baidu.ai.edge.core.base.IStatisticsResultModel;
import com.baidu.ai.edge.core.base.JniParam;
import com.baidu.ai.edge.core.base.e;
import com.baidu.ai.edge.core.classify.ClassificationResultModel;
import com.baidu.ai.edge.core.classify.ClassifyInterface;
import com.baidu.ai.edge.core.detect.DetectInterface;
import com.baidu.ai.edge.core.detect.DetectionResultModel;
import java.util.List;

public class DavinciManager extends BaseManager implements ClassifyInterface, DetectInterface {
    private static volatile boolean j = false;
    private final DDKDaVinciConfig h;
    private long i;

    public DavinciManager(Context context, DDKDaVinciConfig dDKDaVinciConfig, String str) throws BaseException {
        super(context, new DavinciJni(), dDKDaVinciConfig, str);
        synchronized (DavinciManager.class) {
            if (j) {
                throw new CallException(Consts.EC_BASE_MANAGER_MULTI_INSTANCES, "only one active instance of DavinciManager is allowed, please destory() the old one");
            }
            j = true;
        }
        this.h = dDKDaVinciConfig;
        dDKDaVinciConfig.setAutoCheckNpu(true);
        dDKDaVinciConfig.isSupportDavinciNpu();
        if (TextUtils.isEmpty(dDKDaVinciConfig.getModelFileAssetPath())) {
            throw new CallException(Consts.EC_BASE_DETECT_MANAGER_ASSET_MODEL_NULL, "model asset file path is NULL");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getModelFileAssetPath: ");
        stringBuilder.append(dDKDaVinciConfig.getModelFileAssetPath());
        Log.i("DavinciManager", stringBuilder.toString());
        f();
    }

    private List<ClassificationResultModel> c(Bitmap bitmap, float f, e eVar) throws BaseException {
        return a(bitmap, f, eVar);
    }

    private List<DetectionResultModel> d(Bitmap bitmap, float f, e eVar) throws BaseException {
        return b(bitmap, f, eVar);
    }

    private void f() throws BaseException {
        JniParam b = b();
        b.put("modelName", (Object) "params");
        Context context = this.b;
        long loadModelSync = DavinciJni.loadModelSync(context, context.getAssets(), b);
        if (loadModelSync != 0) {
            this.i = loadModelSync;
            return;
        }
        throw new BaseException(Consts.EC_LOAD_DAVINCI_MODEL_FAILED, "加载DDK-Davinci模型文件失败");
    }

    protected float[] a(Bitmap bitmap, JniParam jniParam, int i) throws BaseException {
        jniParam.put("modelName", (Object) "params");
        return DavinciJni.runModelSync(this.i, jniParam, bitmap);
    }

    public List<ClassificationResultModel> classify(Bitmap bitmap) throws BaseException {
        return classify(bitmap, this.h.getRecommendedConfidence());
    }

    public List<ClassificationResultModel> classify(Bitmap bitmap, float f) throws BaseException {
        return c(bitmap, f, null);
    }

    public IStatisticsResultModel classifyPro(Bitmap bitmap) throws BaseException {
        e eVar = new e();
        c(bitmap, this.h.getRecommendedConfidence(), eVar);
        return eVar;
    }

    public void destroy() throws BaseException {
        a();
        j = false;
        DavinciJni.unloadModelSync(this.i);
        DavinciJni.deactivateInstance(this.b);
        this.i = 0;
        super.destroy();
    }

    public List<DetectionResultModel> detect(Bitmap bitmap) throws BaseException {
        return detect(bitmap, this.h.getRecommendedConfidence());
    }

    public List<DetectionResultModel> detect(Bitmap bitmap, float f) throws BaseException {
        return d(bitmap, f, null);
    }

    public IStatisticsResultModel detectPro(Bitmap bitmap) throws BaseException {
        e eVar = new e();
        d(bitmap, this.h.getRecommendedConfidence(), eVar);
        return eVar;
    }

    protected void e() throws CallException {
        DavinciJni.a();
    }
}
