//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.ddk;

import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.CallException;

public class DDKDaVinciConfig extends BaseConfig {
	private boolean w = true;

	public DDKDaVinciConfig(AssetManager var1, String var2) throws Throwable {
		super(var1, var2);
		if (super.m) {
			super.a = var2 + "/params.enc";
		} else {
			super.a = var2 + "/params";
		}

	}

	public boolean isAutoCheckNpu() {
		return this.w;
	}

	public void setAutoCheckNpu(boolean var1) {
		this.w = var1;
	}

	public boolean isSupportDavinciNpu() throws CallException {
		String var1;
		if (this.w && !(var1 = Build.HARDWARE.toLowerCase()).contains("kirin810") && !var1.contains("kirin990") && !var1.contains("kirin820") && !var1.contains("kirin985")) {
			Log.e("DDKDaVinciConfig", "Your device does NOT support Davinci:" + var1);
			throw new CallException(7001, "Your device does NOT support Davinci: " + var1);
		} else {
			return true;
		}
	}

	public String getSoc() {
		return "npu-vinci";
	}
}
