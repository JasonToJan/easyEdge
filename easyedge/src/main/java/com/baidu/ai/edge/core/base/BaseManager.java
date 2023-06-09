package com.baidu.ai.edge.core.base;

import a.a.a.a.a.a.a;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import androidx.core.content.ContextCompat;

import com.baidu.ai.edge.core.classify.ClassificationResultModel;
import com.baidu.ai.edge.core.ddk.DDKManager;
import com.baidu.ai.edge.core.ddk.DavinciManager;
import com.baidu.ai.edge.core.detect.DetectionResultModel;
import com.baidu.ai.edge.core.infer.InferManager;
import com.baidu.ai.edge.core.pose.PoseResultModel;
import com.baidu.ai.edge.core.snpe.SnpeManager;
import com.baidu.ai.edge.core.util.ImageUtil;
import com.baidu.ai.edge.core.util.TimeRecorderNew;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseManager {
    public static final String VERSION = "0.10.7";
    protected ActivateManager a;
    protected Context b;
    protected BaseConfig c;
    protected String d;
    private a e;
    protected String f;
    private boolean g = false;

    public BaseManager(Context context, ISDKJni iSDKJni, BaseConfig baseConfig, String str) throws CallException {
        a(context);
        this.c = baseConfig;
        this.d = str;
        this.f = getClass().getSimpleName();
        boolean d = d();
        String str2 = "BaseManager";
        if (d) {
            this.d = null;
        } else if (str == null) {
            String str3 = "serial number is NULL";
            Log.e(str2, str3);
            throw new CallException(Consts.EC_OFFLINE_SERIAL_NULL_ERROR, str3);
        }
        this.b = context;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("new Manager with ");
        stringBuilder.append(baseConfig.getMid());
        stringBuilder.append(" ");
        stringBuilder.append(baseConfig.getRid());
        Log.i(str2, stringBuilder.toString());
        this.a = new ActivateManager(context, baseConfig);
        e();
        try {
            str = a(str, d);
            if (baseConfig.getRid() != -1 && baseConfig.getMid() != -1 && baseConfig.getModelEncValue() != 1200) {
                this.e = new a(context, iSDKJni, baseConfig, str);
                this.e.c();
            }
        } catch (Exception e) {
            throw new CallException(Consts.EC_OFFLINE_AUTH_ERROR, e.getMessage(), e.getCause());
        }
    }

    private String a(String str, boolean z) throws IOException, BaseException {
        ActivateManager activateManager;
        int i;
        if (this instanceof InferManager) {
            activateManager = this.a;
            i = 100;
        } else if (this instanceof SnpeManager) {
            activateManager = this.a;
            i = 101;
        } else if (this instanceof DDKManager) {
            activateManager = this.a;
            i = 102;
        } else if (!(this instanceof DavinciManager)) {
            return null;
        } else {
            activateManager = this.a;
            i = 104;
        }
        return activateManager.activate(str, i, z);
    }

    private void a(Context context) throws CallException {
        String[] strArr = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.READ_PHONE_STATE"};
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str = strArr[i];
            if (ContextCompat.checkSelfPermission(context, str) == 0) {
                i++;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Please allow permission:");
                stringBuilder.append(str);
                throw new CallException(1003, stringBuilder.toString());
            }
        }
        if (Build.VERSION.SDK_INT >= 30 && !Environment.isExternalStorageManager()) {
            throw new CallException(1003, "Please allow all files access:");
        }
    }

    protected JniParam a(Bitmap bitmap, int i, float f, int[] iArr) {
        JniParam jniParam = new JniParam();
        jniParam.put("product", this.c.j);
        jniParam.put("originWidth", (long) bitmap.getWidth());
        jniParam.put("originHeight", (long) bitmap.getHeight());
        jniParam.put("preprocessObj", a(bitmap, iArr, i));
        jniParam.put("modelType", (long) i);
        jniParam.put("nType", (long) this.c.getNType());
        jniParam.put("confidence", Float.valueOf(f));
        jniParam.put("extraDetection", this.c.getExtraDetectionJson());
        jniParam.put("classNum", (long) this.c.getLabels().length);
        return jniParam;
    }

    protected JniParam a(Bitmap bitmap, int[] iArr, int i) {
        Pair calcWithStep;
        d preprocessConfig = this.c.getPreprocessConfig();
        BaseConfig baseConfig = this.c;
        if (i == 100) {
            calcWithStep = ImageUtil.calcWithStep(bitmap, baseConfig.getMaxSize(), 32);
        } else {
            if (!(baseConfig.getNType() == 102 || this.c.getNType() == 900102 || this.c.getNType() == Consts.NTYPE_MRCNN_R50_VD_FPN)) {
                if (!"keep_ratio".equalsIgnoreCase(this.c.getPreprocessConfig().r())) {
                    if (this.c.getNType() == 11002) {
                        calcWithStep = ImageUtil.calcShrinkSize(bitmap.getWidth(), bitmap.getHeight());
                    } else {
                        calcWithStep = "keep_ratio2".equalsIgnoreCase(preprocessConfig.r()) ? ImageUtil.calcTargetSizeForKeepRatio2(bitmap, preprocessConfig.q(), preprocessConfig.p()) : null;
                    }
                }
            }
            calcWithStep = ImageUtil.calcTarget(bitmap, preprocessConfig.t(), preprocessConfig.g());
        }
        int q = preprocessConfig.q();
        int p = preprocessConfig.p();
        if (calcWithStep != null) {
            q = ((Integer) calcWithStep.first).intValue();
            p = ((Integer) calcWithStep.second).intValue();
        }
        if (iArr != null) {
            iArr[0] = preprocessConfig.u() ? preprocessConfig.q() : q;
            iArr[1] = preprocessConfig.u() ? preprocessConfig.p() : p;
        }
        JniParam a = com.baidu.ai.edge.core.base.b.a(preprocessConfig, q, p);
        if (i == 100) {
            a.put("ocrRecWidth", (long) preprocessConfig.j());
            a.put("ocrRecHeight", (long) preprocessConfig.i());
            a.put("ocrRecBatchNum", (long) preprocessConfig.h());
        }
        return a;
    }

    protected c a(Bitmap bitmap, float f, IStatisticsResultModel iStatisticsResultModel, int i, int[] iArr) throws BaseException {
        a();
        String str = this.f;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("predict ");
        stringBuilder.append(i);
        stringBuilder.append(": confidence: ");
        stringBuilder.append(f);
        Log.i(str, stringBuilder.toString());
        TimeRecorderNew timeRecorderNew = new TimeRecorderNew();
        JniParam a = a(bitmap, i, f, iArr);
        c cVar = new c(a(bitmap, a, i));
        long j = a.getLong("preprocessEndTime");
        String str2 = "extraNetFlag";
        if (a.containsKey(str2)) {
            cVar.a((int) a.getLong(str2));
        }
        long checkpoint = timeRecorderNew.checkpoint(j);
        String str3 = this.f;
        stringBuilder = new StringBuilder();
        stringBuilder.append("[stat]preprocess time: ");
        stringBuilder.append(checkpoint);
        Log.i(str3, stringBuilder.toString());
        long end = timeRecorderNew.end();
        str3 = this.f;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[stat]forward time: ");
        stringBuilder2.append(end);
        Log.i(str3, stringBuilder2.toString());
        if (iStatisticsResultModel != null) {
            iStatisticsResultModel.setPreprocessTime(checkpoint);
            iStatisticsResultModel.setForwardTime(end);
        }
        c();
        return cVar;
    }

    protected String a(int i) {
        String[] labels = this.c.getLabels();
        return (i < 0 || i >= labels.length) ? "UNKNOWN" : labels[i];
    }

    protected List<ClassificationResultModel> a(Bitmap bitmap, float f, e eVar) throws BaseException {
        int[] iArr = new int[2];
        float[] b = a(bitmap, f, eVar, 2, iArr).b();
        com.baidu.ai.edge.core.classify.a aVar = new com.baidu.ai.edge.core.classify.a(getClass(), this.c, bitmap.getWidth(), bitmap.getHeight(), iArr[0], iArr[1]);
        TimeRecorderNew timeRecorderNew = new TimeRecorderNew();
        List<ClassificationResultModel> a = aVar.a(b, f);
        long end = timeRecorderNew.end();
        if (eVar != null) {
            eVar.setPostprocessTime(end);
            eVar.setResultModel(a);
        }
        return a;
    }

    protected List<DetectionResultModel> a(float[] fArr, String[] strArr, int i, int i2) {
        float[] fArr2 = fArr;
        String[] strArr2 = strArr;
        int length = fArr2.length / 7;
        List<DetectionResultModel> arrayList = new ArrayList(length);
        for (int i3 = 0; i3 < length; i3++) {
            String str;
            int i4 = i3 * 7;
            int round = Math.round(fArr2[i4 + 1]);
            if (round < 0 || round >= strArr2.length) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("label index out of bound , index : ");
                stringBuilder.append(round);
                stringBuilder.append(" ,at :");
                stringBuilder.append(i3);
                Log.e("SnpeManager", stringBuilder.toString());
                str = "UNKNOWN";
            } else {
                str = strArr2[round];
            }
            float f = (float) i;
            float f2 = (float) i2;
            DetectionResultModel detectionResultModel = new DetectionResultModel(str, fArr2[i4 + 2], new Rect(Math.round(fArr2[i4 + 3] * f), Math.round(fArr2[i4 + 4] * f2), Math.round(fArr2[i4 + 5] * f), Math.round(fArr2[i4 + 6] * f2)));
            detectionResultModel.setLabelIndex(round);
            arrayList.add(detectionResultModel);
        }
        return arrayList;
    }

    protected void a() throws CallException {
        if (this.g) {
            throw new CallException(Consts.EC_BASE_MANAGER_HAS_DETORYED, "this instance is destoryed");
        }
    }

    protected abstract float[] a(Bitmap bitmap, JniParam jniParam, int i) throws BaseException;

    protected JniParam b() {
        JniParam fillCommonAuthParam = this.a.fillCommonAuthParam(this.d);
        fillCommonAuthParam.put("modelEncVal", (long) this.c.getModelEncValue());
        fillCommonAuthParam.put("modelType", (long) this.c.getModelType());
        fillCommonAuthParam.put("modelFileAssetPath", this.c.getModelFileAssetPath());
        fillCommonAuthParam.put("nType", (long) this.c.getNType());
        fillCommonAuthParam.put("skipDecrypt", Boolean.valueOf(d()));
        return fillCommonAuthParam;
    }

    protected List<DetectionResultModel> b(Bitmap bitmap, float f, e eVar) throws BaseException {
        int[] iArr = new int[2];
        c a = a(bitmap, f, eVar, 2, iArr);
        float[] b = a.b();
        com.baidu.ai.edge.core.detect.a aVar = new com.baidu.ai.edge.core.detect.a(getClass(), this.c, bitmap.getWidth(), bitmap.getHeight(), iArr[0], iArr[1], a.a());
        TimeRecorderNew timeRecorderNew = new TimeRecorderNew();
        List<DetectionResultModel> a2 = aVar.a(b, f);
        long end = timeRecorderNew.end();
        if (eVar != null) {
            eVar.setPostprocessTime(end);
            eVar.setResultModel(a2);
        }
        return a2;
    }

    protected void c() {
        a aVar = this.e;
        if (aVar != null) {
            aVar.a();
        }
    }

    public List<ClassificationResultModel> classify(Bitmap bitmap) throws BaseException {
        return classify(bitmap, this.c.getRecommendedConfidence());
    }

    public List<ClassificationResultModel> classify(Bitmap bitmap, float f) throws BaseException {
        return a(bitmap, f, null);
    }

    public IStatisticsResultModel classifyPro(Bitmap bitmap) throws BaseException {
        e eVar = new e();
        a(bitmap, this.c.getRecommendedConfidence(), eVar);
        return eVar;
    }

    protected boolean d() {
        String authType = this.c.getAuthType();
        if (authType != null && !authType.isEmpty()) {
            return "no-auth".equals(authType);
        } else {
            return Consts.PROD_EASYEDGE_FREE.equals(this.c.getProduct());
        }
    }

    protected void destroy() throws BaseException {
        this.g = true;
        this.a.terminate();
        Log.i("InferManager", "pointer destroy");
        a aVar = this.e;
        if (aVar != null) {
            aVar.b();
        }
    }

    public List<DetectionResultModel> detect(Bitmap bitmap) throws BaseException {
        return detect(bitmap, this.c.getRecommendedConfidence());
    }

    public List<DetectionResultModel> detect(Bitmap bitmap, float f) throws BaseException {
        return b(bitmap, f, null);
    }

    public IStatisticsResultModel detectPro(Bitmap bitmap) throws BaseException {
        e eVar = new e();
        b(bitmap, this.c.getRecommendedConfidence(), eVar);
        return eVar;
    }

    protected abstract void e() throws CallException;
}
