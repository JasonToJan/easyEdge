//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.detect;

import android.graphics.Rect;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.BaseManager;
import com.baidu.ai.edge.core.ddk.DDKManager;
import com.baidu.ai.edge.core.ddk.DavinciManager;
import com.baidu.ai.edge.core.infer.InferManager;
import com.baidu.ai.edge.core.snpe.SnpeManager;
import java.util.ArrayList;
import java.util.List;

public class a {
	private int a;
	private int b;
	private int c;
	private int d;
	private int e;
	private BaseConfig f;
	private int g;
	private int h;
	private int i;
	private int j;
	private int k;
	private boolean l = false;
	private boolean m = false;

	public a(Class<? extends BaseManager> var1, BaseConfig var2, int var3, int var4, int var5, int var6, int var7) {
		this.f = var2;
		this.g = var3;
		this.h = var4;
		this.i = var5;
		this.j = var6;
		this.k = var7;
		this.a = var2.getNType();
		if (var1.equals(InferManager.class)) {
			this.b = 0;
			this.d = 1;
			this.c = 2;
			this.e = 6;
			this.l = true;
		} else {
			if (var1.equals(DDKManager.class)) {
				return;
			}

			if (var1.equals(SnpeManager.class)) {
				return;
			}

			if (!var1.equals(DavinciManager.class)) {
				throw new RuntimeException("DetectPostProcess class not support " + var1.getSimpleName());
			}

			this.b = 1;
			this.d = 2;
			this.c = 3;
			this.e = 7;
			this.l = true;
			this.m = true;
		}

	}

	private int a(float var1, int var2) {
		int var3;
		if ((var3 = this.a) == 101 || var3 == 102 || var3 == 109 || var3 == 110) {
			if (this.k == 3) {
				return Math.round(var1);
			}

			if (var2 % 2 == 0) {
				var1 /= (float)this.i;
			} else {
				var1 /= (float)this.j;
			}
		}

		return Math.round(var2 % 2 == 0 ? var1 * (float)this.g : var1 * (float)this.h);
	}

	public List<DetectionResultModel> a(float[] var1, float var2) {
		ArrayList var3;
		var3 = new ArrayList();
		String[] var4 = this.f.getLabels();
		int var5 = var1.length;
		int var6;
		if (this.l) {
			if ((var6 = var5 / (var6 = this.e) * var6) != var5) {
				Log.w("DetectPostProcess", "output length is " + var5);
			}
		} else {
			var6 = var5;
		}

		for(var5 = 0; var5 < var6; var5 += this.e) {
			float var7 = var1[var5 + this.d];
			if (this.m && (double)var7 < 0.01) {
				break;
			}

			if (!(var7 < var2)) {
				int var8;
				if ((var8 = Math.round(var1[var5 + this.b])) < 0) {
					Log.e("DetectPostProcess", "label index out of bound , index : " + var8 + " ,at :" + var5);
				} else {
					String var9;
					if (var8 < var4.length) {
						var9 = var4[var8];
					} else {
						var9 = "UNKNOWN:" + var8;
					}

					int var10;
					int var10003 = var10 = var5 + this.c;
					int var10006 = var10;
					int var10009 = var10;
					var10 = this.a(var1[var10], 0);
					int var11 = this.a(var1[var10009 + 1], 1);
					int var12 = this.a(var1[var10006 + 2], 2);
					int var13 = this.a(var1[var10003 + 3], 3);
					DetectionResultModel var10001 = new DetectionResultModel(var9, var7, new Rect(var10, var11, var12, var13));
					var10001.setLabelIndex(var8);
					var3.add(var10001);
				}
			}
		}

		return var3;
	}
}
