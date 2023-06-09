package com.baidu.ai.edge.core.ddk;

import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.Consts;

public class DDKDaVinciConfig extends BaseConfig {
    private boolean w = true;

    public DDKDaVinciConfig(AssetManager assetManager, String str) throws CallException {
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

    public String getSoc() {
        return Consts.SOC_NPU_VINCI;
    }

    public boolean isAutoCheckNpu() {
        return this.w;
    }

    public boolean isSupportDavinciNpu() throws CallException {
        if (this.w) {
            String toLowerCase = Build.HARDWARE.toLowerCase();
            if (!(toLowerCase.contains("kirin810") || toLowerCase.contains("kirin990") || toLowerCase.contains("kirin820") || toLowerCase.contains("kirin985"))) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Your device does NOT support Davinci:");
                stringBuilder.append(toLowerCase);
                Log.e("DDKDaVinciConfig", stringBuilder.toString());
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Your device does NOT support Davinci: ");
                stringBuilder2.append(toLowerCase);
                throw new CallException(Consts.EC_CHECK_NPU_FAIL, stringBuilder2.toString());
            }
        }
        return true;
    }

    public void setAutoCheckNpu(boolean z) {
        this.w = z;
    }
}
