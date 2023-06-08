//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.ddk;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.BaseManager;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.IStatisticsResultModel;
import com.baidu.ai.edge.core.base.JniParam;
import com.baidu.ai.edge.core.base.e;
import com.baidu.ai.edge.core.classify.ClassificationResultModel;
import com.baidu.ai.edge.core.classify.ClassifyInterface;
import com.baidu.ai.edge.core.detect.DetectInterface;
import com.baidu.ai.edge.core.detect.DetectionResultModel;
import java.util.List;

public class DavinciManager extends BaseManager implements ClassifyInterface, DetectInterface {
	private static volatile boolean j;
	private DDKDaVinciConfig h;
	private long i;

	public DavinciManager(Context var1, DDKDaVinciConfig var2, String var3) throws Throwable {
		super(var1, new DavinciJni(), var2, var3);
		Class var17 = DavinciManager.class;
		synchronized(DavinciManager.class){}

		Throwable var10000;
		boolean var18;
		boolean var20;
		try {
			var18 = j;
		} catch (Throwable var15) {
			var10000 = var15;
			var20 = false;
			throw var10000;
		}

		if (!var18) {
			DDKDaVinciConfig var19;
			DavinciManager var10003;
			DDKDaVinciConfig var10004;
			DDKDaVinciConfig var21;
			DDKDaVinciConfig var22;
			try {
				var19 = var2;
				var21 = var2;
				var22 = var2;
				var10003 = this;
				var10004 = var2;
				j = true;
			} catch (Throwable var13) {
				var10000 = var13;
				var20 = false;
				throw var10000;
			}

			var10003.h = var10004;
			var22.setAutoCheckNpu(true);
			var21.isSupportDavinciNpu();
			if (!TextUtils.isEmpty(var19.getModelFileAssetPath())) {
				Log.i("DavinciManager", "getModelFileAssetPath: " + var2.getModelFileAssetPath());
				this.f();
			} else {
				throw new CallException(2702, "model asset file path is NULL");
			}
		} else {
			try {
				throw new CallException(2001, "only one active instance of DavinciManager is allowed, please destory() the old one");
			} catch (Throwable var14) {
				var10000 = var14;
				var20 = false;
				throw var10000;
			}
		}
	}

	private void f() throws BaseException {
		JniParam var1;
		(var1 = this.b()).put("modelName", "params");
		Context var10000 = super.b;
		long var3;
		if ((var3 = DavinciJni.loadModelSync(var10000, var10000.getAssets(), var1)) != 0L) {
			this.i = var3;
		} else {
			throw new BaseException(3003, "加载DDK-Davinci模型文件失败");
		}
	}

	private List<ClassificationResultModel> c(Bitmap var1, float var2, e var3) throws BaseException {
		return this.a(var1, var2, var3);
	}

	private List<DetectionResultModel> d(Bitmap var1, float var2, e var3) throws BaseException {
		return this.b(var1, var2, var3);
	}

	protected void e() throws CallException {
		DavinciJni.a();
	}

	public List<ClassificationResultModel> classify(Bitmap var1) throws BaseException {
		return this.classify(var1, this.h.getRecommendedConfidence());
	}

	public List<ClassificationResultModel> classify(Bitmap var1, float var2) throws BaseException {
		return this.c(var1, var2, (e)null);
	}

	public IStatisticsResultModel classifyPro(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();

		this.c(var1, this.h.getRecommendedConfidence(), var2);
		return var10000;
	}

	public List<DetectionResultModel> detect(Bitmap var1) throws BaseException {
		return this.detect(var1, this.h.getRecommendedConfidence());
	}

	public List<DetectionResultModel> detect(Bitmap var1, float var2) throws BaseException {
		return this.d(var1, var2, (e)null);
	}

	public IStatisticsResultModel detectPro(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();

		this.d(var1, this.h.getRecommendedConfidence(), var2);
		return var10000;
	}

	protected float[] a(Bitmap var1, JniParam var2, int var3) throws BaseException {
		var2.put("modelName", "params");
		return DavinciJni.runModelSync(this.i, var2, var1);
	}

	public void destroy() throws BaseException {
		this.a();
		j = false;
		DavinciJni.unloadModelSync(this.i);
		DavinciJni.deactivateInstance(super.b);
		this.i = 0L;
		super.destroy();
	}
}
