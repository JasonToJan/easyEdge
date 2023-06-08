//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.base;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseResultModel implements Comparable<BaseResultModel>, IBaseResultModel {
	protected int a;
	protected String b;
	protected float c;

	public BaseResultModel() {
	}

	public BaseResultModel(String var1, float var2) {
		this.b = var1;
		this.c = var2;
	}

	public BaseResultModel(int var1, float var2) {
		this.a = var1;
		this.c = var2;
	}

	public int getLabelIndex() {
		return this.a;
	}

	public void setLabelIndex(int var1) {
		this.a = var1;
	}

	public String getLabel() {
		return this.b;
	}

	public void setLabel(String var1) {
		this.b = var1;
	}

	public float getConfidence() {
		return this.c;
	}

	public void setConfidence(float var1) {
		this.c = var1;
	}

	public JSONObject toJsonObject() {
		JSONObject var10000 = new JSONObject();
		JSONObject var1;
		JSONObject var10001 = var1 = var10000;

		String var10002 = "label";

		Exception var6;
		label37: {
			boolean var7;
			try {
				var10001.put(var10002, this.b);
			} catch (JSONException var4) {
				var6 = var4;
				var7 = false;
				break label37;
			}

			String var8 = "confidence";

			float var9;
			try {
				var9 = this.c;
			} catch (Exception var3) {
				var6 = var3;
				var7 = false;
				break label37;
			}

			double var10 = (double)var9;

			try {
				var10000.put(var8, var10);
				return var1;
			} catch (JSONException var2) {
				var6 = var2;
				var7 = false;
			}
		}

		Exception var5 = var6;
		Log.e("BaseResultModel", "json serialize error", var5);
		return var1;
	}

	public int compareTo(@NonNull BaseResultModel var1) {
		return Float.valueOf(var1.getConfidence()).compareTo(this.c);
	}
}
