//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.detect;

import android.graphics.Rect;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseResultModel;
import org.json.JSONException;
import org.json.JSONObject;

public class DetectionResultModel extends BaseResultModel implements IDetectionResultModel {
	private Rect d;

	public DetectionResultModel() {
	}

	public DetectionResultModel(String var1, float var2, Rect var3) {
		super(var1, var2);
		this.d = var3;
	}

	public void setBounds(Rect var1) {
		this.d = var1;
	}

	public Rect getBounds() {
		return this.d;
	}

	public String toString() {
		return "[" + super.b + "][" + super.c + "][" + this.d.toString() + "]";
	}

	public JSONObject toJsonObject() {
		JSONObject var10000 = super.toJsonObject();
		JSONObject var10001 = var10000;
		JSONObject var10002 = var10000;
		JSONObject var1;
		JSONObject var10003 = var1 = var10000;
		String var10004 = "bounds_left";

		JSONException var7;
		label45: {
			boolean var8;
			try {
				var10003.put(var10004, this.d.left);
			} catch (JSONException var5) {
				var7 = var5;
				var8 = false;
				break label45;
			}

			String var11 = "bounds_top";

			try {
				var10002.put(var11, this.d.top);
			} catch (JSONException var4) {
				var7 = var4;
				var8 = false;
				break label45;
			}

			String var9 = "bounds_right";

			try {
				var10001.put(var9, this.d.right);
			} catch (JSONException var3) {
				var7 = var3;
				var8 = false;
				break label45;
			}

			String var10 = "bounds_bottom";

			try {
				var10000.put(var10, this.d.bottom);
				return var1;
			} catch (JSONException var2) {
				var7 = var2;
				var8 = false;
			}
		}

		JSONException var6 = var7;
		Log.e("DetectionResultModel", "json serialize error", var6);
		return var1;
	}
}
