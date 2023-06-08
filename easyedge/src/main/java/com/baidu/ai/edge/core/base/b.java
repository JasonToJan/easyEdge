//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.base;

import com.baidu.ai.edge.core.util.ImageUtil;

public class b {
	public static JniParam a(d var0, int var1, int var2) {
		JniParam var3;
		JniParam var10000 = var3 = new JniParam();
		d var10002 = var0;
		d var10004 = var0;
		d var10006 = var0;
		d var10008 = var0;
		d var10010 = var0;
		d var10012 = var0;
		d var10014 = var0;
		d var10016 = var0;
		d var10018 = var0;
		d var10020 = var0;
		d var10022 = var0;
		d var10024 = var0;
		d var10026 = var0;
		d var10028 = var0;
		d var10030 = var0;
		d var10032 = var0;
		d var10034 = var0;
		d var10036 = var0;
		d var10039 = var0;
		d var10042 = var0;
		d var10044 = var0;
		d var10046 = var0;

		long var4 = (long)var1;
		var3.put("width", var4);
		var4 = (long)var2;
		var3.put("height", var4);
		var4 = (long)var10046.q();
		var3.put("prepWidth", var4);
		var4 = (long)var10044.p();
		var3.put("prepHeight", var4);
		var4 = (long)ImageUtil.calPaddedValue(var1, var10042.o());
		var3.put("padded_width", var4);
		var4 = (long)ImageUtil.calPaddedValue(var2, var10039.o());
		var3.put("padded_height", var4);
		var3.put("imgMeans", var10036.f());
		var3.put("scales", var10034.s());
		var3.put("colorFormat", var10032.d());
		var3.put("rescaleMode", var10030.r());
		var3.put("isWarpAffineKeepRes", var10028.x());
		var3.put("channelOrder", var10026.c());
		var3.put("isRGB", var10024.d().equals("RGB"));
		var3.put("isHWC", var10022.c().equals("HWC"));
		var4 = (long)var10020.o();
		var3.put("paddings", var4);
		var3.put("isLetterbox", var10018.u());
		var3.put("isSkipNorm", var10016.w());
		var4 = (long)var10014.b();
		var3.put("centerCropWidth", var4);
		var4 = (long)var10012.a();
		var3.put("centerCropHeight", var4);
		var3.put("isPadding", var10010.v());
		var3.put("paddingMode", var10008.m());
		var4 = (long)var10006.l();
		var3.put("paddingFillWidth", var4);
		var4 = (long)var10004.k();
		var3.put("paddingFillHeight", var4);
		var10000.put("paddingScalar", var10002.n());
		return var10000;
	}
}
