package com.baidu.ai.edge.core.infer;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.Consts;
import com.baidu.ai.edge.core.util.Util;

public class InferConfig extends BaseConfig {
    private String w;
    private String x;
    private int y = 0;

    public InferConfig(AssetManager assetManager, String str) throws CallException {
        super(assetManager, str);
        String str2 = "/model";
        String str3 = "/params";
        StringBuilder stringBuilder;
        if (this.i == 100) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(str2);
            this.a = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(str3);
            this.x = stringBuilder.toString();
            this.l = true;
            return;
        }
        if (this.m) {
            str2 = "/params.enc";
            if (this.l) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(str2);
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append("/model.enc");
                this.a = stringBuilder.toString();
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(str2);
                this.w = stringBuilder.toString();
                return;
            }
        } else if (this.l) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(str3);
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(str2);
            this.a = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(str3);
            this.w = stringBuilder.toString();
            return;
        }
        this.a = stringBuilder.toString();
    }

    public String getExtraModelFilePath() {
        return this.x;
    }

    public String getParamFileAssetPath() {
        return this.w;
    }

    public String getSoc() {
        return Consts.SOC_ARM;
    }

    public int getThread() {
        if (this.y == 0) {
            this.y = Util.getInferCores();
        }
        return this.y;
    }

    public void setExtraModelFilePath(String str) {
        this.x = str;
    }

    public void setParamFileAssetPath(String str) {
        this.w = str;
    }

    public void setThread(int i) {
        this.y = i;
    }
}
