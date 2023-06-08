//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.ddk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
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
import com.baidu.ai.edge.core.util.ImageUtil;
import com.baidu.ai.edge.core.util.TimerRecorder;
import java.util.ArrayList;
import java.util.List;

public class DDKManager extends BaseManager implements ClassifyInterface, DetectInterface {
	private int h;
	private DDKConfig i;

	public DDKManager(Context var1, DDKConfig var2, String var3) throws BaseException {
		super(var1, new DDKJni(), var2, var3);
		this.h = 0;
		this.i = var2;
		if (DDKJni.a() != null) {
			this.h = -1;
		} else {
			this.loadModelFromAssets(super.d);
			Log.i("DDKManager", "Build.HARDWARE: " + Build.HARDWARE);
			this.h = 2;
		}
	}

	private List<ClassificationResultModel> a(float[] var1) throws BaseException {
		return DDKJni.runMixModelSync(new DDKModelInfo(this.i.getImageHeight(), this.i.getImageWidth(), this.i.getCateNum()), var1);
	}

	private List<DetectionResultModel> d(Bitmap var1, float var2, e var3) throws BaseException {
		TimerRecorder.start();
		float[] var4 = DDKJni.getPixels(ImageUtil.resize(var1, this.i.getImageWidth(), this.i.getImageHeight()), this.i.getImgMeans(), this.i.getScales(), this.i.isHWC(), this.i.isRGB());
		DDKModelInfo var5;
		DDKModelInfo var10000 = var5 = new DDKModelInfo(this.i.getImageHeight(), this.i.getImageWidth(), this.i.getCateNum());
		float[] var10001 = var4;
		Bitmap var10002 = var1;
		int var19 = var1.getWidth();
		int var20 = var10002.getHeight();
		TimerRecorder.start();
		long var21 = TimerRecorder.end();
		Log.i("DDKManager", "[stat] detect preprocessTime time:" + var21);
		TimerRecorder.start();
		float[] var7 = DDKJni.runMixModelDetectSync(var10000, var10001);
		long var8 = TimerRecorder.end();
		Log.i("DDKManager", "[stat] forwardTime time:" + var8);
		TimerRecorder.start();
		ArrayList var10;
		var10 = new ArrayList();

		for(int var11 = 0; var11 < var7.length; var11 += 6) {
			float var12;
			if (!((var12 = var7[var11 + 4]) < var2)) {
				float var13;
				int var14 = (int)(var7[var11] * (var13 = (float)var19));
				float var15;
				int var16 = (int)(var7[var11 + 1] * (var15 = (float)var20));
				int var22 = (int)(var7[var11 + 2] * var13);
				int var24 = (int)(var7[var11 + 3] * var15);
				Rect var17;
				var17 = new Rect(var14, var16, var22, var24);
				var22 = (int)var7[var11 + 5];
				DetectionResultModel var23;
				DetectionResultModel var25 = var23 = new DetectionResultModel();
				var23.setBounds(var17);
				var23.setConfidence(var12);
				var23.setLabelIndex(var22);
				var25.setLabel(this.i.getLabels()[var22]);
				var10.add(var25);
			}
		}

		long var18 = TimerRecorder.end();
		if (var3 != null) {
			var3.setPreprocessTime(var21);
			var3.setForwardTime(var8);
			var3.setPostprocessTime(var18);
			var3.setResultModel(var10);
		}

		return var10;
	}

	private List<ClassificationResultModel> c(Bitmap var1, float var2, e var3) throws BaseException {
		if (this.h == 2) {
			TimerRecorder.start();
			float[] var10001 = DDKJni.getPixels(ImageUtil.resize(var1, this.i.getImageWidth(), this.i.getImageHeight()), this.i.getImgMeans(), this.i.getScales(), this.i.isHWC(), this.i.isRGB());
			long var4 = TimerRecorder.end();
			Log.i("DDKManager", "[stat] preprocess time:" + var4);
			TimerRecorder.start();
			List var11 = this.a(var10001);
			long var6 = TimerRecorder.end();
			Log.i("DDKManager", "[stat] forward time:" + var6);
			TimerRecorder.start();
			ArrayList var8;
			var8 = new ArrayList();

			for(int var9 = 0; var9 < var11.size(); ++var9) {
				ClassificationResultModel var10;
				if (!((var10 = (ClassificationResultModel)var11.get(var9)).getConfidence() < var2)) {
					var10.setLabel(this.i.getLabels()[var10.getLabelIndex()]);
					var8.add(var10);
				}
			}

			long var12 = TimerRecorder.end();
			if (var3 != null) {
				var3.setPreprocessTime(var4);
				var3.setForwardTime(var6);
				var3.setPostprocessTime(var12);
				var3.setResultModel(var8);
			}

			this.c();
			return var8;
		} else {
			throw DDKJni.a();
		}
	}

	public void loadModelFromAssets(String var1) throws BaseException {
		JniParam var2 = this.b();
		if (this.i.getModelFileAssetPath() != null) {
			var2.put("modelFileAssetPath", this.i.getModelFileAssetPath());
			Log.i("DDKManager", "load 200 model file: " + this.i.getModelFileAssetPath());
			Context var10000 = super.b;
			if (DDKJni.loadMixModelSync(var10000, var10000.getAssets(), var2) != 0) {
				throw new BaseException(3001, "加载DDK模型文件失败");
			}
		} else {
			throw new BaseException(3001, "该模型在您的设备上不可用");
		}
	}

	public List<DetectionResultModel> detect(Bitmap var1, float var2) throws BaseException {
		return this.d(var1, var2, (e)null);
	}

	public List<DetectionResultModel> detect(Bitmap var1) throws BaseException {
		return this.detect(var1, this.i.getRecommendedConfidence());
	}

	protected float[] a(Bitmap var1, JniParam var2, int var3) {
		return new float[0];
	}

	public IStatisticsResultModel detectPro(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();
		this.d(var1, this.i.getRecommendedConfidence(), var2);
		return var10000;
	}

	protected void e() throws CallException {
		DDKJni.b();
	}

	public void destroy() throws BaseException {
		int var10000 = DDKJni.unloadMixModelSync();
		DDKJni.deactivateInstance(super.b);
		if (var10000 == 0) {
			super.destroy();
		} else {
			throw new BaseException(3002, "卸载DDK模型失败");
		}
	}

	public List<ClassificationResultModel> classify(Bitmap var1) throws BaseException {
		return this.classify(var1, this.i.getRecommendedConfidence());
	}

	public List<ClassificationResultModel> classify(Bitmap var1, float var2) throws BaseException {
		return this.c(var1, var2, (e)null);
	}

	public IStatisticsResultModel classifyPro(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();
		this.c(var1, this.i.getRecommendedConfidence(), var2);
		return var10000;
	}
}
