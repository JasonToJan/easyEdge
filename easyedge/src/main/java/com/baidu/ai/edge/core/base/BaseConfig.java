package com.baidu.ai.edge.core.base;

import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.ai.edge.core.util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseConfig implements IBaseConfig {
    public static final float DEFAULT_THRESHOLD = 0.3f;
    protected String a;
    protected String[] b;
    protected int c;
    protected int d;
    protected int e;
    protected int f;
    protected float g;
    protected String h;
    protected int i;
    protected String j;
    protected boolean k;
    protected boolean l;
    protected boolean m = true;
    protected int n = -1;
    private String o = "";
    protected String p;
    private String q = null;
    private String r = Consts.AUTH_MODE_DEVICE;
    private long s;
    private String t;
    private String u;
    protected d v;

    public BaseConfig(AssetManager assetManager, String str) throws CallException {
        String readAssetsFileUTF8StringIfExists;
        String readAssetsFileUTF8StringIfExists2;
        Throwable e;
        boolean z = true;
        if (str.startsWith("file:///")) {
            str = str.substring(7);
            z = false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("/conf.json");
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(str);
        stringBuilder3.append("/preprocess_args.json");
        String stringBuilder4 = stringBuilder3.toString();
        StringBuilder stringBuilder5 = new StringBuilder();
        stringBuilder5.append(str);
        stringBuilder5.append("/infer_cfg.json");
        String stringBuilder6 = stringBuilder5.toString();
        StringBuilder stringBuilder7 = new StringBuilder();
        stringBuilder7.append(str);
        stringBuilder7.append("/label_list.txt");
        String stringBuilder8 = stringBuilder7.toString();
        if (z) {
            readAssetsFileUTF8StringIfExists = FileUtil.readAssetsFileUTF8StringIfExists(assetManager, stringBuilder2);
            readAssetsFileUTF8StringIfExists2 = FileUtil.readAssetsFileUTF8StringIfExists(assetManager, stringBuilder6);
        } else {
            readAssetsFileUTF8StringIfExists = FileUtil.readFileIfExists(stringBuilder2);
            readAssetsFileUTF8StringIfExists2 = FileUtil.readFileIfExists(stringBuilder6);
        }
        if (TextUtils.isEmpty(readAssetsFileUTF8StringIfExists) && TextUtils.isEmpty(readAssetsFileUTF8StringIfExists2)) {
            throw new CallException(1001, "No config file");
        }
        String readAssetsFileUTF8StringIfExists3;
        String readAssetFileUtf8String;
        if (z) {
            try {
                readAssetsFileUTF8StringIfExists3 = FileUtil.readAssetsFileUTF8StringIfExists(assetManager, stringBuilder4);
                readAssetFileUtf8String = FileUtil.readAssetFileUtf8String(assetManager, stringBuilder8);
                a(readAssetsFileUTF8StringIfExists, readAssetsFileUTF8StringIfExists3, readAssetFileUtf8String, readAssetsFileUTF8StringIfExists2);
            } catch (IOException e2) {
                e = e2;
                stringBuilder = new StringBuilder();
                stringBuilder.append("config read asset file error ");
                stringBuilder.append(str);
                throw new CallException(1001, stringBuilder.toString(), e);
            }
        }

//        try {
//            readAssetsFileUTF8StringIfExists3 = FileUtil.readFileIfExists(stringBuilder4);
//            readAssetFileUtf8String = FileUtil.readFile(stringBuilder8);
//            a(readAssetsFileUTF8StringIfExists, readAssetsFileUTF8StringIfExists3, readAssetFileUtf8String, readAssetsFileUTF8StringIfExists2);
//        } catch (IOException e4) {
//            stringBuilder = new StringBuilder();
//            stringBuilder.append("config read asset file error ");
//            stringBuilder.append(str);
//            throw new CallException(1001, stringBuilder.toString(), e4);
//        }

    }

    private void a(String str, String str2, String str3, String str4) throws CallException {
        Log.e("TEST##", "str==================\n"+str);

        Log.e("TEST##", "\nstr2==================\n"+str2);

//        Log.e("TEST##", "\nstr3==================\n"+str3);

        Log.e("TEST##", "\nstr4==================\n"+str4);

        JSONObject b;
        JSONObject jSONObject = null;
        if (!TextUtils.isEmpty(str4)) {
            if (!TextUtils.isEmpty(str)) {
                jSONObject = a(str);
            }
            b = b(str4);
            try {
                str2 = b.getString("pre_process");
                JSONObject jSONObject2 = jSONObject;
                jSONObject = b;
                b = jSONObject2;
            } catch (Throwable e) {
                e.printStackTrace();
                throw new CallException(1001, "No pre_process config", e);
            }
        } else if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            throw new CallException(1001, "No conf.json and preprocess.json");
        } else {
            b = a(str);
        }
        d(str2);
        c(str3);
        try {
            a(b, jSONObject);
        } catch (Throwable e2) {
            throw new CallException(1001, "parse JsonError ", e2);
        }
    }

    private JSONObject b(String str) throws CallException {
        String str2 = "quantization";
        String str3 = "optType";
        String str4 = "fluid";
        String str5 = "yolo";
        String str6 = "extra";
        String str7 = "block";
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.getJSONObject("model_info");
            this.f = jSONObject2.optInt("n_type", 0);
            this.g = (float) jSONObject2.optDouble("best_threshold", 0.30000001192092896d);
            this.i = jSONObject2.getInt("model_kind");
            if (jSONObject.has(str7)) {
                jSONObject2 = jSONObject.getJSONObject(str7);
                if (jSONObject2.has(str5)) {
                    this.p = jSONObject2.getJSONObject(str5).optString("detection");
                }
            }
            if (jSONObject.has(str6)) {
                jSONObject2 = jSONObject.getJSONObject(str6);
                if (jSONObject2.has(str4)) {
                    jSONObject2 = jSONObject2.getJSONObject(str4);
                    if (jSONObject2.has(str3)) {
                        if ("nb".equals(jSONObject2.getString(str3))) {
                            this.l = true;
                        }
                    }
                    if (jSONObject2.has(str2)) {
                        if ("int8".equals(jSONObject2.getString(str2))) {
                            this.k = true;
                        }
                    }
                }
            }
            this.j = Consts.PROD_EASYEDGE_FREE;
            this.q = "no-auth";
            this.m = false;
            this.n = 0;
            return jSONObject;
        } catch (Throwable e) {
            e.printStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("infer_cfg.json parse error: ");
            stringBuilder.append(e.getMessage());
            throw new CallException(1002, stringBuilder.toString(), e);
        }
    }

    private void c(String str) {
        String[] split = str.replaceAll("\r", "").split("\n");
        List arrayList;
        if (this.i == 100 && this.v.h() == 6) {
            arrayList = new ArrayList(Arrays.asList(split));
            arrayList.add(0, "#");
            arrayList.add(" ");
            this.b = (String[]) arrayList.toArray(new String[0]);
        } else {
            arrayList = new ArrayList();
            for (String obj2 : split) {
                if (Pattern.matches("\\d+:.+", obj2)) {
                    obj2 = obj2.substring(obj2.indexOf(58) + 1);
                }
                arrayList.add(obj2);
            }
            this.b = (String[]) arrayList.toArray(new String[0]);
        }
        this.c = this.b.length;
    }

    private void d(String str) throws CallException {
        this.v = new d(str, this.i, this.f);
    }

    public static float[] getArrayFloatValues(JSONArray jSONArray) throws JSONException {
        int length = jSONArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = (float) jSONArray.getDouble(i);
        }
        return fArr;
    }

    public static String[] getArrayStringValues(JSONArray jSONArray) throws JSONException {
        int length = jSONArray.length();
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = jSONArray.getString(i);
        }
        return strArr;
    }

    protected JSONObject a(String str) throws CallException {
        String str2 = "modelEnc";
        String str3 = "quantization";
        String str4 = "optType";
        String str5 = "fluid";
        String str6 = "authType";
        String str7 = "extra";
        String str8 = "nType";
        String str9 = "thresholdRec";
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(str9)) {
                this.g = (float) jSONObject.getDouble(str9);
            } else {
                this.g = 0.3f;
            }
            if (jSONObject.has(str8)) {
                this.f = jSONObject.getInt(str8);
            } else {
                this.f = 0;
            }
            str8 = "";
            if (jSONObject.has(str7)) {
                JSONObject jSONObject2 = jSONObject.getJSONObject(str7);
                if (jSONObject2.has(str5)) {
                    JSONObject jSONObject3 = jSONObject2.getJSONObject(str5);
                    if (jSONObject3.has(str4) && jSONObject3.getString(str4).equals("nb")) {
                        this.l = true;
                    }
                    if (jSONObject3.has(str3) && jSONObject3.getString(str3).equals("int8")) {
                        this.k = true;
                    }
                }
                this.p = jSONObject2.optString("detection", str8);
            }
            if (jSONObject.has(str6)) {
                this.q = jSONObject.getString(str6);
                if ("acceleration".equals(this.q)) {
                    this.k = true;
                }
            }
            this.i = jSONObject.optInt("modelType");
            this.e = jSONObject.optInt("releaseId");
            this.d = jSONObject.optInt("modelId");
            this.j = jSONObject.optString("product");
            if ("no-auth".equals(this.q) || ((this.q == null && this.j.equals(Consts.PROD_EASYEDGE_FREE)) || this.j.equals(Consts.PROD_BML_FREE))) {
                this.m = false;
            }
            if (jSONObject.has(str2)) {
                this.n = jSONObject.getInt(str2);
            } else if (this.j.equals(Consts.PROD_PRO)) {
                this.n = 2;
            } else {
                this.n = 1;
            }
            this.h = jSONObject.optString("modelName", str8);
            return jSONObject;
        } catch (Throwable e) {
            e.printStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" conf.json parse error ");
            stringBuilder.append(e.getMessage());
            throw new CallException(1002, stringBuilder.toString(), e);
        }
    }

    protected void a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
    }

    public String getAuthDomain() {
        return this.t;
    }

    public long getAuthInterval() {
        return this.s;
    }

    public String getAuthMode() {
        return this.r;
    }

    public String getAuthType() {
        return this.q;
    }

    public int getCateNum() {
        return this.c;
    }

    public String getChannelOrder() {
        return this.v.c();
    }

    public String getColorFormat() {
        return this.v.d();
    }

    public String getDeviceLicenseUri() {
        return this.u;
    }

    public String getExtraDetectionJson() {
        return !TextUtils.isEmpty(this.p) ? this.p : this.v.e();
    }

    public int getImageHeight() {
        return this.v.p();
    }

    public int getImageWidth() {
        return this.v.q();
    }

    public float[] getImgMeans() {
        return this.v.f();
    }

    public String[] getLabels() {
        return this.b;
    }

    public int getMaxSize() {
        return this.v.g();
    }

    public int getMid() {
        return this.d;
    }

    public int getModelEncValue() {
        return this.n;
    }

    public String getModelFileAssetPath() {
        return this.a;
    }

    public String getModelInfo() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("modelId", getMid());
            jSONObject.put("modelName", getModelName());
            jSONObject.put("modelType", getModelType());
            jSONObject.put("thresholdRec", (double) getRecommendedConfidence());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public String getModelName() {
        return this.h;
    }

    public int getModelType() {
        return this.i;
    }

    public int getNType() {
        return this.f;
    }

    public d getPreprocessConfig() {
        return this.v;
    }

    public String getProduct() {
        return this.j;
    }

    public float getRecommendedConfidence() {
        return this.g;
    }

    public int getRid() {
        return this.e;
    }

    public float[] getScales() {
        return this.v.s();
    }

    public int getTargetSize() {
        return this.v.t();
    }

    public String getUserDeviceId() {
        return this.o;
    }

    public boolean isAcceleration() {
        return this.k;
    }

    public boolean isEnc() {
        return this.m;
    }

    public boolean isHWC() {
        return this.v.c().equals("HWC");
    }

    public boolean isOptModel() {
        return this.l;
    }

    public boolean isRGB() {
        return this.v.d().equals("RGB");
    }

    public void setAcceleration(boolean z) {
        this.k = z;
    }

    public void setAuthDomain(String str) {
        this.t = str;
    }

    public void setAuthType(String str) {
        this.q = str;
    }

    public void setCateNum(int i) {
        this.c = i;
    }

    public void setDeviceLicenseUri(String str) {
        this.u = str;
    }

    public void setInstanceAuthMode() {
        setInstanceAuthMode(Consts.AUTH_DEF_INTERVAL);
    }

    public void setInstanceAuthMode(long j) {
        this.s = Consts.AUTH_DEF_INTERVAL;
        if (j > 5) {
            this.s = j;
        }
        this.r = Consts.AUTH_MODE_INSTANCE;
    }

    public void setLabels(String[] strArr) {
        this.b = strArr;
    }

    public void setMid(int i) {
        this.d = i;
    }

    public void setModelFileAssetPath(String str) {
        this.a = str;
    }

    public void setModelType(int i) {
        this.i = i;
    }

    public void setProduct(String str) {
        this.j = str;
    }

    public void setRecommendedConfidence(int i) {
        this.g = (float) i;
    }

    public void setRid(int i) {
        this.e = i;
    }

    public void setUserDeviceId(String str) {
        this.o = str;
    }
}
