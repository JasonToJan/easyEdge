package com.baidu.ai.edge.core.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;

import androidx.core.os.EnvironmentCompat;

import java.io.File;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class DeviceInfo {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private Context a;

    public DeviceInfo(Context context) {
        this.a = context;
    }

    public static String byteArrayToMacAddress(byte[] bytes) {
        if (bytes == null || bytes.length != 6) {
            return "";
        }
        char[] hexChars = new char[17];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 3] = HEX_ARRAY[v >>> 4];
            hexChars[i * 3 + 1] = HEX_ARRAY[v & 0x0F];
            if (i != bytes.length - 1) {
                hexChars[i * 3 + 2] = ':';
            }
        }
        return new String(hexChars);
    }

    public static String getAndroidVersion() {
        return VERSION.RELEASE;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static HashMap<String, String> getMacArray() {
        HashMap<String, String> linkedHashMap = new LinkedHashMap();
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return null;
            }
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                String a = byteArrayToMacAddress(networkInterface.getHardwareAddress());
                if (!a.isEmpty()) {
                    linkedHashMap.put(networkInterface.getName(), a);
                }
            }
            return linkedHashMap;
        } catch (Throwable e) {
            Log.e("tag", e.getMessage(), e);
        }
        return linkedHashMap;
    }

    public static String getPingModifiedTime() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Long.valueOf(new File("/bin/ping").lastModified()));
    }

    public static String getSerial() {
        if (VERSION.SDK_INT < 26) {
            return Build.SERIAL;
        }
        try {
            Class cls = Class.forName("android.os.Build");
            return (String) cls.getMethod("getSerial", new Class[0]).invoke(cls, new Object[0]);
        } catch (Exception unused) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public static String getSystemModel() {
        return Build.MODEL;
    }

    public String getBundleId() {
        return this.a.getPackageName();
    }

    public String getDeviceId() {
        String string;
        synchronized (DeviceInfo.class) {
            SharedPreferences sharedPreferences = this.a.getSharedPreferences("easydl-app", 0);
            string = sharedPreferences.getString("id", "");
            if (string.isEmpty()) {
                string = UUID.randomUUID().toString();
                Editor edit = sharedPreferences.edit();
                edit.putString("id", string);
                edit.commit();
            }
        }
        return string;
    }
}
