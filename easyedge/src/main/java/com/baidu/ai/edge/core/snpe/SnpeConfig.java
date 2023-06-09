package com.baidu.ai.edge.core.snpe;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.Consts;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SnpeConfig extends BaseConfig implements ISnpeConfig, SnpeRuntimeInterface {
    private boolean w;
    protected int[] x;

    public SnpeConfig(AssetManager assetManager, String str) throws CallException {
        super(assetManager, str);
        StringBuilder stringBuilder;
        if (this.m) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            str = "/params.enc";
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            str = "/params";
        }
        stringBuilder.append(str);
        this.a = stringBuilder.toString();
    }

    protected void a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        super.a(jSONObject, jSONObject2);
        if (this.x == null) {
            this.x = new int[]{2, 1, 3, 0};
        }
        this.w = jSONObject.optBoolean("autocheck_qcom", true);
        JSONArray optJSONArray = jSONObject.optJSONArray("snpe_runtimes_order");
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            if (length > 0) {
                this.x = new int[length];
                for (int i = 0; i < length; i++) {
                    this.x[i] = optJSONArray.getInt(i);
                }
            }
        }
        this.v.a("HWC");
    }

    public int[] getSnpeRuntimesOrder() {
        return (int[]) this.x.clone();
    }

    public String getSoc() {
        return Consts.SOC_DSP;
    }

    public boolean isAutocheckQcom() {
        return this.w;
    }

    public void setAutocheckQcom(boolean z) {
        this.w = z;
    }

    public void setSnpeRuntimesOrder(int[] iArr) {
        this.x = (int[]) iArr.clone();
    }
}
