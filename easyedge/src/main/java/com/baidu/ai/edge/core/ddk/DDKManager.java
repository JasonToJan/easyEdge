package com.baidu.ai.edge.core.ddk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
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
import java.util.List;

public class DDKManager extends BaseManager implements ClassifyInterface, DetectInterface {
    private int h = 0;
    private DDKConfig i;

    public DDKManager(Context context, DDKConfig dDKConfig, String str) throws BaseException {
        super(context, new DDKJni(), dDKConfig, str);
        int i;
        this.i = dDKConfig;
        if (DDKJni.a() != null) {
            i = -1;
        } else {
            loadModelFromAssets(this.d);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Build.HARDWARE: ");
            stringBuilder.append(Build.HARDWARE);
            Log.i("DDKManager", stringBuilder.toString());
            i = 2;
        }
        this.h = i;
    }

    private List<ClassificationResultModel> a(float[] fArr) throws BaseException {
        return DDKJni.runMixModelSync(new DDKModelInfo(this.i.getImageHeight(), this.i.getImageWidth(), this.i.getCateNum()), fArr);
    }

    private List<ClassificationResultModel> c(Bitmap bitmap, float f, e eVar) throws BaseException {
        if (this.h == 2) {
            TimerRecorder.start();
            float[] pixels = DDKJni.getPixels(ImageUtil.resize(bitmap, this.i.getImageWidth(), this.i.getImageHeight()), this.i.getImgMeans(), this.i.getScales(), this.i.isHWC(), this.i.isRGB());
            long end = TimerRecorder.end();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[stat] preprocess time:");
            stringBuilder.append(end);
            String str = "DDKManager";
            Log.i(str, stringBuilder.toString());
            TimerRecorder.start();
            List a = a(pixels);
            long end2 = TimerRecorder.end();
            stringBuilder = new StringBuilder();
            stringBuilder.append("[stat] forward time:");
            stringBuilder.append(end2);
            Log.i(str, stringBuilder.toString());
            TimerRecorder.start();
            List<ClassificationResultModel> arrayList = new ArrayList();
            for (int i = 0; i < a.size(); i++) {
                ClassificationResultModel classificationResultModel = (ClassificationResultModel) a.get(i);
                if (classificationResultModel.getConfidence() >= f) {
                    classificationResultModel.setLabel(this.i.getLabels()[classificationResultModel.getLabelIndex()]);
                    arrayList.add(classificationResultModel);
                }
            }
            long end3 = TimerRecorder.end();
            if (eVar != null) {
                eVar.setPreprocessTime(end);
                eVar.setForwardTime(end2);
                eVar.setPostprocessTime(end3);
                eVar.setResultModel(arrayList);
            }
            c();
            return arrayList;
        }
        throw DDKJni.a();
    }

    private List<DetectionResultModel> d(Bitmap bitmap, float f, e eVar) throws BaseException {
        e eVar2 = eVar;
        TimerRecorder.start();
        float[] pixels = DDKJni.getPixels(ImageUtil.resize(bitmap, this.i.getImageWidth(), this.i.getImageHeight()), this.i.getImgMeans(), this.i.getScales(), this.i.isHWC(), this.i.isRGB());
        DDKModelInfo dDKModelInfo = new DDKModelInfo(this.i.getImageHeight(), this.i.getImageWidth(), this.i.getCateNum());
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        TimerRecorder.start();
        long end = TimerRecorder.end();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[stat] detect preprocessTime time:");
        stringBuilder.append(end);
        String str = "DDKManager";
        Log.i(str, stringBuilder.toString());
        TimerRecorder.start();
        pixels = DDKJni.runMixModelDetectSync(dDKModelInfo, pixels);
        long end2 = TimerRecorder.end();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[stat] forwardTime time:");
        stringBuilder2.append(end2);
        Log.i(str, stringBuilder2.toString());
        TimerRecorder.start();
        List<DetectionResultModel> arrayList = new ArrayList();
        int i = 0;
        while (i < pixels.length) {
            int i2;
            float f2 = pixels[i + 4];
            if (f2 < f) {
                i2 = height;
            } else {
                float f3 = (float) width;
                float f4 = (float) height;
                i2 = height;
                Rect rect = new Rect((int) (pixels[i] * f3), (int) (pixels[i + 1] * f4), (int) (f3 * pixels[i + 2]), (int) (f4 * pixels[i + 3]));
                int i3 = (int) pixels[i + 5];
                DetectionResultModel detectionResultModel = new DetectionResultModel();
                detectionResultModel.setBounds(rect);
                detectionResultModel.setConfidence(f2);
                detectionResultModel.setLabelIndex(i3);
                detectionResultModel.setLabel(this.i.getLabels()[i3]);
                arrayList.add(detectionResultModel);
            }
            i += 6;
            height = i2;
        }
        long end3 = TimerRecorder.end();
        if (eVar2 != null) {
            eVar2.setPreprocessTime(end);
            eVar2.setForwardTime(end2);
            eVar2.setPostprocessTime(end3);
            eVar2.setResultModel(arrayList);
        }
        return arrayList;
    }

    protected float[] a(Bitmap bitmap, JniParam jniParam, int i) {
        return new float[0];
    }

    public List<ClassificationResultModel> classify(Bitmap bitmap) throws BaseException {
        return classify(bitmap, this.i.getRecommendedConfidence());
    }

    public List<ClassificationResultModel> classify(Bitmap bitmap, float f) throws BaseException {
        return c(bitmap, f, null);
    }

    public IStatisticsResultModel classifyPro(Bitmap bitmap) throws BaseException {
        e eVar = new e();
        c(bitmap, this.i.getRecommendedConfidence(), eVar);
        return eVar;
    }

    public void destroy() throws BaseException {
        int unloadMixModelSync = DDKJni.unloadMixModelSync();
        DDKJni.deactivateInstance(this.b);
        if (unloadMixModelSync == 0) {
            super.destroy();
            return;
        }
        throw new BaseException(Consts.EC_UNLOAD_DDK_MODEL_FAIL, "卸载DDK模型失败");
    }

    public List<DetectionResultModel> detect(Bitmap bitmap) throws BaseException {
        return detect(bitmap, this.i.getRecommendedConfidence());
    }

    public List<DetectionResultModel> detect(Bitmap bitmap, float f) throws BaseException {
        return d(bitmap, f, null);
    }

    public IStatisticsResultModel detectPro(Bitmap bitmap) throws BaseException {
        e eVar = new e();
        d(bitmap, this.i.getRecommendedConfidence(), eVar);
        return eVar;
    }

    protected void e() throws CallException {
        DDKJni.b();
    }

    public void loadModelFromAssets(String str) throws BaseException {
        JniParam b = b();
        if (this.i.getModelFileAssetPath() != null) {
            b.put("modelFileAssetPath", this.i.getModelFileAssetPath());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("load 200 model file: ");
            stringBuilder.append(this.i.getModelFileAssetPath());
            Log.i("DDKManager", stringBuilder.toString());
            Context context = this.b;
            if (DDKJni.loadMixModelSync(context, context.getAssets(), b) != 0) {
                throw new BaseException(Consts.EC_LOAD_DDK_MODEL_FAIL, "加载DDK模型文件失败");
            }
            return;
        }
        throw new BaseException(Consts.EC_LOAD_DDK_MODEL_FAIL, "该模型在您的设备上不可用");
    }
}
