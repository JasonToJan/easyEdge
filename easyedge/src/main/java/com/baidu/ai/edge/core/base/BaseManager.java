//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.base;

import a.a.a.a.a.a.aaa;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;

import androidx.core.content.ContextCompat;

import com.baidu.ai.edge.core.classify.ClassificationResultModel;
import com.baidu.ai.edge.core.ddk.DDKManager;
import com.baidu.ai.edge.core.ddk.DavinciManager;
import com.baidu.ai.edge.core.detect.DetectionResultModel;
import com.baidu.ai.edge.core.infer.InferManager;
import com.baidu.ai.edge.core.snpe.SnpeManager;
import com.baidu.ai.edge.core.util.ImageUtil;
import com.baidu.ai.edge.core.util.TimeRecorderNew;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseManager {
	public static final String VERSION = "0.10.7";
	protected ActivateManager a;
	protected Context b;
	protected BaseConfig c;
	protected String d;
	private aaa e;
	protected String f;
	private boolean g = false;

	public BaseManager(Context var1, ISDKJni var2, BaseConfig var3, String var4) throws CallException {
		this.a(var1);
		this.c = var3;
		this.d = var4;
		this.f = this.getClass().getSimpleName();
		boolean var5;
		if (var5 = this.d()) {
			this.d = null;
		} else if (var4 == null) {
			Log.e("BaseManager", "serial number is NULL");
			throw new CallException(5004, "serial number is NULL");
		}

		BaseManager var10001 = this;
		String var10002 = var4;
		boolean var10003 = var5;
		this.b = var1;
		Log.i("BaseManager", "new Manager with " + var3.getMid() + " " + var3.getRid());
		this.a = new ActivateManager(var1, var3);
		this.e();

		String var9;
		try {
			var9 = var10001.a(var10002, var10003);
		} catch (Exception var6) {
			String var7 = var6.getMessage();
			Throwable var8 = var6.getCause();
			throw new CallException(5003, var7, var8);
		}

		var4 = var9;
		if (var3.getRid() != -1 && var3.getMid() != -1 && var3.getModelEncValue() != 1200) {
			this.e = new aaa(var1, var2, var3, var4);
			this.e.c();
		}

	}

	private String a(String var1, boolean var2) throws IOException, BaseException {

		if (this instanceof InferManager) {
			Log.e("TEST##", "BaseManager....1");
			return this.a.activate(var1, 100, var2);
		} else if (this instanceof SnpeManager) {
			Log.e("TEST##", "BaseManager....2");
			return this.a.activate(var1, 101, var2);
		} else if (this instanceof DDKManager) {
			Log.e("TEST##", "BaseManager....3");
			return this.a.activate(var1, 102, var2);
		} else {
			Log.e("TEST##", "BaseManager....4");
			return this instanceof DavinciManager ? this.a.activate(var1, 104, var2) : null;
		}
	}

	private void a(Context var1) throws CallException {
		String[] var5;
		String[] var10000 = var5 = new String[4];
		var10000[0] = "android.permission.WRITE_EXTERNAL_STORAGE";
		var10000[1] = "android.permission.INTERNET";
		var10000[2] = "android.permission.ACCESS_NETWORK_STATE";
		var10000[3] = "android.permission.READ_PHONE_STATE";
		int var2 = var10000.length;

		for(int var3 = 0; var3 < var2; ++var3) {
			String var4;
			if (ContextCompat.checkSelfPermission(var1, var4 = var5[var3]) != 0) {
				throw new CallException(1003, "Please allow permission:" + var4);
			}
		}

		if (android.os.Build.VERSION.SDK_INT >= 30 && !Environment.isExternalStorageManager()) {
			throw new CallException(1003, "Please allow all files access:");
		}
	}

	protected abstract void e() throws CallException;

	protected boolean d() {
		String var1;
		if ((var1 = this.c.getAuthType()) != null && !var1.isEmpty()) {
			return "no-auth".equals(var1);
		} else {
			return "EasyEdge-Free".equals(this.c.getProduct());
		}
	}

	public IStatisticsResultModel detectPro(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();
		this.b(var1, this.c.getRecommendedConfidence(), var2);
		return var10000;
	}

	public List<DetectionResultModel> detect(Bitmap var1, float var2) throws BaseException {
		return this.b(var1, var2, (e)null);
	}

	public List<DetectionResultModel> detect(Bitmap var1) throws BaseException {
		return this.detect(var1, this.c.getRecommendedConfidence());
	}

	public IStatisticsResultModel classifyPro(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();
		this.a(var1, this.c.getRecommendedConfidence(), var2);
		return var10000;
	}

	public List<ClassificationResultModel> classify(Bitmap var1) throws BaseException {
		return this.classify(var1, this.c.getRecommendedConfidence());
	}

	public List<ClassificationResultModel> classify(Bitmap var1, float var2) throws BaseException {
		return this.a(var1, var2, (e)null);
	}

	protected List<DetectionResultModel> b(Bitmap var1, float var2, e var3) throws BaseException {
		int[] var4 = new int[2];
		c var5;
		float[] var6 = (var5 = this.a(var1, var2, var3, 2, var4)).b();
		com.baidu.ai.edge.core.detect.a var7;
		var7 = new com.baidu.ai.edge.core.detect.a(this.getClass(), this.c, var1.getWidth(), var1.getHeight(), var4[0], var4[1], var5.a());
		TimeRecorderNew var8;
		TimeRecorderNew var10001 = var8 = new TimeRecorderNew();

		List var9 = var7.a(var6, var2);
		long var10 = var10001.end();
		if (var3 != null) {
			var3.setPostprocessTime(var10);
			var3.setResultModel(var9);
		}

		return var9;
	}

	protected List<ClassificationResultModel> a(Bitmap var1, float var2, e var3) throws BaseException {
		int[] var4 = new int[2];
		float[] var5 = this.a(var1, var2, var3, 2, var4).b();
		com.baidu.ai.edge.core.classify.a var6;
		var6 = new com.baidu.ai.edge.core.classify.a(this.getClass(), this.c, var1.getWidth(), var1.getHeight(), var4[0], var4[1]);
		TimeRecorderNew var7;
		TimeRecorderNew var10001 = var7 = new TimeRecorderNew();
		List var8 = var6.a(var5, var2);
		long var9 = var10001.end();
		if (var3 != null) {
			var3.setPostprocessTime(var9);
			var3.setResultModel(var8);
		}

		return var8;
	}

	protected c a(Bitmap var1, float var2, IStatisticsResultModel var3, int var4, int[] var5) throws BaseException {
		float var10003 = var2;
		this.a();
		Log.i(this.f, "predict " + var4 + ": confidence: " + var2);
		TimeRecorderNew var10;
		var10 = new TimeRecorderNew();
		JniParam var12;
		JniParam var10000 = var12 = this.a(var1, var4, var10003, var5);
		float[] var8 = this.a(var1, var12, var4);
		c var11;
		var11 = new c(var8);
		long var6 = var10000.getLong("preprocessEndTime");
		if (var10000.containsKey("extraNetFlag")) {
			var11.a((int)var12.getLong("extraNetFlag"));
		}

		long var9 = var10.checkpoint(var6);
		Log.i(this.f, "[stat]preprocess time: " + var9);
		var6 = var10.end();
		Log.i(this.f, "[stat]forward time: " + var6);
		if (var3 != null) {
			var3.setPreprocessTime(var9);
			var3.setForwardTime(var6);
		}

		this.c();
		return var11;
	}

	protected abstract float[] a(Bitmap var1, JniParam var2, int var3) throws BaseException;

	protected JniParam a(Bitmap var1, int[] var2, int var3) {
		d var4 = this.c.getPreprocessConfig();
		Pair var5 = null;
		if (var3 == 100) {
			var5 = ImageUtil.calcWithStep(var1, this.c.getMaxSize(), 32);
		} else if (this.c.getNType() != 102 && this.c.getNType() != 900102 && this.c.getNType() != 2010 && !"keep_ratio".equalsIgnoreCase(this.c.getPreprocessConfig().r())) {
			if (this.c.getNType() == 11002) {
				var5 = ImageUtil.calcShrinkSize(var1.getWidth(), var1.getHeight());
			} else if ("keep_ratio2".equalsIgnoreCase(var4.r())) {
				var5 = ImageUtil.calcTargetSizeForKeepRatio2(var1, var4.q(), var4.p());
			}
		} else {
			var5 = ImageUtil.calcTarget(var1, var4.t(), var4.g());
		}

		int var7 = var4.q();
		int var8 = var4.p();
		if (var5 != null) {
			var7 = (Integer)var5.first;
			var8 = (Integer)var5.second;
		}

		if (var2 != null) {
			byte var11 = 0;
			int var6;
			if (var4.u()) {
				var6 = var4.q();
			} else {
				var6 = var7;
			}

			var2[var11] = var6;
			var11 = 1;
			if (var4.u()) {
				var6 = var4.p();
			} else {
				var6 = var8;
			}

			var2[var11] = var6;
		}

		JniParam var9 = com.baidu.ai.edge.core.base.b.a(var4, var7, var8);
		if (var3 == 100) {
			long var10 = (long)var4.j();
			var9.put("ocrRecWidth", var10);
			var10 = (long)var4.i();
			var9.put("ocrRecHeight", var10);
			var10 = (long)var4.h();
			var9.put("ocrRecBatchNum", var10);
		}

		return var9;
	}

	protected JniParam a(Bitmap var1, int var2, float var3, int[] var4) {
		JniParam var5;
		JniParam var10000 = var5 = new JniParam();
		BaseManager var10002 = this;
		BaseManager var10004 = this;
		BaseManager var10008 = this;
		BaseManager var10012 = this;

		var5.put("product", this.c.j);
		long var6 = (long)var1.getWidth();
		var5.put("originWidth", var6);
		var6 = (long)var1.getHeight();
		var5.put("originHeight", var6);
		var5.put("preprocessObj", var10012.a(var1, var4, var2));
		var6 = (long)var2;
		var5.put("modelType", var6);
		var6 = (long)var10008.c.getNType();
		var5.put("nType", var6);
		var5.put("confidence", var3);
		var5.put("extraDetection", var10004.c.getExtraDetectionJson());
		var6 = (long)var10002.c.getLabels().length;
		var10000.put("classNum", var6);
		return var10000;
	}

	protected List<DetectionResultModel> a(float[] var1, String[] var2, int var3, int var4) {
		int var14 = var1.length / 7;
		ArrayList var5;
		var5 = new ArrayList(var14);

		for(int var6 = 0; var6 < var14; ++var6) {
			int var7;
			int var8;
			String var9;
			if ((var8 = Math.round(var1[(var7 = var6 * 7) + 1])) >= 0 && var8 < var2.length) {
				var9 = var2[var8];
			} else {
				Log.e("SnpeManager", "label index out of bound , index : " + var8 + " ,at :" + var6);
				var9 = "UNKNOWN";
			}

			float var15 = var1[var7 + 2];
			float var10;
			int var11 = Math.round(var1[var7 + 3] * (var10 = (float)var3));
			float var12;
			int var13 = Math.round(var1[var7 + 4] * (var12 = (float)var4));
			int var16 = Math.round(var1[var7 + 5] * var10);
			int var17 = Math.round(var1[var7 + 6] * var12);
			DetectionResultModel var10001 = new DetectionResultModel(var9, var15, new Rect(var11, var13, var16, var17));
			var10001.setLabelIndex(var8);
			var5.add(var10001);
		}

		return var5;
	}

	protected void c() {
		aaa var1;
		if ((var1 = this.e) != null) {
			var1.a();
		}

	}

	protected void destroy() throws BaseException {
		this.g = true;
		this.a.terminate();
		Log.i("InferManager", "pointer destroy");
		aaa var1;
		if ((var1 = this.e) != null) {
			var1.b();
		}

	}

	protected JniParam b() {
		JniParam var1;
		JniParam var10000 = var1 = this.a.fillCommonAuthParam(this.d);
		JniParam var10003 = var1;
		JniParam var10005 = var1;
		JniParam var10007 = var1;
		JniParam var10009 = var1;
		long var3 = (long)this.c.getModelEncValue();
		var10009.put("modelEncVal", var3);
		var3 = (long)this.c.getModelType();
		var10007.put("modelType", var3);
		var10005.put("modelFileAssetPath", this.c.getModelFileAssetPath());
		var3 = (long)this.c.getNType();
		var10003.put("nType", var3);
		var10000.put("skipDecrypt", this.d());
		return var10000;
	}

	protected void a() throws CallException {
		if (this.g) {
			throw new CallException(2002, "this instance is destoryed");
		}
	}

	protected String a(int var1) {
		String[] var2 = this.c.getLabels();
		return var1 >= 0 && var1 < var2.length ? var2[var1] : "UNKNOWN";
	}
}
