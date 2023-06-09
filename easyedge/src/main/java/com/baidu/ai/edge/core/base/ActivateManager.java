package com.baidu.ai.edge.core.base;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.baidu.ai.edge.core.ddk.DDKJni;
import com.baidu.ai.edge.core.ddk.DavinciJni;
import com.baidu.ai.edge.core.infer.InferLiteJni;
import com.baidu.ai.edge.core.snpe.SnpeJni;
import com.baidu.ai.edge.core.util.HttpUtil;
import com.baidu.ai.edge.core.util.Util;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

public final class ActivateManager {
    public static final int JNILIB_TYPE_ARM = 100;
    public static final int JNILIB_TYPE_DAVINCI = 104;
    public static final int JNILIB_TYPE_DDK = 102;
    public static final int JNILIB_TYPE_SNPE = 101;
    public static final int JNILIB_TYPE_XEYE = 103;
    private static final String TAG = "ActivateManager";
    private static final String URL_DEVICE_ACTIVATE = "/offline-auth/v2/key/activate/edge";
    private static final String URL_GENERATE_KEY = "/instance-auth/v1/key/generate";
    private static final String URL_INSTANCE_ACTIVATE = "/instance-auth/v1/key/activate/edge";
    private static final String URL_INSTANCE_DEACTIVATE = "/instance-auth/v1/key/deactivate/edge";
    private static String deviceActivateUri = "/offline-auth/v2/key/activate/edge";
    private static String domain = "https://verify.baidubce.com";
    private static volatile ScheduledThreadPoolExecutor instanceActivateExecutor;
    private IBaseConfig config;
    private Context context;

    class a implements Runnable {
        a(ActivateManager activateManager) {
        }

        public void run() {
            if (ActivateManager.instanceActivateExecutor != null) {
                ActivateManager.instanceActivateExecutor.shutdownNow();
                ActivateManager.instanceActivateExecutor = null;
                Log.i(ActivateManager.TAG, "executor terminated");
            }
        }
    }

    static class ActivateManagerTask implements Callable<String> {
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        ActivateManagerTask(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public String call() throws Exception {
            try {
                return HttpUtil.postPlain(this.a, this.b);
            } catch (Throwable e) {
                Log.e(ActivateManager.TAG, e.getMessage());
                throw new IOException("NETWORK STATUS IS CHECKED, NO NETWORK", e);
            }
        }
    }

    private class c implements Runnable {
        private final String a;
        private final int b;
        private final boolean c;

        public c(String str, int i, boolean z) {
            this.a = str;
            this.b = i;
            this.c = z;
        }

        public void run() {
            try {
                ActivateManager.this.activateInternal(this.a, this.b, this.c);
            } catch (Exception e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Scheduled activation failed: ");
                stringBuilder.append(e.getClass());
                stringBuilder.append("|");
                stringBuilder.append(e.getMessage());
                Log.e(ActivateManager.TAG, stringBuilder.toString());
            }
        }
    }

    public ActivateManager(Context context, IBaseConfig iBaseConfig) {
        this.context = context.getApplicationContext();
        this.config = iBaseConfig;
        if (!TextUtils.isEmpty(iBaseConfig.getAuthDomain())) {
            domain = iBaseConfig.getAuthDomain();
        }
        if (!TextUtils.isEmpty(iBaseConfig.getDeviceLicenseUri())) {
            deviceActivateUri = iBaseConfig.getDeviceLicenseUri();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("domain: ");
        stringBuilder.append(domain);
        stringBuilder.append(", deviceLicenseUri: ");
        stringBuilder.append(deviceActivateUri);
        Log.i(TAG, stringBuilder.toString());
    }

    private String activateInternal(String str, int i, boolean z) throws IOException, CallException, BaseException {
        String str2 = TAG;
        String str3 = "isSkipDecrypt";
        AssetManager assets = this.context.getAssets();
        JSONObject mergeActiveJSON = mergeActiveJSON(this.config, str);
        JniParam fillCommonAuthParam = fillCommonAuthParam(str);
        StringBuilder stringBuilder;
        try {
            fillCommonAuthParam.put(str3, Boolean.valueOf(z));
            mergeActiveJSON.put(str3, z);
            fillCommonAuthParam.put("activate_json_body", mergeActiveJSON.toString());
            fillCommonAuthParam.put("authMode", this.config.getAuthMode());
            if (this.config.getAuthMode().equals(Consts.AUTH_MODE_INSTANCE)) {
                fillCommonAuthParam.put("key_json_body", mergeKeyJSON(str).toString());
            }
            fillCommonAuthParam.put("deviceActivateURI", deviceActivateUri);
            str = null;
            switch (i) {
                case 100:
                    str = InferLiteJni.activate(this.context, assets, fillCommonAuthParam);
                    break;
                case 101:
                    str = SnpeJni.activate(this.context, assets, fillCommonAuthParam);
                    break;
                case 102:
                    str = DDKJni.activate(this.context, assets, fillCommonAuthParam);
                    break;
                case 104:
                    str = DavinciJni.activate(this.context, assets, fillCommonAuthParam);
                    break;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Activation succeed: ");
            stringBuilder.append(this.config.getAuthMode());
            Log.i(str2, stringBuilder.toString());
            return str;
        } catch (Throwable e) {
            stringBuilder = new StringBuilder();
            String str4 = "json error:";
            stringBuilder.append(str4);
            stringBuilder.append(e.getMessage());
            Log.e(str2, stringBuilder.toString(), e);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str4);
            stringBuilder2.append(e.getMessage());
            throw new CallException(Consts.EC_BASE_INTERNAL_ERROR, stringBuilder2.toString(), e);
        }
    }

    static byte[] deactivateInstanceByHttp(Context context, String str, String str2) throws BaseException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(domain);
        stringBuilder.append(URL_INSTANCE_DEACTIVATE);
        stringBuilder.append("?ak=");
        stringBuilder.append(str2);
        return requestByHttp(context, stringBuilder.toString(), str);
    }

    static byte[] deviceActivateByHttp(Context context, String str) throws BaseException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(domain);
        stringBuilder.append(deviceActivateUri);
        return requestByHttp(context, stringBuilder.toString(), str);
    }

    static byte[] generateKeyByHTTP(Context context, String str) throws BaseException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(domain);
        stringBuilder.append(URL_GENERATE_KEY);
        return requestByHttp(context, stringBuilder.toString(), str);
    }

    static String getBase64(byte[] bArr) {
        return Base64.encodeToString(bArr, 2);
    }

    static byte[] instanceActivateByHttp(Context context, String str, String str2) throws BaseException, IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(domain);
        stringBuilder.append(URL_INSTANCE_ACTIVATE);
        stringBuilder.append("?ak=");
        stringBuilder.append(str2);
        return requestByHttp(context, stringBuilder.toString(), str);
    }

    private JSONObject mergeActiveJSON(IBaseConfig iBaseConfig, String str) {
        JSONObject baseInfoJson = Util.getBaseInfoJson(iBaseConfig, true, null);
        if (baseInfoJson != null) {
            try {
                baseInfoJson.put("authMode", iBaseConfig.getAuthMode());
                baseInfoJson.put("serialKey", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return baseInfoJson;
    }

    private JSONObject mergeKeyJSON(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("serialKey", str);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return jSONObject;
    }

    private static byte[] requestByHttp(Context context, String requestBody, String requestUrl) throws BaseException, IOException {
        String statusKey = "status";
        String noNetworkMessage = "NETWORK STATUS IS CHECKED, NO NETWORK";
        String logTag = "ActivateManager";
        int errorCode = 1900;

        try {
            StringBuilder logMessageBuilder = new StringBuilder();
            logMessageBuilder.append("request body: ");
            logMessageBuilder.append(requestBody);
            logMessageBuilder.append(" | ");
            logMessageBuilder.append(requestUrl);
            String logMessage = logMessageBuilder.toString();
            android.util.Log.i(logTag, logMessage);

            FutureTask<String> futureTask = new FutureTask<>(new ActivateManagerTask(requestBody, requestUrl));
            Thread taskThread = new Thread(futureTask, "task");
            taskThread.start();
            String response = futureTask.get();

            if (HttpUtil.isOnline(context)) {
                if (response != null && !response.isEmpty()) {
                    JSONObject jsonResponse = new JSONObject(response);
                    int status = jsonResponse.getInt(statusKey);

                    if (status == 0) {
                        android.util.Log.i(logTag, "Server activation success");
                        String result = jsonResponse.getString("result");
                        return android.util.Base64.decode(result, 2);
                    } else {
                        String message = jsonResponse.getString("message");
                        StringBuilder errorMessageBuilder = new StringBuilder();
                        errorMessageBuilder.append("Response ");
                        errorMessageBuilder.append(status);
                        errorMessageBuilder.append(": ");
                        errorMessageBuilder.append(message);
                        String errorMessage = errorMessageBuilder.toString();
                        android.util.Log.e(logTag, errorMessage);
                        throw new BaseException(errorCode, "server activation failed: " + message);
                    }
                } else {
                    throw new BaseException(errorCode, "server return empty result");
                }
            } else {
                throw new IOException(noNetworkMessage);
            }
        } catch (Throwable e) {
            throw new BaseException(errorCode, e.getMessage(), e);
        }
    }


    private void scheduleInstanceActivation(String str, int i, boolean z) throws BaseException {
        instanceActivateExecutor = new ScheduledThreadPoolExecutor(1);
        instanceActivateExecutor.scheduleAtFixedRate(new c(str, i, z), this.config.getAuthInterval(), this.config.getAuthInterval(), TimeUnit.SECONDS);
    }

    public String activate(String str, int i, boolean z) throws IOException, CallException, BaseException {
        String activateInternal = activateInternal(str, i, z);
        if (this.config.getAuthMode().equals(Consts.AUTH_MODE_INSTANCE)) {
            scheduleInstanceActivation(str, i, z);
        }
        return activateInternal;
    }

    public JniParam fillCommonAuthParam(String str2) {
        JniParam jniParam = new JniParam();
        if (str2 == null) {
            str2 = "";
        }
        jniParam.put("serial", str2);
        jniParam.put("serialNo", str2);
        String[] strArr = new String[3];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.context.getFilesDir());
        String str3 = "/.baidu/easyedge";
        stringBuilder.append(str3);
        strArr[0] = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        stringBuilder.append(str3);
        strArr[1] = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory());
        stringBuilder.append(str3);
        strArr[2] = stringBuilder.toString();
        for (String str4 : strArr) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(str4);
            stringBuilder2.append("/licenses");
            File file = new File(stringBuilder2.toString());
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                String str5 = TAG;
                StringBuilder stringBuilder3;
                if (mkdirs) {
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("mkdir success :");
                    stringBuilder3.append(file.getAbsolutePath());
                    Log.i(str5, stringBuilder3.toString());
                } else {
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("mkdir error :");
                    stringBuilder3.append(file.getAbsolutePath());
                    Log.e(str5, stringBuilder3.toString());
                }
            }
        }
        jniParam.put("isAcceleration", Boolean.valueOf(this.config.isAcceleration()));
        jniParam.put("fileDir", strArr[0]);
        if (strArr.length > 1) {
            jniParam.put("externalFileDir", strArr[1]);
            jniParam.put("externalStorageDir", strArr[2]);
        }
        jniParam.put("authRequireSDCard", Boolean.valueOf(true));
        return jniParam;
    }

    public void terminate() {
        new Thread(new a(this)).start();
    }
}
