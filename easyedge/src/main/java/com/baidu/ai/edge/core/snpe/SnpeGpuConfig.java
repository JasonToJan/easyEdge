package com.baidu.ai.edge.core.snpe;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.CallException;
import org.json.JSONException;
import org.json.JSONObject;

public class SnpeGpuConfig extends SnpeConfig {
    public SnpeGpuConfig(AssetManager assetManager, String str) throws CallException {
        super(assetManager, str);
    }

    protected void a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        if (this.x == null) {
            this.x = new int[]{1, 3, 0};
        }
        super.a(jSONObject, jSONObject2);
    }
}
