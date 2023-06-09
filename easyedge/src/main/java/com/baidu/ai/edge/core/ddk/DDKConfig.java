package com.baidu.ai.edge.core.ddk;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.Consts;
import com.baidu.ai.edge.core.util.FileUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DDKConfig extends BaseConfig {
    public static final String SDK_CONFIG_PATH = "sdk-config.json";

    public DDKConfig(AssetManager assetManager, String str) throws CallException {
        super(assetManager, str);
        String str2 = "id";
        StringBuilder stringBuilder;
        try {
            String readFile;
            boolean startsWith = str.startsWith("file:///");
            String str3 = SDK_CONFIG_PATH;
            String str4 = "/";
            if (startsWith) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str.substring(7));
                stringBuilder2.append(str4);
                stringBuilder2.append(str3);
                readFile = FileUtil.readFile(stringBuilder2.toString());
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(str);
                stringBuilder.append(str4);
                stringBuilder.append(str3);
                readFile = FileUtil.readAssetFileUtf8String(assetManager, stringBuilder.toString());
            }
            JSONArray jSONArray = new JSONArray(readFile);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject.has(str2) && jSONObject.getString(str2).equals("ddk200")) {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(str);
                    stringBuilder3.append(str4);
                    stringBuilder3.append(jSONObject.getString("params"));
                    stringBuilder3.append(isEnc() ? ".enc" : "");
                    this.a = stringBuilder3.toString();
                }
            }
        } catch (Exception e) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sdk-config read asset file error ");
            stringBuilder.append(str);
            throw new CallException(1001, stringBuilder.toString(), e);
        } catch (Throwable e2) {
            e2.printStackTrace();
            throw new CallException(1002, " sdk-config parse json error ", e2);
        }
    }

    protected void a(JSONObject jSONObject, JSONObject jSONObject2) throws JSONException {
        super.a(jSONObject, jSONObject2);
        this.v.a("CHW");
    }

    public String getSoc() {
        return Consts.SOC_NPU;
    }
}
