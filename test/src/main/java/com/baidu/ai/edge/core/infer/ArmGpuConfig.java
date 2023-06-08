//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.infer;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.CallException;

public class ArmGpuConfig extends InferConfig {
	private boolean z;

	public ArmGpuConfig(AssetManager var1, String var2) throws CallException {
		super(var1, var2);
		if (super.m) {
			super.a = var2 + "/params.enc";
		} else {
			super.a = var2 + "/params";
		}

	}

	public void setOpenclTune(boolean var1) {
		this.z = var1;
	}

	public boolean isOpenclTune() {
		return this.z;
	}

	public String getSoc() {
		return "arm-gpu";
	}
}
