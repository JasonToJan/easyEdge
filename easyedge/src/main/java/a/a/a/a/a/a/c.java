package a.a.a.a.a.a;

import a.a.a.a.a.a.d.a;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.baidu.ai.edge.core.base.IBaseConfig;
import com.baidu.ai.edge.core.base.ISDKJni;
import com.baidu.ai.edge.core.util.HttpUtil;
import com.baidu.ai.edge.core.util.Util;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
    private static String c = "https://verify.baidubce.com";
    private ISDKJni a;
    private String b;

    public c(Context context, ISDKJni iSDKJni) {
        this.a = iSDKJni;
        this.b = context.getPackageName();
    }

    private JSONObject a(d dVar) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (a aVar : dVar.b()) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("modelId", aVar.b());
            jSONObject2.put("releaseId", aVar.c());
            jSONObject2.put("data", Util.mapToJsonObject(aVar.a()));
            jSONArray.put(jSONObject2);
        }
        jSONObject.put("modelInvoke", jSONArray);
        jSONObject.put("sdkLaunch", Util.mapToJsonObject(dVar.c()));
        return jSONObject;
    }

    public void a(d dVar, IBaseConfig iBaseConfig, String str) throws JSONException, IOException {
        JSONObject baseInfoJson = Util.getBaseInfoJson(iBaseConfig, false, dVar.a());
        baseInfoJson.put("bundleId", this.b);
        baseInfoJson.put("timestamp", new Date().getTime() / 1000);
        baseInfoJson.put("data", a(dVar));
        baseInfoJson.put("version", 2);
        baseInfoJson.getJSONObject("terminalInfo").put("deviceId", str);
        String statJson = this.a.getStatJson(baseInfoJson.toString());
        Map hashMap = new HashMap();
        hashMap.put("ConnectTimeout", "4000");
        hashMap.put("ReadTimeout", "3000");
        hashMap.put("Content-Type", "text/plain");
        if (!TextUtils.isEmpty(iBaseConfig.getAuthDomain())) {
            c = iBaseConfig.getAuthDomain();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(c);
        stringBuilder.append("/offline-auth/v2/usage/edge");
        statJson = HttpUtil.post(stringBuilder.toString(), statJson, hashMap);
        stringBuilder = new StringBuilder();
        stringBuilder.append("http result:");
        stringBuilder.append(statJson);
        Log.i("StatRequest", stringBuilder.toString());
        if (statJson == null || statJson.isEmpty()) {
            throw new IOException("Request content not correct");
        }
        try {
            JSONObject jSONObject = new JSONObject(statJson);
            if (!jSONObject.getBoolean("success") || jSONObject.getInt(NotificationCompat.CATEGORY_STATUS) != 0) {
                throw new IOException("Request content is not successful");
            }
        } catch (Throwable e) {
            throw new IOException("Request content is not json", e);
        }
    }
}
