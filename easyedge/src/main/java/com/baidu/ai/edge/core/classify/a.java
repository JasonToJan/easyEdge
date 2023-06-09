package com.baidu.ai.edge.core.classify;

import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.BaseManager;
import com.baidu.ai.edge.core.base.BaseResultModel;
import com.baidu.ai.edge.core.ddk.DDKManager;
import com.baidu.ai.edge.core.ddk.DavinciManager;
import com.baidu.ai.edge.core.infer.InferManager;
import com.baidu.ai.edge.core.snpe.SnpeManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class a {
    private BaseConfig a;
    private int b = 1;

    class InnerA implements Comparator<ClassificationResultModel> {
        InnerA(a aVar) {
        }

        /* renamed from: a */
        public int compare(ClassificationResultModel classificationResultModel, ClassificationResultModel classificationResultModel2) {
            float confidence = classificationResultModel2.getConfidence() - classificationResultModel.getConfidence();
            return confidence > 0.0f ? 1 : confidence < 0.0f ? -1 : 0;
        }
    }

    public a(Class<? extends BaseManager> cls, BaseConfig baseConfig, int i, int i2, int i3, int i4) {
        this.a = baseConfig;
        baseConfig.getNType();
        if (cls.equals(InferManager.class) || cls.equals(DavinciManager.class)) {
            this.b = 1;
        } else if (!cls.equals(DDKManager.class) && !cls.equals(SnpeManager.class)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DetectPostProcess class not support ");
            stringBuilder.append(cls.getSimpleName());
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    private List<ClassificationResultModel> b(float[] fArr, float f) {
        List<ClassificationResultModel> arrayList = new ArrayList();
        String[] labels = this.a.getLabels();
        int i = 0;
        while (i < fArr.length) {
            if (fArr[i] >= f) {
                String stringBuilder;
                if (i >= labels.length || i < 0) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("UNKNOWN:");
                    stringBuilder2.append(i);
                    stringBuilder = stringBuilder2.toString();
                } else {
                    stringBuilder = labels[i];
                }
                ClassificationResultModel classificationResultModel = new ClassificationResultModel(stringBuilder, fArr[i]);
                classificationResultModel.setLabelIndex(i);
                arrayList.add(classificationResultModel);
            }
            i++;
        }
        Collections.sort(arrayList, new InnerA(this));
        return arrayList;
    }

    public List<ClassificationResultModel> a(float[] fArr, float f) {
        return this.b == 1 ? b(fArr, f) : null;
    }
}
