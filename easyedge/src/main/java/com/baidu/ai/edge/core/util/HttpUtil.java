package com.baidu.ai.edge.core.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpUtil {

    private static class b implements HostnameVerifier {
        private b() {
        }

        public boolean verify(String str, SSLSession sSLSession) {
            return true;
        }
    }

    private static class c implements X509TrustManager {
        private c() {
        }

        public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private HttpUtil() {
    }

    private static void a(HttpsURLConnection httpsURLConnection) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext instance = SSLContext.getInstance("TLSv1.2");
        instance.init(null, new TrustManager[]{new c()}, new SecureRandom());
        httpsURLConnection.setSSLSocketFactory(instance.getSocketFactory());
        httpsURLConnection.setHostnameVerifier(new b());
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.NO_WRAP);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String get(String str) throws IOException {
        GeneralSecurityException e;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("try to connect:");
        stringBuilder.append(str);
        stringBuilder.append(" Thread id:");
        stringBuilder.append(Thread.currentThread().getId());
        Log.i("HttpUtil", stringBuilder.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        if (httpURLConnection instanceof HttpsURLConnection) {
            try {
                a((HttpsURLConnection) httpURLConnection);
            } catch (NoSuchAlgorithmException e2) {
                e = e2;
            } catch (KeyManagementException e3) {
                e = e3;
            }
        }
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(4000);
        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setDoOutput(false);
        return getResp(httpURLConnection.getInputStream());
    }

    public static byte[] getBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }

    public static String getResp(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        char[] cArr = new char[512];
        while (true) {
            int read = bufferedReader.read(cArr);
            if (read != -1) {
                stringBuffer.append(new String(cArr, 0, read));
            } else {
                bufferedReader.close();
                return stringBuffer.toString();
            }
        }
    }

    public static boolean isOnline(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static String post(String str, Bitmap bitmap, Map<String, Float> map) throws IOException, JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("try to connect:");
        stringBuilder.append(str);
        stringBuilder.append(" Thread id:");
        stringBuilder.append(Thread.currentThread().getId());
        Log.i("HttpUtil", stringBuilder.toString());
        URL url = new URL(str);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("image", bitmapToBase64(bitmap));
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                jSONObject.put((String) entry.getKey(), entry.getValue());
            }
        }
        byte[] bytes = getBytes(jSONObject.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setConnectTimeout(4000);
        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.getOutputStream().write(bytes);
        return getResp(httpURLConnection.getInputStream());
    }

    public static String post(String str, String str2) throws IOException {
        return post(str, str2, new HashMap());
    }

    public static String post(String str, String str2, Map<String, String> map) throws IOException {
        GeneralSecurityException e;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("try to connect:");
        stringBuilder.append(str);
        stringBuilder.append(" Thread id:");
        stringBuilder.append(Thread.currentThread().getId());
        Log.i("HttpUtil", stringBuilder.toString());
        URL url = new URL(str);
        byte[] bytes = getBytes(str2);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        if (httpURLConnection instanceof HttpsURLConnection) {
            try {
                a((HttpsURLConnection) httpURLConnection);
            } catch (NoSuchAlgorithmException e2) {
                e = e2;
            } catch (KeyManagementException e3) {
                e = e3;
            }
        }
        String str3 = "Content-Type";
        httpURLConnection.setRequestProperty(str3, map.containsKey(str3) ? (String) map.get(str3) : "application/json");
        httpURLConnection.setRequestMethod("POST");
        str3 = "ConnectTimeout";
        int intValue = map.containsKey(str3) ? Integer.valueOf((String) map.get(str3)).intValue() : 4000;
        String str4 = "ReadTimeout";
        int intValue2 = map.containsKey(str4) ? Integer.valueOf((String) map.get(str4)).intValue() : 20000;
        httpURLConnection.setConnectTimeout(intValue);
        httpURLConnection.setReadTimeout(intValue2);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.getOutputStream().write(bytes);
        return getResp(httpURLConnection.getInputStream());
    }

    public static String postPlain(String str, String str2) throws IOException {
        Map hashMap = new HashMap();
        hashMap.put("Content-Type", "text/plain");
        return post(str, str2, hashMap);
    }
}
