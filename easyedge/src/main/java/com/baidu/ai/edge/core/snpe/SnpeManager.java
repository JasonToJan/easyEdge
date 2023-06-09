package com.baidu.ai.edge.core.snpe;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.BaseManager;
import com.baidu.ai.edge.core.base.BaseResultModel;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.Consts;
import com.baidu.ai.edge.core.base.IStatisticsResultModel;
import com.baidu.ai.edge.core.base.JniParam;
import com.baidu.ai.edge.core.base.e;
import com.baidu.ai.edge.core.classify.ClassificationResultModel;
import com.baidu.ai.edge.core.classify.ClassifyInterface;
import com.baidu.ai.edge.core.detect.DetectInterface;
import com.baidu.ai.edge.core.detect.DetectionResultModel;
import com.baidu.ai.edge.core.util.ImageUtil;
import com.baidu.ai.edge.core.util.TimerRecorder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SnpeManager extends BaseManager implements ClassifyInterface, DetectInterface {
    private static volatile boolean j = false;
    private static ArrayList<Integer> k;
    private SnpeConfig h;
    private long i;

    public SnpeManager(Context context, SnpeConfig snpeConfig, String str) throws BaseException {
        super(context, new SnpeJni(), snpeConfig, str);
        StringBuilder stringBuilder;
        if (snpeConfig.isAutocheckQcom()) {
            if ("qcom".equalsIgnoreCase(Build.HARDWARE)) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("cpu support : ");
                stringBuilder.append(Build.HARDWARE);
                Log.i("SnpeManager", stringBuilder.toString());
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Build.HARDWARE is not qcom;");
                stringBuilder2.append(Build.HARDWARE);
                throw new SnpeQcomNotSupportException(Consts.EC_SNPE_NOT_QCOM, stringBuilder2.toString());
            }
        }
        synchronized (SnpeManager.class) {
            if (j) {
                throw new CallException(Consts.EC_BASE_MANAGER_MULTI_INSTANCES, "only one active instance of SnpeManager is allowed, please destory() the old one");
            }
            getAvailableRuntimes(context);
            j = true;
        }
        int i = -1;
        for (int i2 : snpeConfig.getSnpeRuntimesOrder()) {
            if (k.contains(Integer.valueOf(i2))) {
                i = i2;
                break;
            }
        }
        if (i >= 0) {
            this.h = snpeConfig;
            JniParam b = b();
            b.put("runtime", (long) i);
            this.i = SnpeJni.a(context, context.getAssets(), b);
            return;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("your allowed runtimes is:");
        stringBuilder.append(Arrays.toString(snpeConfig.getSnpeRuntimesOrder()));
        throw new SnpeQcomNotSupportException(Consts.EC_SNPE_RUNTIME_ALLOWED_NONE, stringBuilder.toString());
    }

    private List<ClassificationResultModel> a(float[] fArr, float f, String[] strArr) {
        List<ClassificationResultModel> arrayList = new ArrayList(fArr.length / 2);
        for (int i = 0; i < fArr.length / 2; i++) {
            int i2 = i * 2;
            int round = Math.round(fArr[i2]);
            float f2 = fArr[i2 + 1];
            if (f2 >= f) {
                String str;
                if (round < strArr.length) {
                    str = strArr[round];
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UNKNOWN:");
                    stringBuilder.append(round);
                    str = stringBuilder.toString();
                }
                ClassificationResultModel classificationResultModel = new ClassificationResultModel(str, f2);
                classificationResultModel.setLabelIndex(round);
                arrayList.add(classificationResultModel);
            }
        }
        return arrayList;
    }

    private List<ClassificationResultModel> c(Bitmap bitmap, float f, IStatisticsResultModel eVar) throws BaseException {
        a();
        BaseConfig baseConfig = this.h;
        TimerRecorder.start();
        float[] pixels = SnpeJni.getPixels(ImageUtil.resize(bitmap, baseConfig.getImageWidth(), baseConfig.getImageHeight()), baseConfig.getImgMeans(), baseConfig.getScales(), baseConfig.isHWC(), baseConfig.isRGB());
        long end = TimerRecorder.end();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[stat] preprocess time:");
        stringBuilder.append(end);
        String str = "SnpeManager";
        Log.i(str, stringBuilder.toString());
        TimerRecorder.start();
        pixels = SnpeJni.a(this.i, pixels, 0);
        long end2 = TimerRecorder.end();
        TimerRecorder.start();
        List<ClassificationResultModel> a = a(pixels, f, baseConfig.getLabels());
        long end3 = TimerRecorder.end();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[stat] forward time:");
        stringBuilder2.append(end2);
        Log.i(str, stringBuilder2.toString());
        if (eVar != null) {
            eVar.setPreprocessTime(end);
            eVar.setForwardTime(end2);
            eVar.setPostprocessTime(end3);
            eVar.setResultModel(a);
        }
        c();
        return a;
    }

    private List<DetectionResultModel> d(Bitmap bitmap, float f, IStatisticsResultModel eVar) throws BaseException {
        a();
        BaseConfig baseConfig = this.h;
        TimerRecorder.start();
        float[] pixels = SnpeJni.getPixels(ImageUtil.resize(bitmap, baseConfig.getImageWidth(), baseConfig.getImageHeight()), baseConfig.getImgMeans(), baseConfig.getScales(), baseConfig.isHWC(), baseConfig.isRGB());
        long end = TimerRecorder.end();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[stat] preprocess time:");
        stringBuilder.append(end);
        String str = "SnpeManager";
        Log.i(str, stringBuilder.toString());
        TimerRecorder.start();
        float[] execute = SnpeJni.execute(this.i, pixels, 1, f);
        long end2 = TimerRecorder.end();
        TimerRecorder.start();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("result ");
        stringBuilder2.append(execute.length);
        Log.i(str, stringBuilder2.toString());
        List<DetectionResultModel> a = a(execute, this.h.getLabels(), bitmap.getWidth(), bitmap.getHeight());
        long end3 = TimerRecorder.end();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("[stat] forward time:");
        stringBuilder3.append(end2);
        Log.i(str, stringBuilder3.toString());
        if (eVar != null) {
            eVar.setPreprocessTime(end);
            eVar.setForwardTime(end2);
            eVar.setPostprocessTime(end3);
            eVar.setResultModel(a);
        }
        c();
        return a;
    }

    public static List<Integer> getAvailableRuntimes(Context context) {
        if (k == null) {
            SnpeJni.setDspRuntimePath(context.getApplicationInfo().nativeLibraryDir);
            k = SnpeJni.a();
        }
        return new ArrayList(k);
    }

    protected float[] a(Bitmap bitmap, JniParam jniParam, int i) {
        return new float[0];
    }

    public List<ClassificationResultModel> classify(Bitmap bitmap) throws BaseException {
        return classify(bitmap, this.h.getRecommendedConfidence());
    }

    public List<ClassificationResultModel> classify(Bitmap bitmap, float f) throws BaseException {
        return c(bitmap, f, null);
    }

    public IStatisticsResultModel classifyPro(Bitmap bitmap) throws BaseException {
        IStatisticsResultModel eVar = new e();
        c(bitmap, this.h.getRecommendedConfidence(), eVar);
        return eVar;
    }

    public void destroy() throws BaseException {
        a();
        long j = this.i;
        if (j != 0) {
            SnpeJni.destory(j);
            SnpeJni.deactivateInstance(this.b);
        }
        super.destroy();
    }

    public List<DetectionResultModel> detect(Bitmap bitmap) throws BaseException {
        return detect(bitmap, this.h.getRecommendedConfidence());
    }

    public List<DetectionResultModel> detect(Bitmap bitmap, float f) throws BaseException {
        return d(bitmap, f, null);
    }

    public IStatisticsResultModel detectPro(Bitmap bitmap) throws BaseException {
        IStatisticsResultModel eVar = new e();
        d(bitmap, this.h.getRecommendedConfidence(), eVar);
        return eVar;
    }

    protected void e() throws CallException {
        SnpeJni.b();
    }
}
