package com.baidu.ai.edge.core.infer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.BaseManager;
import com.baidu.ai.edge.core.base.BaseResultModel;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.Consts;
import com.baidu.ai.edge.core.base.IStatisticsResultModel;
import com.baidu.ai.edge.core.base.JniParam;
import com.baidu.ai.edge.core.base.d;
import com.baidu.ai.edge.core.base.e;
import com.baidu.ai.edge.core.classify.ClassificationResultModel;
import com.baidu.ai.edge.core.classify.ClassifyInterface;
import com.baidu.ai.edge.core.detect.DetectException;
import com.baidu.ai.edge.core.detect.DetectInterface;
import com.baidu.ai.edge.core.detect.DetectionResultModel;
import com.baidu.ai.edge.core.ocr.OcrInterface;
import com.baidu.ai.edge.core.ocr.OcrResultModel;
import com.baidu.ai.edge.core.pose.PoseInterface;
import com.baidu.ai.edge.core.pose.PoseResultModel;
import com.baidu.ai.edge.core.segment.SegmentInterface;
import com.baidu.ai.edge.core.segment.SegmentationResultModel;
import com.baidu.ai.edge.core.util.ImageUtil;
import com.baidu.ai.edge.core.util.TimeRecorderNew;
import com.baidu.ai.edge.core.util.TimerRecorder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class InferManager extends BaseManager implements ClassifyInterface, DetectInterface, SegmentInterface, OcrInterface, PoseInterface {
    private static volatile boolean k = false;
    private InferConfig h;
    private long i;
    private int j;

    class a implements Comparator<ClassificationResultModel> {
        a(InferManager inferManager) {
        }

        /* renamed from: a */
        public int compare(ClassificationResultModel classificationResultModel, ClassificationResultModel classificationResultModel2) {
            float confidence = classificationResultModel2.getConfidence() - classificationResultModel.getConfidence();
            return confidence > 0.0f ? 1 : confidence < 0.0f ? -1 : 0;
        }
    }

    public InferManager(Context context, InferConfig inferConfig, String str) throws BaseException {
        super(context, new InferLiteJni(), inferConfig, str);
        d preprocessConfig;
        int i;

        synchronized (InferManager.class) {
            if (k) {
                throw new CallException(Consts.EC_BASE_MANAGER_MULTI_INSTANCES, "only one active instance of InferManager is allowed, please destory() the old one");
            }
            k = true;
        }
        if (inferConfig.getNType() == 102 || inferConfig.getNType() == 900102 || inferConfig.getNType() == Consts.NTYPE_MRCNN_R50_VD_FPN) {
            preprocessConfig = inferConfig.getPreprocessConfig();
            i = 32;
        } else {
            preprocessConfig = inferConfig.getPreprocessConfig();
            i = 0;
        }
        preprocessConfig.a(i);
        this.h = inferConfig;
        if (inferConfig.getModelFileAssetPath() == null) {
            throw new CallException(Consts.EC_BASE_DETECT_MANAGER_ASSET_MODEL_NULL, "model asset file path is NULL");
        } else if (inferConfig.isOptModel() || inferConfig.getParamFileAssetPath() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("infer thread: ");
            stringBuilder.append(inferConfig.getThread());
            Log.i("InferManager", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("infer getParamFileAssetPath: ");
            stringBuilder.append(inferConfig.getParamFileAssetPath());
            Log.i("InferManager", stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("infer getModelFileAssetPath: ");
            stringBuilder.append(inferConfig.getModelFileAssetPath());
            Log.i("InferManager", stringBuilder.toString());
            a(context, this.d);
        } else {
            throw new CallException(Consts.EC_BASE_DETECT_MANAGER_ASSET_MODEL_NULL, "param asset file path is NULL");
        }
    }

    private OcrResultModel a(float[] fArr, int i, int i2, int i3) {
        int i4;
        OcrResultModel ocrResultModel = new OcrResultModel();
        ocrResultModel.setConfidence(fArr[i]);
        i++;
        for (int i5 = 0; i5 < i2; i5++) {
            i4 = (i5 * 2) + i;
            ocrResultModel.addPoints(Math.round(fArr[i4]), Math.round(fArr[i4 + 1]));
        }
        i += i2 * 2;
        StringBuffer stringBuffer = new StringBuffer(i3);
        String[] labels = this.h.getLabels();
        for (int i6 = 0; i6 < i3; i6++) {
            i4 = Math.round(fArr[i + i6]);
            ocrResultModel.addWordIndex(i4);
            stringBuffer.append(i4 < labels.length ? labels[i4] : "?");
            if (i4 >= labels.length) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("UNKNOWN index :");
                stringBuilder.append(i4);
                stringBuilder.append("; total:");
                stringBuilder.append(labels.length);
                Log.e("InferManager", stringBuilder.toString());
            }
        }
        ocrResultModel.setLabel(stringBuffer.toString());
        return ocrResultModel;
    }

    private ArrayList<OcrResultModel> a(float[] fArr, float f) {
        ArrayList<OcrResultModel> arrayList = new ArrayList();
        int i = 0;
        while (i < fArr.length) {
            int round = Math.round(fArr[i]);
            int round2 = Math.round(fArr[i + 1]);
            int i2 = i + 2;
            if (fArr[i2] >= f) {
                arrayList.add(a(fArr, i2, round, round2));
            }
            i += ((round * 2) + 3) + round2;
        }
        return arrayList;
    }

    private List<DetectionResultModel> a(Bitmap bitmap, float f, IStatisticsResultModel iStatisticsResultModel) throws BaseException {
        float[] pixels;
        int intValue;
        long j;
        InferManager inferManager = this;
        Bitmap bitmap2 = bitmap;
        float f2 = f;
        IStatisticsResultModel iStatisticsResultModel2 = iStatisticsResultModel;
        a();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("detect confidence: ");
        stringBuilder.append(f2);
        String str = "InferManager detect";
        Log.i(str, stringBuilder.toString());
        TimerRecorder.start();
        float[] fArr = new float[4];
        fArr[0] = 1.0f;
        fArr[1] = 3.0f;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[preprocess] detect target: ");
        stringBuilder2.append(inferManager.h.getTargetSize());
        Log.i(str, stringBuilder2.toString());
        Pair pair = null;
        if (inferManager.h.getNType() == 102 || inferManager.h.getNType() == 900102) {
            pair = ImageUtil.resizeTarget(bitmap2, inferManager.h.getTargetSize(), inferManager.h.getMaxSize());
            fArr[2] = (float) ((Bitmap) pair.first).getHeight();
            fArr[3] = (float) ((Bitmap) pair.first).getWidth();
            pixels = InferLiteJni.getPixels((Bitmap) pair.first, inferManager.h.getImgMeans(), inferManager.h.getScales(), inferManager.h.isHWC(), inferManager.h.isRGB(), 32);
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("");
            stringBuilder2.append(pixels.length);
            Log.i("pixel size", stringBuilder2.toString());
        } else if (inferManager.h.getNType() == 11002) {
            Pair calcShrinkSize = ImageUtil.calcShrinkSize(bitmap.getWidth(), bitmap.getHeight());
            intValue = ((Integer) calcShrinkSize.first).intValue();
            int intValue2 = ((Integer) calcShrinkSize.second).intValue();
            pixels = InferLiteJni.getPixels(ImageUtil.resize(bitmap2, intValue, intValue2), inferManager.h.getImgMeans(), inferManager.h.getScales(), inferManager.h.isHWC(), inferManager.h.isRGB(), 0);
            fArr[2] = (float) intValue2;
            fArr[3] = (float) intValue;
        } else {
            pixels = InferLiteJni.getPixels(ImageUtil.resize(bitmap2, inferManager.h.getImageWidth(), inferManager.h.getImageHeight()), inferManager.h.getImgMeans(), inferManager.h.getScales(), inferManager.h.isHWC(), inferManager.h.isRGB(), 0);
            fArr[2] = (float) inferManager.h.getImageHeight();
            fArr[3] = (float) inferManager.h.getImageWidth();
        }
        long end = TimerRecorder.end();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[stat] preprocess time:");
        stringBuilder2.append(end);
        Log.i(str, stringBuilder2.toString());
        TimerRecorder.start();
        fArr = InferLiteJni.predictImage(inferManager.i, pixels, fArr, inferManager.h.getNType());
        long end2 = TimerRecorder.end();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("[stat] forward time:");
        stringBuilder3.append(end2);
        Log.i(str, stringBuilder3.toString());
        TimerRecorder.start();
        intValue = fArr.length / 6;
        List<DetectionResultModel> arrayList = new ArrayList(intValue);
        String[] labels = inferManager.h.getLabels();
        int i = 0;
        while (i < intValue) {
            int round;
            int i2;
            Pair pair2;
            int i3;
            float[] fArr2;
            int i4 = i * 6;
            int round2 = Math.round(fArr[i4 + 0]);
            int i5 = intValue;
            int round3;
            int round4;
            if (inferManager.h.getNType() == 101) {
                round3 = Math.round((fArr[i4 + 2] / ((float) inferManager.h.getImageWidth())) * ((float) bitmap.getWidth()));
                round4 = Math.round((fArr[i4 + 3] / ((float) inferManager.h.getImageHeight())) * ((float) bitmap.getHeight()));
                int round5 = Math.round((fArr[i4 + 4] / ((float) inferManager.h.getImageWidth())) * ((float) bitmap.getWidth()));
                round = Math.round((fArr[i4 + 5] / ((float) inferManager.h.getImageHeight())) * ((float) bitmap.getHeight()));
                i2 = round4;
                intValue = round5;
                pair2 = pair;
                i3 = round;
                round = round3;
            } else {
                float f3;
                if (inferManager.h.getNType() != 102 || pair == null) {
                    round = Math.round(fArr[i4 + 2] * ((float) bitmap.getWidth()));
                    round3 = Math.round(fArr[i4 + 3] * ((float) bitmap.getHeight()));
                    i2 = Math.round(fArr[i4 + 4] * ((float) bitmap.getWidth()));
                    f3 = fArr[i4 + 5];
                    round4 = i2;
                } else {
                    round = Math.round((fArr[i4 + 2] / ((float) ((Bitmap) pair.first).getWidth())) * ((float) bitmap.getWidth()));
                    round3 = Math.round((fArr[i4 + 3] / ((float) ((Bitmap) pair.first).getHeight())) * ((float) bitmap.getHeight()));
                    round4 = Math.round((fArr[i4 + 4] / ((float) ((Bitmap) pair.first).getWidth())) * ((float) bitmap.getWidth()));
                    f3 = fArr[i4 + 5] / ((float) ((Bitmap) pair.first).getHeight());
                }
                i2 = Math.round(f3 * ((float) bitmap.getHeight()));
                intValue = round4;
                pair2 = pair;
                i3 = i2;
                i2 = round3;
            }
            Pair pair3 = pair2;
            if (round2 >= 0) {
                j = end2;
                if (round2 >= labels.length) {
                    fArr2 = fArr;
                } else {
                    float f4 = fArr[i4 + 1];
                    if (f4 < f2) {
                        fArr2 = fArr;
                    } else {
                        fArr2 = fArr;
                        DetectionResultModel detectionResultModel = new DetectionResultModel(labels[round2], f4, new Rect(round, i2, intValue, i3));
                        detectionResultModel.setLabelIndex(round2);
                        arrayList.add(detectionResultModel);
                    }
                    i++;
                    inferManager = this;
                    bitmap2 = bitmap;
                    f2 = f;
                    fArr = fArr2;
                    intValue = i5;
                    pair = pair3;
                    end2 = j;
                }
            } else {
                fArr2 = fArr;
                j = end2;
            }
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append("label index out of bound , index : ");
            stringBuilder4.append(round2);
            stringBuilder4.append(" ,at :");
            stringBuilder4.append(i);
            Log.e("InferManager", stringBuilder4.toString());
            i++;
            inferManager = this;
            bitmap2 = bitmap;
            f2 = f;
            fArr = fArr2;
            intValue = i5;
            pair = pair3;
            end2 = j;
        }
        j = end2;
        long end3 = TimerRecorder.end();
        StringBuilder stringBuilder5 = new StringBuilder();
        stringBuilder5.append("[stat] postprocess time:");
        stringBuilder5.append(end3);
        Log.i(str, stringBuilder5.toString());
        if (iStatisticsResultModel2 != null) {
            iStatisticsResultModel2.setPreprocessTime(end);
            iStatisticsResultModel2.setForwardTime(j);
            iStatisticsResultModel2.setPostprocessTime(end3);
            iStatisticsResultModel2.setResultModel(arrayList);
        }
        c();
        return arrayList;
    }

    private List<ClassificationResultModel> a(float[] fArr, float f, String[] strArr) {
        List<ClassificationResultModel> arrayList = new ArrayList();
        for (int i = 0; i < fArr.length; i++) {
            String str;
            if (i < strArr.length) {
                str = strArr[i];
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("UnKnown:");
                stringBuilder.append(i);
                str = stringBuilder.toString();
            }
            ClassificationResultModel classificationResultModel = new ClassificationResultModel(str, fArr[i]);
            if (classificationResultModel.getConfidence() >= f) {
                classificationResultModel.setLabelIndex(i);
                arrayList.add(classificationResultModel);
            }
        }
        Collections.sort(arrayList, new a(this));
        return arrayList;
    }

    private List<PoseResultModel> a(float[] fArr, Bitmap bitmap) {
        List<PoseResultModel> arrayList = new ArrayList();
        boolean z = this.h.getNType() == 20041;
        int i = 0;
        while (i < fArr.length) {
            PoseResultModel poseResultModel = new PoseResultModel();
            poseResultModel.setLabelIndex(0);
            poseResultModel.setLabel(a(0));
            poseResultModel.setHasGroups(z);
            int i2 = i + 1;
            poseResultModel.setIndex(Math.round(fArr[i]));
            i = i2 + 1;
            poseResultModel.setGroupIndex(Math.round(fArr[i2]));
            i2 = i + 1;
            poseResultModel.setConfidence(fArr[i]);
            i = i2 + 1;
            int i3 = i + 1;
            poseResultModel.setPoint(new Point(Math.round(fArr[i2] * ((float) bitmap.getWidth())), Math.round(fArr[i] * ((float) bitmap.getHeight()))));
            List arrayList2 = new ArrayList();
            i2 = i3 + 1;
            i3 = Math.round(fArr[i3]);
            int i4 = i2;
            i2 = 0;
            while (i2 < i3) {
                PoseResultModel poseResultModel2 = new PoseResultModel();
                int i5 = i4 + 1;
                poseResultModel2.setIndex(Math.round(fArr[i4]));
                poseResultModel2.setGroupIndex(poseResultModel.getGroupIndex());
                i4 = i5 + 1;
                poseResultModel2.setConfidence(fArr[i5]);
                i5 = i4 + 1;
                int i6 = i5 + 1;
                poseResultModel2.setPoint(new Point(Math.round(fArr[i4] * ((float) bitmap.getWidth())), Math.round(fArr[i5] * ((float) bitmap.getHeight()))));
                arrayList2.add(poseResultModel2);
                i2++;
                i4 = i6;
            }
            poseResultModel.setPairs(arrayList2);
            arrayList.add(poseResultModel);
            i = i4;
        }
        return arrayList;
    }

    private void a(Context context, String str) throws BaseException {
        JniParam b = b();
        b.put("thread", (long) this.h.getThread());
        if (this.j == 3) {
            b.put("opencl_tune", Boolean.valueOf(((ArmGpuConfig) this.c).isOpenclTune()));
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("serial num length is ");
        stringBuilder.append(str == null ? null : Integer.valueOf(str.length()));
        str = stringBuilder.toString();
        String str2 = "InferManager";
        Log.i(str2, str);
        int modelType = this.h.getModelType();
        if (modelType == 100) {
            if (this.h.getExtraModelFilePath() != null) {
                b.put("extraModelFileAssetPath", this.h.getExtraModelFilePath());
            } else {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("extraModelFileAssetPath must be set for this model type :");
                stringBuilder2.append(modelType);
                throw new DetectException(Consts.EC_BASE_JNI_MODELFILE_READERROR, stringBuilder2.toString());
            }
        }
        try {
            long loadCombinedMemoryNB;
            if (this.h.isOptModel()) {
                loadCombinedMemoryNB = InferLiteJni.loadCombinedMemoryNB(context, context.getAssets(), b);
            } else {
                b.put("paramFileAssetPath", this.h.getParamFileAssetPath());
                loadCombinedMemoryNB = InferLiteJni.loadCombinedMemoryUC(context, context.getAssets(), b);
            }
            this.i = loadCombinedMemoryNB;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("loadCombinedMemory success isOptModel: ");
            stringBuilder3.append(this.h.isOptModel());
            Log.i(str2, stringBuilder3.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw BaseException.transform(e, "init models failed:");
        }
    }

    private List<PoseResultModel> b(Bitmap bitmap, float f, IStatisticsResultModel iStatisticsResultModel) throws BaseException {
        List<PoseResultModel> a = a(a(bitmap, f, iStatisticsResultModel, 402, new int[2]).b(), bitmap);
        long end = new TimeRecorderNew().end();
        if (iStatisticsResultModel != null) {
            iStatisticsResultModel.setPostprocessTime(end);
            iStatisticsResultModel.setResultModel(a);
        }
        return a;
    }

    private List<ClassificationResultModel> c(Bitmap bitmap, float f, e eVar) throws BaseException {
        a();
        float[] fArr = new float[]{1.0f, 3.0f, (float) this.h.getImageHeight(), (float) this.h.getImageWidth()};
        TimerRecorder.start();
        float[] pixels = InferLiteJni.getPixels(ImageUtil.resize(bitmap, this.h.getImageWidth(), this.h.getImageHeight()), this.h.getImgMeans(), this.h.getScales(), this.h.isHWC(), this.h.isRGB(), 0);
        long end = TimerRecorder.end();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[stat] preprocess time:");
        stringBuilder.append(end);
        String str = "InferManager classify";
        Log.i(str, stringBuilder.toString());
        TimerRecorder.start();
        pixels = InferLiteJni.predictImage(this.i, pixels, fArr, this.h.getNType());
        long end2 = TimerRecorder.end();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[stat] forward time:");
        stringBuilder2.append(end2);
        Log.i(str, stringBuilder2.toString());
        TimerRecorder.start();
        List<ClassificationResultModel> a = a(pixels, f, this.h.getLabels());
        long end3 = TimerRecorder.end();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("[stat] postprocessTime time:");
        stringBuilder3.append(end3);
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

    public static boolean isSupportOpencl() throws CallException {
        return InferLiteJni.a();
    }

    protected List<DetectionResultModel> a(Bitmap bitmap, float f) throws BaseException {
        return b(bitmap, f, null);
    }

    protected float[] a(Bitmap bitmap, JniParam jniParam, int i) throws BaseException {
        return InferLiteJni.predictNew(this.i, bitmap, jniParam);
    }

    public List<ClassificationResultModel> classifyOld(Bitmap bitmap, float f) throws BaseException {
        return c(bitmap, f, null);
    }

    public IStatisticsResultModel classifyPro(Bitmap bitmap) throws BaseException {
        return super.classifyPro(bitmap);
    }

    public e classifyProOld(Bitmap bitmap) throws BaseException {
        e eVar = new e();
        c(bitmap, this.h.getRecommendedConfidence(), eVar);
        return eVar;
    }

    public synchronized void destroy() throws BaseException {
        a();
        k = false;
        InferLiteJni.clear(this.i);
        InferLiteJni.deactivateInstance(this.b);
        this.i = 0;
        super.destroy();
    }

    public List<ClassificationResultModel> detectOld(Bitmap bitmap, float f) throws BaseException {
        return a(bitmap, f, null);
    }

    public IStatisticsResultModel detectPro(Bitmap bitmap) throws BaseException {
        return super.detectPro(bitmap);
    }

    public IStatisticsResultModel detectProOld(Bitmap bitmap) throws BaseException {
        IStatisticsResultModel eVar = new e();
        a(bitmap, this.h.getRecommendedConfidence(), eVar);
        return eVar;
    }

    protected void e() throws CallException {
        this.j = this.c instanceof ArmGpuConfig ? 3 : 0;
        InferLiteJni.a(this.j);
    }

    public List<OcrResultModel> ocr(Bitmap bitmap) throws BaseException {
        return ocr(bitmap, this.h.getRecommendedConfidence());
    }

    public List<OcrResultModel> ocr(Bitmap bitmap, float f) throws BaseException {
        return ocrInternal(bitmap, f, null);
    }

    public List<OcrResultModel> ocrInternal(Bitmap bitmap, float f, IStatisticsResultModel iStatisticsResultModel) throws BaseException {
        a();
        TimeRecorderNew timeRecorderNew = new TimeRecorderNew();
        JniParam a = a(bitmap, 100, f, new int[2]);
        float[] predictImageOcrNew = InferLiteJni.predictImageOcrNew(this.i, bitmap, a);
        long checkpoint = timeRecorderNew.checkpoint(a.getLong("preprocessEndTime"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[stat]preprocess time: ");
        stringBuilder.append(checkpoint);
        String str = "InferManager";
        Log.i(str, stringBuilder.toString());
        long checkpoint2 = timeRecorderNew.checkpoint();
        stringBuilder = new StringBuilder();
        stringBuilder.append("[stat]forward time: ");
        stringBuilder.append(checkpoint2);
        Log.i(str, stringBuilder.toString());
        List<OcrResultModel> a2 = a(predictImageOcrNew, f);
        long end = timeRecorderNew.end();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[stat] ocr postprocess time:");
        stringBuilder2.append(end);
        Log.i(str, stringBuilder2.toString());
        if (iStatisticsResultModel != null) {
            iStatisticsResultModel.setPreprocessTime(checkpoint);
            iStatisticsResultModel.setForwardTime(checkpoint2);
            iStatisticsResultModel.setPostprocessTime(end);
            iStatisticsResultModel.setResultModel(a2);
        }
        c();
        return a2;
    }

    public List<OcrResultModel> ocrInternalOld(Bitmap bitmap, float f, IStatisticsResultModel iStatisticsResultModel) throws BaseException {
        a();
        TimerRecorder.start();
        Bitmap resizeWithStep = ImageUtil.resizeWithStep(bitmap, this.h.getMaxSize(), 32);
        float[] pixels = InferLiteJni.getPixels(resizeWithStep, this.h.getImgMeans(), this.h.getScales(), this.h.isHWC(), this.h.isRGB(), 0);
        long end = TimerRecorder.end();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[stat] ocr preprocess time:");
        stringBuilder.append(end);
        String str = "InferManager";
        Log.i(str, stringBuilder.toString());
        TimerRecorder.start();
        float[] fArr = new float[]{1.0f, 3.0f, (float) resizeWithStep.getHeight(), (float) resizeWithStep.getWidth()};
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(pixels.length);
        stringBuilder2.append(" height ");
        stringBuilder2.append(resizeWithStep.getHeight());
        stringBuilder2.append(" ;width ");
        stringBuilder2.append(resizeWithStep.getWidth());
        Log.i("pixel size", stringBuilder2.toString());
        int pixel = bitmap.getPixel(bitmap.getWidth() - 1, bitmap.getHeight() - 1);
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("pixels ");
        stringBuilder3.append(pixel);
        String str2 = " ";
        stringBuilder3.append(str2);
        stringBuilder3.append(Color.red(pixel));
        stringBuilder3.append(str2);
        stringBuilder3.append(Color.blue(pixel));
        stringBuilder3.append(str2);
        stringBuilder3.append(Color.green(pixel));
        Log.i("Predictor", stringBuilder3.toString());
        float[] predictImageOcr = InferLiteJni.predictImageOcr(this.i, pixels, fArr, bitmap);
        long end2 = TimerRecorder.end();
        TimerRecorder.start();
        stringBuilder = new StringBuilder();
        stringBuilder.append("[stat] ocr forward time:");
        stringBuilder.append(end2);
        Log.i(str, stringBuilder.toString());
        List<OcrResultModel> a = a(predictImageOcr, f);
        long end3 = TimerRecorder.end();
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("[stat] ocr postprocess time:");
        stringBuilder4.append(end3);
        Log.i(str, stringBuilder4.toString());
        if (iStatisticsResultModel != null) {
            iStatisticsResultModel.setPreprocessTime(end);
            iStatisticsResultModel.setForwardTime(end2);
            iStatisticsResultModel.setPostprocessTime(end3);
            iStatisticsResultModel.setResultModel(a);
        }
        c();
        return a;
    }

    public List<OcrResultModel> ocrOld(Bitmap bitmap, float f) throws BaseException {
        return ocrInternalOld(bitmap, f, null);
    }

    public List<PoseResultModel> pose(Bitmap bitmap) throws BaseException {
        return a2(bitmap, 0.0f);
    }

    protected List<PoseResultModel> a2(Bitmap var1, float var2) throws BaseException {
        return this.b(var1, var2, (IStatisticsResultModel)null);
    }

    public e posePro(Bitmap bitmap) throws BaseException {
        e eVar = new e();
        b(bitmap, this.h.getRecommendedConfidence(), eVar);
        return eVar;
    }

    public List<SegmentationResultModel> segment(Bitmap bitmap) throws BaseException {
        return segment(bitmap, this.h.getRecommendedConfidence());
    }

    public List<SegmentationResultModel> segment(Bitmap bitmap, float f) throws BaseException {
        return segmentInternal(bitmap, f, null);
    }

    public List<SegmentationResultModel> segmentInternal(Bitmap bitmap, float f, e eVar) throws BaseException {
        a();
        TimeRecorderNew timeRecorderNew = new TimeRecorderNew();
        JniParam a = a(bitmap, this.c.getModelType(), f, null);
        List<SegmentationResultModel> predictImageSegmentNew = InferLiteJni.predictImageSegmentNew(this.i, bitmap, a);
        long checkpoint = timeRecorderNew.checkpoint(a.getLong("preprocessEndTime"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[stat]preprocess time: ");
        stringBuilder.append(checkpoint);
        String str = "InferManager";
        Log.i(str, stringBuilder.toString());
        long end = timeRecorderNew.end();
        stringBuilder = new StringBuilder();
        stringBuilder.append("[stat]forward time: ");
        stringBuilder.append(end);
        Log.i(str, stringBuilder.toString());
        String[] labels = this.h.getLabels();
        for (SegmentationResultModel segmentationResultModel : predictImageSegmentNew) {
            String stringBuilder2;
            int labelIndex = segmentationResultModel.getLabelIndex();
            if (labelIndex < 0 || labelIndex >= labels.length) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("UNKNOWN:");
                stringBuilder3.append(labelIndex);
                stringBuilder2 = stringBuilder3.toString();
            } else {
                stringBuilder2 = labels[labelIndex];
            }
            segmentationResultModel.setLabel(stringBuilder2);
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("segment result size ");
        stringBuilder.append(predictImageSegmentNew.size());
        Log.i("InferManager segment", stringBuilder.toString());
        if (eVar != null) {
            eVar.setPreprocessTime(checkpoint);
            eVar.setForwardTime(end);
            eVar.setResultModel(predictImageSegmentNew);
        }
        c();
        return predictImageSegmentNew;
    }

    public List<SegmentationResultModel> segmentInternalOld(Bitmap bitmap, float f, e eVar) throws BaseException {
        e eVar2 = eVar;
        float[] fArr = new float[4];
        Pair resizeTarget = ImageUtil.resizeTarget(bitmap, this.h.getTargetSize(), this.h.getMaxSize());
        Bitmap bitmap2 = (Bitmap) resizeTarget.first;
        float floatValue = ((Float) resizeTarget.second).floatValue();
        fArr[0] = 1.0f;
        fArr[1] = 3.0f;
        fArr[2] = (float) bitmap2.getHeight();
        fArr[3] = (float) bitmap2.getWidth();
        TimerRecorder.start();
        float[] pixels = InferLiteJni.getPixels(ImageUtil.resize(bitmap2, bitmap2.getWidth(), bitmap2.getHeight()), this.h.getImgMeans(), this.h.getScales(), this.h.isHWC(), this.h.isRGB(), this.h.getNType() == Consts.NTYPE_MRCNN_R50_VD_FPN ? 32 : 0);
        long end = TimerRecorder.end();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[stat] preprocess time:");
        stringBuilder.append(end);
        String str = "InferManager segment";
        Log.i(str, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("pixels：");
        stringBuilder.append(pixels.length);
        Log.i(str, stringBuilder.toString());
        TimerRecorder.start();
        List<SegmentationResultModel> predictImageSegment = InferLiteJni.predictImageSegment(this.i, pixels, fArr, floatValue, f);
        long end2 = TimerRecorder.end();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[stat] forward time:");
        stringBuilder2.append(end2);
        Log.i(str, stringBuilder2.toString());
        TimerRecorder.start();
        String[] labels = this.h.getLabels();
        for (SegmentationResultModel segmentationResultModel : predictImageSegment) {
            segmentationResultModel.setLabel(labels[segmentationResultModel.getLabelIndex()]);
        }
        long end3 = TimerRecorder.end();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("segment result size ");
        stringBuilder3.append(predictImageSegment.size());
        Log.i(str, stringBuilder3.toString());
        if (eVar2 != null) {
            eVar2.setPreprocessTime(end);
            eVar2.setForwardTime(end2);
            eVar2.setPostprocessTime(end3);
            eVar2.setResultModel(predictImageSegment);
        }
        c();
        return predictImageSegment;
    }

    public List<SegmentationResultModel> segmentOld(Bitmap bitmap, float f) throws BaseException {
        return segmentInternalOld(bitmap, f, null);
    }

    public e segmentPro(Bitmap bitmap) throws BaseException {
        e eVar = new e();
        segmentInternal(bitmap, this.h.getRecommendedConfidence(), eVar);
        return eVar;
    }
}
