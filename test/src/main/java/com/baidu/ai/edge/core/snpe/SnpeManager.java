//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.snpe;

import android.content.Context;
import android.graphics.Bitmap;
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
import java.util.Arrays;
import java.util.List;

public class SnpeManager extends BaseManager implements ClassifyInterface, DetectInterface {
	private static volatile boolean j;
	private static ArrayList<Integer> k;
	private SnpeConfig h;
	private long i;

	public SnpeManager(Context var1, SnpeConfig var2, String var3) throws Throwable {
		super(var1, new SnpeJni(), var2, var3);


		if (var2.isAutocheckQcom()) {
			if (!"qcom".equalsIgnoreCase(Build.HARDWARE)) {
				throw new SnpeQcomNotSupportException(4011, "Build.HARDWARE is not qcom;" + Build.HARDWARE);
			}

			Log.i("SnpeManager", "cpu support : " + Build.HARDWARE);
		}

		Class var30 = SnpeManager.class;
		synchronized(SnpeManager.class){}

		boolean var33;
		Throwable var10000;
		boolean var10001;
		try {
			var33 = j;
		} catch (Throwable var27) {
			var10000 = var27;
			var10001 = false;
			throw var10000;
		}

		if (!var33) {
			SnpeConfig var34;
			Class var35;
			try {
				var34 = var2;
				var35 = var30;
				getAvailableRuntimes(var1);
			} catch (Throwable var25) {
				var10000 = var25;
				var10001 = false;
				throw var10000;
			}

			try {
				j = true;
			} catch (Throwable var24) {
				var10000 = var24;
				var10001 = false;
				throw var10000;
			}

			byte var31 = -1;
			int[] var32;
			int var5 = (var32 = var34.getSnpeRuntimesOrder()).length;
			int var6 = 0;

			int var7;
			while(true) {
				if (var6 >= var5) {
					var7 = var31;
					break;
				}

				var7 = var32[var6];
				if (k.contains(var7)) {
					break;
				}

				++var6;
			}

			if (var7 >= 0) {
				this.h = var2;
				JniParam var28;
				JniParam var38 = var28 = this.b();
				long var29 = (long)var7;
				var38.put("runtime", var29);
				this.i = SnpeJni.a(var1, var1.getAssets(), var28);
			} else {
				throw new SnpeQcomNotSupportException(4012, "your allowed runtimes is:" + Arrays.toString(var2.getSnpeRuntimesOrder()));
			}
		} else {
			try {
				throw new CallException(2001, "only one active instance of SnpeManager is allowed, please destory() the old one");
			} catch (Throwable var26) {
				var10000 = var26;
				var10001 = false;
				throw var10000;
			}
		}
	}

	public static List<Integer> getAvailableRuntimes(Context var0) {
		if (k == null) {
			SnpeJni.setDspRuntimePath(var0.getApplicationInfo().nativeLibraryDir);
			k = SnpeJni.a();
		}

		return new ArrayList(k);
	}

	private List<ClassificationResultModel> a(float[] var1, float var2, String[] var3) {
		ArrayList var8;
		var8 = new ArrayList(var1.length / 2);

		for(int var4 = 0; var4 < var1.length / 2; ++var4) {
			int var10001 = var4 * 2;
			int var5 = Math.round(var1[var4 * 2]);
			float var6;
			if (!((var6 = var1[var10001 + 1]) < var2)) {
				String var7;
				if (var5 < var3.length) {
					var7 = var3[var5];
				} else {
					var7 = "UNKNOWN:" + var5;
				}

				ClassificationResultModel var9 = new ClassificationResultModel(var7, var6);
				var9.setLabelIndex(var5);
				var8.add(var9);
			}
		}

		return var8;
	}

	private List<ClassificationResultModel> c(Bitmap var1, float var2, e var3) throws BaseException {
		Bitmap var10003 = var1;
		this.a();
		SnpeConfig var9;
		SnpeConfig var10004 = var9 = this.h;
		TimerRecorder.start();
		float[] var4 = SnpeJni.getPixels(ImageUtil.resize(var10003, var10004.getImageWidth(), var9.getImageHeight()), var9.getImgMeans(), var9.getScales(), var9.isHWC(), var9.isRGB());
		long var5 = TimerRecorder.end();
		Log.i("SnpeManager", "[stat] preprocess time:" + var5);
		TimerRecorder.start();
		float[] var10002 = SnpeJni.a(this.i, var4, 0);
		var10004 = var9;
		long var10 = TimerRecorder.end();
		TimerRecorder.start();
		List var11 = this.a(var10002, var2, var10004.getLabels());
		long var7 = TimerRecorder.end();
		Log.i("SnpeManager", "[stat] forward time:" + var10);
		if (var3 != null) {
			var3.setPreprocessTime(var5);
			var3.setForwardTime(var10);
			var3.setPostprocessTime(var7);
			var3.setResultModel(var11);
		}

		this.c();
		return var11;
	}

	private List<DetectionResultModel> d(Bitmap var1, float var2, e var3) throws BaseException {
		this.a();
		SnpeConfig var4;
		SnpeConfig var10004 = var4 = this.h;
		TimerRecorder.start();
		float[] var13 = SnpeJni.getPixels(ImageUtil.resize(var1, var10004.getImageWidth(), var4.getImageHeight()), var4.getImgMeans(), var4.getScales(), var4.isHWC(), var4.isRGB());
		long var5 = TimerRecorder.end();
		Log.i("SnpeManager", "[stat] preprocess time:" + var5);
		TimerRecorder.start();
		float[] var12;
		float[] var10002 = var12 = SnpeJni.execute(this.i, var13, 1, var2);
		long var7 = TimerRecorder.end();
		TimerRecorder.start();
		Log.i("SnpeManager", "result " + var12.length);
		List var11 = this.a(var10002, this.h.getLabels(), var1.getWidth(), var1.getHeight());
		long var9 = TimerRecorder.end();
		Log.i("SnpeManager", "[stat] forward time:" + var7);
		if (var3 != null) {
			var3.setPreprocessTime(var5);
			var3.setForwardTime(var7);
			var3.setPostprocessTime(var9);
			var3.setResultModel(var11);
		}

		this.c();
		return var11;
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

	public List<DetectionResultModel> detect(Bitmap var1, float var2) throws BaseException {
		return this.d(var1, var2, (e)null);
	}

	public List<DetectionResultModel> detect(Bitmap var1) throws BaseException {
		return this.detect(var1, this.h.getRecommendedConfidence());
	}

	protected float[] a(Bitmap var1, JniParam var2, int var3) {
		return new float[0];
	}

	public IStatisticsResultModel detectPro(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();

		this.d(var1, this.h.getRecommendedConfidence(), var2);
		return var10000;
	}

	protected void e() throws CallException {
		SnpeJni.b();
	}

	public void destroy() throws BaseException {
		this.a();
		long var1;
		if ((var1 = this.i) != 0L) {
			SnpeJni.destory(var1);
			SnpeJni.deactivateInstance(super.b);
		}

		j = false;
		super.destroy();
	}
}
