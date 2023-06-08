//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.infer;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.util.Util;

public class InferConfig extends BaseConfig {
	private String w;
	private String x;
	private int y = 0;

	public InferConfig(AssetManager var1, String var2) throws Throwable {
		super(var1, var2);
		if (super.i == 100) {
			super.a = var2 + "/model";
			this.x = var2 + "/params";
			super.l = true;
		} else if (super.m) {
			if (super.l) {
				super.a = var2 + "/params.enc";
			} else {
				super.a = var2 + "/model.enc";
				this.w = var2 + "/params.enc";
			}
		} else if (super.l) {
			super.a = var2 + "/params";
		} else {
			super.a = var2 + "/model";
			this.w = var2 + "/params";
		}

	}

	public int getThread() {
		if (this.y == 0) {
			this.y = Util.getInferCores();
		}

		return this.y;
	}

	public void setThread(int var1) {
		this.y = var1;
	}

	public String getParamFileAssetPath() {
		return this.w;
	}

	public void setParamFileAssetPath(String var1) {
		this.w = var1;
	}

	public String getExtraModelFilePath() {
		return this.x;
	}

	public void setExtraModelFilePath(String var1) {
		this.x = var1;
	}

	public String getSoc() {
		return "arm";
	}
}
