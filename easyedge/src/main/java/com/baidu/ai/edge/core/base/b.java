package com.baidu.ai.edge.core.base;

import com.baidu.ai.edge.core.util.ImageUtil;

public class b {
    public static JniParam a(d dVar, int i, int i2) {
        JniParam jniParam = new JniParam();
        jniParam.put("width", (long) i);
        jniParam.put("height", (long) i2);
        jniParam.put("prepWidth", (long) dVar.q());
        jniParam.put("prepHeight", (long) dVar.p());
        jniParam.put("padded_width", (long) ImageUtil.calPaddedValue(i, dVar.o()));
        jniParam.put("padded_height", (long) ImageUtil.calPaddedValue(i2, dVar.o()));
        jniParam.put("imgMeans", dVar.f());
        jniParam.put("scales", dVar.s());
        jniParam.put("colorFormat", dVar.d());
        jniParam.put("rescaleMode", dVar.r());
        jniParam.put("isWarpAffineKeepRes", Boolean.valueOf(dVar.x()));
        jniParam.put("channelOrder", dVar.c());
        jniParam.put("isRGB", Boolean.valueOf(dVar.d().equals("RGB")));
        jniParam.put("isHWC", Boolean.valueOf(dVar.c().equals("HWC")));
        jniParam.put("paddings", (long) dVar.o());
        jniParam.put("isLetterbox", Boolean.valueOf(dVar.u()));
        jniParam.put("isSkipNorm", Boolean.valueOf(dVar.w()));
        jniParam.put("centerCropWidth", (long) dVar.b());
        jniParam.put("centerCropHeight", (long) dVar.a());
        jniParam.put("isPadding", Boolean.valueOf(dVar.v()));
        jniParam.put("paddingMode", dVar.m());
        jniParam.put("paddingFillWidth", (long) dVar.l());
        jniParam.put("paddingFillHeight", (long) dVar.k());
        jniParam.put("paddingScalar", dVar.n());
        return jniParam;
    }
}
