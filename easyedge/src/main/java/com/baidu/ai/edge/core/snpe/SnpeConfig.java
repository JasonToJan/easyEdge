//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.snpe;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.CallException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SnpeConfig extends BaseConfig implements ISnpeConfig, SnpeRuntimeInterface {
	private boolean w;
	protected int[] x;

	public SnpeConfig(AssetManager var1, String var2) throws Throwable {
		super(var1, var2);
		if (super.m) {
			super.a = var2 + "/params.enc";
		} else {
			super.a = var2 + "/params";
		}

	}

	protected void a(JSONObject var1, JSONObject var2) throws JSONException {
		super.a(var1, var2);
		if (this.x == null) {
			this.x = new int[]{2, 1, 3, 0};
		}

		this.w = var1.optBoolean("autocheck_qcom", true);
		JSONArray var4;
		int var5;
		if ((var4 = var1.optJSONArray("snpe_runtimes_order")) != null && (var5 = var4.length()) > 0) {
			this.x = new int[var5];

			for(int var3 = 0; var3 < var5; ++var3) {
				this.x[var3] = var4.getInt(var3);
			}
		}

		super.v.a("HWC");
	}

	public boolean isAutocheckQcom() {
		return this.w;
	}

	public void setAutocheckQcom(boolean var1) {
		this.w = var1;
	}

	public int[] getSnpeRuntimesOrder() {
		return (int[])this.x.clone();
	}

	public void setSnpeRuntimesOrder(int[] var1) {
		this.x = (int[])var1.clone();
	}

	public String getSoc() {
		return "dsp";
	}
}
