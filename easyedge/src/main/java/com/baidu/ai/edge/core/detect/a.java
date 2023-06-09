package com.baidu.ai.edge.core.detect;

import android.graphics.Rect;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.BaseManager;
import com.baidu.ai.edge.core.base.BaseResultModel;
import com.baidu.ai.edge.core.ddk.DDKManager;
import com.baidu.ai.edge.core.ddk.DavinciManager;
import com.baidu.ai.edge.core.infer.InferManager;
import com.baidu.ai.edge.core.snpe.SnpeManager;
import java.util.ArrayList;
import java.util.List;

public class a {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private BaseConfig f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private boolean l = false;
    private boolean m = false;

    public a(Class<? extends BaseManager> cls, BaseConfig baseConfig, int i, int i2, int i3, int i4, int i5) {
        this.f = baseConfig;
        this.g = i;
        this.h = i2;
        this.i = i3;
        this.j = i4;
        this.k = i5;
        this.a = baseConfig.getNType();
        if (cls.equals(InferManager.class)) {
            this.b = 0;
            this.d = 1;
            this.c = 2;
            this.e = 6;
            this.l = true;
        } else if (!cls.equals(DDKManager.class) && !cls.equals(SnpeManager.class)) {
            if (cls.equals(DavinciManager.class)) {
                this.b = 1;
                this.d = 2;
                this.c = 3;
                this.e = 7;
                this.l = true;
                this.m = true;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("DetectPostProcess class not support ");
                stringBuilder.append(cls.getSimpleName());
                throw new RuntimeException(stringBuilder.toString());
            }
        }
    }

    private int a(float f, int i) {
        int i2 = this.a;
        if (i2 == 101 || i2 == 102 || i2 == 109 || i2 == 110) {
            if (this.k == 3) {
                return Math.round(f);
            }
            f /= (float) (i % 2 == 0 ? this.i : this.j);
        }
        return Math.round(f * ((float) (i % 2 == 0 ? this.g : this.h)));
    }

    public List<DetectionResultModel> a(float[] fArr, float f) {
        int i;
        float[] fArr2 = fArr;
        List<DetectionResultModel> arrayList = new ArrayList();
        String[] labels = this.f.getLabels();
        int length = fArr2.length;
        String str = "DetectPostProcess";
        if (this.l) {
            i = this.e;
            i *= length / i;
            if (i != length) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("output length is ");
                stringBuilder.append(length);
                Log.w(str, stringBuilder.toString());
            }
            length = i;
        }
        i = 0;
        int i2 = 0;
        while (i2 < length) {
            float f2 = fArr2[this.d + i2];
            if (this.m && ((double) f2) < 0.01d) {
                break;
            }
            if (f2 >= f) {
                int round = Math.round(fArr2[this.b + i2]);
                if (round < 0) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("label index out of bound , index : ");
                    stringBuilder2.append(round);
                    stringBuilder2.append(" ,at :");
                    stringBuilder2.append(i2);
                    Log.e(str, stringBuilder2.toString());
                } else {
                    String str2;
                    if (round < labels.length) {
                        str2 = labels[round];
                    } else {
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("UNKNOWN:");
                        stringBuilder3.append(round);
                        str2 = stringBuilder3.toString();
                    }
                    int i3 = this.c + i2;
                    DetectionResultModel detectionResultModel = new DetectionResultModel(str2, f2, new Rect(a(fArr2[i3], i), a(fArr2[i3 + 1], 1), a(fArr2[i3 + 2], 2), a(fArr2[i3 + 3], 3)));
                    detectionResultModel.setLabelIndex(round);
                    arrayList.add(detectionResultModel);
                }
            }
            i2 += this.e;
            i = 0;
        }
        return arrayList;
    }
}
