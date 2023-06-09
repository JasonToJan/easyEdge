package com.baidu.ai.edge.core.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.baidu.ai.edge.core.base.BaseManager;
import com.baidu.ai.edge.core.base.IBaseConfig;
import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {
    private static final FileFilter a = new a();

    static class a implements FileFilter {
        a() {
        }

        public boolean accept(File file) {
            String name = file.getName();
            if (!name.startsWith("cpu")) {
                return false;
            }
            int i = 3;
            while (i < name.length()) {
                if (name.charAt(i) < '0' || name.charAt(i) > '9') {
                    return false;
                }
                i++;
            }
            return true;
        }
    }

    private Util() {
    }

    /* DevToolsApp WARNING: Removed duplicated region for block: B:2:0x000f A:{Splitter: B:0:0x0000, ExcHandler: java.lang.SecurityException (unused java.lang.SecurityException)} */
    /* DevToolsApp WARNING: Missing block: B:3:?, code:
            return 1;
     */
    private static int a() {
        return Runtime.getRuntime().availableProcessors();
    }

    private static JSONObject getDeviceInfoDetail(IBaseConfig iBaseConfig) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        HashMap<String, String> macArray = DeviceInfo.getMacArray();
        JSONObject jSONObject2 = new JSONObject();
        for (Entry entry : macArray.entrySet()) {
            jSONObject2.put((String) entry.getKey(), entry.getValue());
        }
        jSONObject.put("sdk.version", BaseManager.VERSION);
        jSONObject.put("macs", jSONObject2);
        jSONObject.put("build_version_sdk_int", VERSION.SDK_INT);
        jSONObject.put("java_serial", DeviceInfo.getSerial());
        jSONObject.put("build.BRAND", Build.BRAND);
        jSONObject.put("build.MODEL ", Build.MODEL);
        jSONObject.put("build.DISPLAY", Build.DISPLAY);
        jSONObject.put("build.HOST", Build.HOST);
        jSONObject.put("build.MANUFACTURER", Build.MANUFACTURER);
        jSONObject.put("build.FINGERPRINT", Build.FINGERPRINT);
        jSONObject.put("build.Time", Build.TIME);
        jSONObject.put("ping_modify_time", DeviceInfo.getPingModifiedTime());
        String replaceAll = iBaseConfig.getUserDeviceId().replaceAll("[^A-Za-z0-9\\-_]", "");
        jSONObject.put("userDeviceId", replaceAll.substring(0, Math.min(0, replaceAll.length())));
        return jSONObject;
    }

    public static JSONObject getBaseInfoJson(IBaseConfig iBaseConfig) {
        return getBaseInfoJson(iBaseConfig, false, null);
    }

    public static JSONObject getBaseInfoJson(IBaseConfig config, boolean includeAuthInfo, String deviceId) {
        JSONObject result = new JSONObject();
        try {
            JSONObject terminalInfo = new JSONObject();
            terminalInfo.put("osType", "ANDROID");
            terminalInfo.put("osVersion", DeviceInfo.getAndroidVersion());
            terminalInfo.put("manufacturer", DeviceInfo.getDeviceBrand() + " | " + DeviceInfo.getSystemModel());
            if (deviceId != null) {
                terminalInfo.put("deviceId", deviceId);
            }

            JSONObject sdkInfo = new JSONObject();
            sdkInfo.put("sdkVersion", "0.10.7");
            sdkInfo.put("sdkType", "Android");
            sdkInfo.put("sdkLevel", config.getSoc().equals("xeye") ? "XEYE" : (config.isAcceleration() ? "ACCELERATION" : "BASIC"));
            sdkInfo.put("os", "ANDROID");
            sdkInfo.put("product", config.getProduct());
            sdkInfo.put("soc", config.getSoc());

            result.put("sdkInfo", sdkInfo);
            if (includeAuthInfo) {
                JSONObject authInfo = new JSONObject();
                authInfo.put("mId", config.getMid());
                authInfo.put("rId", config.getRid());
                authInfo.put("authType", config.isAcceleration() ? "ACCELERATION" : "COMMON");
                result.put("mInfo", authInfo);
                terminalInfo.put("deviceIdDetail", getDeviceInfoDetail(config));
            }
            result.put("terminalInfo", terminalInfo);
            result.put("logId", UUID.randomUUID().toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static String getDeviceId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("edge_sdk_uuid", 0);
        String str = "";
        String str2 = "uuid";
        String string = sharedPreferences.getString(str2, str);
        if (TextUtils.isEmpty(string)) {
            string = UUID.randomUUID().toString();
            Editor edit = sharedPreferences.edit();
            edit.putString(str2, string);
            edit.commit();
        }
        return string == null ? str : string;
    }

    public static int getInferCores() {
        int a = a();
        return a > 1 ? a / 2 : 1;
    }

    public static String getTodayStr() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static Map<String, Integer> jsonObjectToIntMap(JSONObject jSONObject) throws JSONException {
        Map<String, Integer> hashMap = new HashMap();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            hashMap.put(str, Integer.valueOf(jSONObject.getInt(str)));
        }
        return hashMap;
    }

    public static JSONObject mapToJsonObject(Map<String, ?> map) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (Entry entry : map.entrySet()) {
            jSONObject.put((String) entry.getKey(), entry.getValue());
        }
        return jSONObject;
    }
}
