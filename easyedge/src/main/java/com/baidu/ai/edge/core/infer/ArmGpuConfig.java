package com.baidu.ai.edge.core.infer;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.Consts;

public class ArmGpuConfig extends InferConfig {
    private boolean z;

    public ArmGpuConfig(AssetManager assetManager, String str) throws CallException {
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
        return Consts.SOC_ARM_GPU;
    }

    public boolean isOpenclTune() {
        return this.z;
    }

    public void setOpenclTune(boolean z) {
        this.z = z;
    }
}
