//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.classify;

import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.BaseManager;
import com.baidu.ai.edge.core.ddk.DDKManager;
import com.baidu.ai.edge.core.ddk.DavinciManager;
import com.baidu.ai.edge.core.infer.InferManager;
import com.baidu.ai.edge.core.snpe.SnpeManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class a {
	private BaseConfig a;
	private int b = 1;

	public a(Class<? extends BaseManager> var1, BaseConfig var2, int var3, int var4, int var5, int var6) {
		this.a = var2;
		var2.getNType();
		if (!var1.equals(InferManager.class) && !var1.equals(DavinciManager.class)) {
			if (!var1.equals(DDKManager.class)) {
				if (!var1.equals(SnpeManager.class)) {
					throw new RuntimeException("DetectPostProcess class not support " + var1.getSimpleName());
				}
			}
		} else {
			this.b = 1;
		}
	}

	private List<ClassificationResultModel> b(float[] var1, float var2) {
		ArrayList var3;
		var3 = new ArrayList();
		String[] var4 = this.a.getLabels();

		for(int var5 = 0; var5 < var1.length; ++var5) {
			if (!(var1[var5] < var2)) {
				String var6;
				if (var5 < var4.length && var5 >= 0) {
					var6 = var4[var5];
				} else {
					var6 = "UNKNOWN:" + var5;
				}

				ClassificationResultModel var10001 = new ClassificationResultModel(var6, var1[var5]);
				var10001.setLabelIndex(var5);
				var3.add(var10001);
			}
		}

		Collections.sort(var3, new Comparator<ClassificationResultModel>() {
			public int compare(ClassificationResultModel var1, ClassificationResultModel var2) {
				float var3;
				if ((var3 = var2.getConfidence() - var1.getConfidence()) > 0.0F) {
					return 1;
				} else {
					return var3 < 0.0F ? -1 : 0;
				}
			}
		});
		return var3;
	}

	public List<ClassificationResultModel> a(float[] var1, float var2) {
		return this.b == 1 ? this.b(var1, var2) : null;
	}
}
