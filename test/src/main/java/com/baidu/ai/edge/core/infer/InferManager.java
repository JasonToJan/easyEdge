//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.infer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.BaseManager;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.IStatisticsResultModel;
import com.baidu.ai.edge.core.base.JniParam;
import com.baidu.ai.edge.core.base.e;
import com.baidu.ai.edge.core.classify.ClassificationResultModel;
import com.baidu.ai.edge.core.classify.ClassifyInterface;
import com.baidu.ai.edge.core.detect.DetectException;
import com.baidu.ai.edge.core.detect.DetectInterface;
import com.baidu.ai.edge.core.detect.DetectionResultModel;
import com.baidu.ai.edge.core.ocr.OcrInterface;
import com.baidu.ai.edge.core.ocr.OcrResultModel;
import com.baidu.ai.edge.core.pose.PoseInterface;
import com.baidu.ai.edge.core.pose.PoseResultModel;
import com.baidu.ai.edge.core.segment.SegmentInterface;
import com.baidu.ai.edge.core.segment.SegmentationResultModel;
import com.baidu.ai.edge.core.util.ImageUtil;
import com.baidu.ai.edge.core.util.TimeRecorderNew;
import com.baidu.ai.edge.core.util.TimerRecorder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class InferManager extends BaseManager implements ClassifyInterface, DetectInterface, SegmentInterface, OcrInterface, PoseInterface {
	private static volatile boolean k;
	private InferConfig h;
	private long i;
	private int j;

	public InferManager(Context var1, InferConfig var2, String var3) throws Throwable {

		super(var1, new InferLiteJni(), var2, var3);
		Class var17 = InferManager.class;
		synchronized(InferManager.class){}

		Throwable var10000;
		boolean var10001;
		boolean var18;
		try {
			var18 = k;
		} catch (Throwable var16) {
			var10000 = var16;
			var10001 = false;
			throw var10000;
		}

		if (!var18) {
			InferConfig var19;
			try {
				var19 = var2;
				k = true;
			} catch (Throwable var14) {
				var10000 = var14;
				var10001 = false;
				throw var10000;
			}

			if (var19.getNType() != 102 && var2.getNType() != 900102 && var2.getNType() != 2010) {
				var2.getPreprocessConfig().a(0);
			} else {
				var2.getPreprocessConfig().a(32);
			}

			this.h = var2;
			if (var2.getModelFileAssetPath() != null) {
				if (!var2.isOptModel() && var2.getParamFileAssetPath() == null) {
					throw new CallException(2702, "param asset file path is NULL");
				} else {
					Log.i("InferManager", "infer thread: " + var2.getThread());
					Log.i("InferManager", "infer getParamFileAssetPath: " + var2.getParamFileAssetPath());
					Log.i("InferManager", "infer getModelFileAssetPath: " + var2.getModelFileAssetPath());
					this.a(var1, super.d);
				}
			} else {
				throw new CallException(2702, "model asset file path is NULL");
			}
		} else {
			try {
				throw new CallException(2001, "only one active instance of InferManager is allowed, please destory() the old one");
			} catch (Throwable var15) {
				var10000 = var15;
				var10001 = false;
				throw var10000;
			}
		}
	}

	public static boolean isSupportOpencl() throws CallException {
		return InferLiteJni.a();
	}

	private void a(Context var1, String var2) throws BaseException {
		JniParam var3;
		JniParam var10001 = var3 = this.b();
		long var4 = (long)this.h.getThread();
		var10001.put("thread", var4);
		if (this.j == 3) {
			var3.put("opencl_tune", ((ArmGpuConfig)super.c).isOpenclTune());
		}

		String var12 = "InferManager";
		StringBuilder var5 = (new StringBuilder()).append("serial num length is ");
		Integer var10;
		if (var2 == null) {
			var10 = null;
		} else {
			var10 = var2.length();
		}

		Log.i(var12, var5.append(var10).toString());
		int var11;
		if ((var11 = this.h.getModelType()) == 100) {
			if (this.h.getExtraModelFilePath() == null) {
				throw new DetectException(2701, "extraModelFileAssetPath must be set for this model type :" + var11);
			}

			var3.put("extraModelFileAssetPath", this.h.getExtraModelFilePath());
		}

		label44: {
			Exception var10000;
			label52: {
				boolean var13;
				boolean var15;
				try {
					var13 = this.h.isOptModel();
				} catch (Exception var9) {
					var10000 = var9;
					var15 = false;
					break label52;
				}

				InferManager var14;
				long var16;
				if (var13) {
					try {
						var14 = this;
						var16 = InferLiteJni.loadCombinedMemoryNB(var1, var1.getAssets(), var3);
					} catch (Exception var8) {
						var10000 = var8;
						var15 = false;
						break label52;
					}
				} else {
					var14 = this;
					Context var17 = var1;
					Context var10002 = var1;
					JniParam var10003 = var3;
					String var10004 = "paramFileAssetPath";

					try {
						var10003.put(var10004, this.h.getParamFileAssetPath());
						var16 = InferLiteJni.loadCombinedMemoryUC(var17, var10002.getAssets(), var3);
					} catch (Exception var7) {
						var10000 = var7;
						var15 = false;
						break label52;
					}
				}

				try {
					var14.i = var16;
					break label44;
				} catch (Exception var6) {
					var10000 = var6;
					var15 = false;
				}
			}

			var10000.printStackTrace();
			throw BaseException.transform(var10000, "init models failed:");
		}

		Log.i("InferManager", "loadCombinedMemory success isOptModel: " + this.h.isOptModel());
	}

	private ArrayList<OcrResultModel> a(float[] var1, float var2) {
		ArrayList var3;
		var3 = new ArrayList();

		int var5;
		int var6;
		for(int var4 = 0; var4 < var1.length; var4 += var5 * 2 + 3 + var6) {
			var5 = Math.round(var1[var4]);
			var6 = Math.round(var1[var4 + 1]);
			int var7;
			if (var1[var7 = var4 + 2] >= var2) {
				var3.add(this.a(var1, var7, var5, var6));
			}
		}

		return var3;
	}

	private OcrResultModel a(float[] var1, int var2, int var3, int var4) {
		OcrResultModel var5;
		OcrResultModel var10000 = var5 = new OcrResultModel();

		var10000.setConfidence(var1[var2]);
		++var2;

		int var6;
		int var7;
		for(var6 = 0; var6 < var3; ++var6) {
			var5.addPoints(Math.round(var1[var7 = var2 + var6 * 2]), Math.round(var1[var7 + 1]));
		}

		int var9 = var2 + var3 * 2;
		StringBuffer var10;
		var10 = new StringBuffer(var4);
		String[] var11 = this.h.getLabels();

		for(var6 = 0; var6 < var4; ++var6) {
			int var13 = var7 = Math.round(var1[var9 + var6]);
			var5.addWordIndex(var7);
			String var8;
			if (var13 < var11.length) {
				var8 = var11[var7];
			} else {
				var8 = "?";
			}

			var10.append(var8);
			if (var7 >= var11.length) {
				Log.e("InferManager", "UNKNOWN index :" + var7 + "; total:" + var11.length);
			}
		}

		var5.setLabel(var10.toString());
		return var5;
	}

	private List<PoseResultModel> b(Bitmap var1, float var2, IStatisticsResultModel var3) throws BaseException {
		List var4 = this.a(this.a(var1, var2, var3, 402, new int[2]).b(), var1);
		long var5 = (new TimeRecorderNew()).end();
		if (var3 != null) {
			var3.setPostprocessTime(var5);
			var3.setResultModel(var4);
		}

		return var4;
	}

	private List<PoseResultModel> a(float[] var1, Bitmap var2) {
		ArrayList var3;
		var3 = new ArrayList();
		boolean var4;
		if (this.h.getNType() == 20041) {
			var4 = true;
		} else {
			var4 = false;
		}

		int var7;
		for(int var5 = 0; var5 < var1.length; var5 = var7) {
			PoseResultModel var6;
			PoseResultModel var10002 = var6 = new PoseResultModel();
			var6.setLabelIndex(0);
			var6.setLabel(this.a(0));
			var6.setHasGroups(var4);
			int var10004 = var7 = var5 + 1;
			var6.setIndex(Math.round(var1[var5]));
			var5 = var7 + 1;
			var10002.setGroupIndex(Math.round(var1[var10004]));
			int var15 = var7 = var5 + 1;
			var6.setConfidence(var1[var5]);
			var5 = var7 + 1;
			var7 = Math.round(var1[var15] * (float)var2.getWidth());
			int var8;
			int var10001 = var8 = var5 + 1;
			var5 = Math.round(var1[var5] * (float)var2.getHeight());
			var6.setPoint(new Point(var7, var5));
			ArrayList var12;
			var12 = new ArrayList();
			var7 = var8 + 1;
			var8 = Math.round(var1[var10001]);

			int var13;
			for(int var9 = 0; var9 < var8; var7 = var13) {
				PoseResultModel var10;
				PoseResultModel var14 = var10 = new PoseResultModel();

				int var11;
				int var10006 = var11 = var7 + 1;
				var10.setIndex(Math.round(var1[var7]));
				var10.setGroupIndex(var6.getGroupIndex());
				var7 = var11 + 1;
				var10.setConfidence(var1[var10006]);
				var10004 = var13 = var7 + 1;
				var7 = Math.round(var1[var7] * (float)var2.getWidth());
				++var13;
				var11 = Math.round(var1[var10004] * (float)var2.getHeight());
				var14.setPoint(new Point(var7, var11));
				var12.add(var14);
				++var9;
			}

			var6.setPairs(var12);
			var3.add(var6);
		}

		return var3;
	}

	private List<ClassificationResultModel> c(Bitmap var1, float var2, e var3) throws BaseException {
		Bitmap var10003 = var1;
		this.a();
		float[] var9;
		float[] var10005 = var9 = new float[4];
		var9[0] = 1.0F;
		var9[1] = 3.0F;
		var9[2] = (float)this.h.getImageHeight();
		var10005[3] = (float)this.h.getImageWidth();
		TimerRecorder.start();
		float[] var4 = InferLiteJni.getPixels(ImageUtil.resize(var10003, this.h.getImageWidth(), this.h.getImageHeight()), this.h.getImgMeans(), this.h.getScales(), this.h.isHWC(), this.h.isRGB(), 0);
		long var5 = TimerRecorder.end();
		Log.i("InferManager classify", "[stat] preprocess time:" + var5);
		TimerRecorder.start();
		float[] var10002 = InferLiteJni.predictImage(this.i, var4, var9, this.h.getNType());
		long var10 = TimerRecorder.end();
		Log.i("InferManager classify", "[stat] forward time:" + var10);
		TimerRecorder.start();
		List var11 = this.a(var10002, var2, this.h.getLabels());
		long var7 = TimerRecorder.end();
		Log.i("InferManager classify", "[stat] postprocessTime time:" + var7);
		if (var3 != null) {
			var3.setPreprocessTime(var5);
			var3.setForwardTime(var10);
			var3.setPostprocessTime(var7);
			var3.setResultModel(var11);
		}

		this.c();
		return var11;
	}

	private List<DetectionResultModel> a(Bitmap var1, float var2, IStatisticsResultModel var3) throws BaseException {
		this.a();
		Log.i("InferManager detect", "detect confidence: " + var2);
		TimerRecorder.start();
		byte var4 = 0;
		float[] var5;
		float[] var10001 = var5 = new float[4];
		var10001[0] = 1.0F;
		var10001[1] = 3.0F;
		Pair var6 = null;
		Log.i("InferManager detect", "[preprocess] detect target: " + this.h.getTargetSize());
		float[] var23;
		if (this.h.getNType() != 102 && this.h.getNType() != 900102) {
			if (this.h.getNType() == 11002) {
				Pair var7;
				int var8;
				int var28 = var8 = (Integer)(var7 = ImageUtil.calcShrinkSize(var1.getWidth(), var1.getHeight())).first;
				int var25;
				int var10003 = var25 = (Integer)var7.second;
				var23 = InferLiteJni.getPixels(ImageUtil.resize(var1, var8, var25), this.h.getImgMeans(), this.h.getScales(), this.h.isHWC(), this.h.isRGB(), var4);
				var5[2] = (float)var10003;
				var5[3] = (float)var28;
			} else {
				var23 = InferLiteJni.getPixels(ImageUtil.resize(var1, this.h.getImageWidth(), this.h.getImageHeight()), this.h.getImgMeans(), this.h.getScales(), this.h.isHWC(), this.h.isRGB(), 0);
				var5[2] = (float)this.h.getImageHeight();
				var5[3] = (float)this.h.getImageWidth();
			}
		} else {
			var5[2] = (float)((Bitmap)(var6 = ImageUtil.resizeTarget(var1, this.h.getTargetSize(), this.h.getMaxSize())).first).getHeight();
			var5[3] = (float)((Bitmap)var6.first).getWidth();
			var23 = InferLiteJni.getPixels((Bitmap)var6.first, this.h.getImgMeans(), this.h.getScales(), this.h.isHWC(), this.h.isRGB(), 32);
			Log.i("pixel size", "" + var23.length);
		}

		long var26 = TimerRecorder.end();
		Log.i("InferManager detect", "[stat] preprocess time:" + var26);
		TimerRecorder.start();
		var10001 = var23 = InferLiteJni.predictImage(this.i, var23, var5, this.h.getNType());
		long var9 = TimerRecorder.end();
		Log.i("InferManager detect", "[stat] forward time:" + var9);
		TimerRecorder.start();
		byte var24 = 6;
		int var11 = var10001.length / var24;
		ArrayList var12;
		var12 = new ArrayList(var11);
		String[] var13 = this.h.getLabels();

		for(int var14 = 0; var14 < var11; ++var14) {
			int var15;
			int var16 = Math.round(var23[(var15 = var14 * var24) + 0]);
			int var17;
			int var18;
			int var19;
			int var20;
			if (this.h.getNType() == 101) {
				var17 = Math.round(var23[var15 + 2] / (float)this.h.getImageWidth() * (float)var1.getWidth());
				var18 = Math.round(var23[var15 + 3] / (float)this.h.getImageHeight() * (float)var1.getHeight());
				var19 = Math.round(var23[var15 + 4] / (float)this.h.getImageWidth() * (float)var1.getWidth());
				var20 = Math.round(var23[var15 + 5] / (float)this.h.getImageHeight() * (float)var1.getHeight());
			} else if (this.h.getNType() == 102 && var6 != null) {
				var17 = Math.round(var23[var15 + 2] / (float)((Bitmap)var6.first).getWidth() * (float)var1.getWidth());
				var18 = Math.round(var23[var15 + 3] / (float)((Bitmap)var6.first).getHeight() * (float)var1.getHeight());
				var19 = Math.round(var23[var15 + 4] / (float)((Bitmap)var6.first).getWidth() * (float)var1.getWidth());
				var20 = Math.round(var23[var15 + 5] / (float)((Bitmap)var6.first).getHeight() * (float)var1.getHeight());
			} else {
				var17 = Math.round(var23[var15 + 2] * (float)var1.getWidth());
				var18 = Math.round(var23[var15 + 3] * (float)var1.getHeight());
				var19 = Math.round(var23[var15 + 4] * (float)var1.getWidth());
				var20 = Math.round(var23[var15 + 5] * (float)var1.getHeight());
			}

			if (var16 >= 0 && var16 < var13.length) {
				float var27;
				if (!((var27 = var23[var15 + 1]) < var2)) {
					String var21 = var13[var16];
					DetectionResultModel var29 = new DetectionResultModel(var21, var27, new Rect(var17, var18, var19, var20));
					var29.setLabelIndex(var16);
					var12.add(var29);
				}
			} else {
				Log.e("InferManager", "label index out of bound , index : " + var16 + " ,at :" + var14);
			}
		}

		long var22 = TimerRecorder.end();
		Log.i("InferManager detect", "[stat] postprocess time:" + var22);
		if (var3 != null) {
			var3.setPreprocessTime(var26);
			var3.setForwardTime(var9);
			var3.setPostprocessTime(var22);
			var3.setResultModel(var12);
		}

		this.c();
		return var12;
	}

	private List<ClassificationResultModel> a(float[] var1, float var2, String[] var3) {
		ArrayList var4;
		var4 = new ArrayList();

		for(int var5 = 0; var5 < var1.length; ++var5) {
			String var6;
			if (var5 < var3.length) {
				var6 = var3[var5];
			} else {
				var6 = "UnKnown:" + var5;
			}

			ClassificationResultModel var7;
			ClassificationResultModel var10000 = var7 = new ClassificationResultModel(var6, var1[var5]);

			if (!(var10000.getConfidence() < var2)) {
				var7.setLabelIndex(var5);
				var4.add(var7);
			}
		}

		Collections.sort(var4, new Comparator<ClassificationResultModel>() {
			public int compare(ClassificationResultModel var1, ClassificationResultModel var2) {
				float var3;
				if ((var3 = var2.getConfidence() - var1.getConfidence()) > 0.0F) {
					return 1;
				} else {
					return var3 < 0.0F ? -1 : 0;
				}
			}
		});
		return var4;
	}

	public synchronized void destroy() throws BaseException {
		this.a();
		k = false;
		InferLiteJni.clear(this.i);
		InferLiteJni.deactivateInstance(super.b);
		this.i = 0L;
		super.destroy();
	}

	protected void e() throws CallException {
		if (super.c instanceof ArmGpuConfig) {
			this.j = 3;
		} else {
			this.j = 0;
		}

		InferLiteJni.a(this.j);
	}

	protected float[] a(Bitmap var1, JniParam var2, int var3) throws BaseException {
		return InferLiteJni.predictNew(this.i, var1, var2);
	}

	public IStatisticsResultModel detectPro(Bitmap var1) throws BaseException {
		return super.detectPro(var1);
	}

	public IStatisticsResultModel classifyPro(Bitmap var1) throws BaseException {
		return super.classifyPro(var1);
	}

	public List<SegmentationResultModel> segment(Bitmap var1) throws BaseException {
		return this.segment(var1, this.h.getRecommendedConfidence());
	}

	public List<SegmentationResultModel> segment(Bitmap var1, float var2) throws BaseException {
		return this.segmentInternal(var1, var2, (e)null);
	}

	public List<SegmentationResultModel> segmentInternal(Bitmap var1, float var2, e var3) throws BaseException {
		this.a();
		TimeRecorderNew var4;
		var4 = new TimeRecorderNew();
		JniParam var13 = this.a(var1, super.c.getModelType(), var2, (int[])null);
		ArrayList var12;
		ArrayList var10000 = var12 = InferLiteJni.predictImageSegmentNew(this.i, var1, var13);
		TimeRecorderNew var10002 = var4;
		long var15 = var4.checkpoint(var13.getLong("preprocessEndTime"));
		Log.i("InferManager", "[stat]preprocess time: " + var15);
		long var6 = var10002.end();
		Log.i("InferManager", "[stat]forward time: " + var6);
		String[] var14 = this.h.getLabels();

		SegmentationResultModel var9;
		String var11;
		for(Iterator var8 = var10000.iterator(); var8.hasNext(); var9.setLabel(var11)) {
			int var10;
			if ((var10 = (var9 = (SegmentationResultModel)var8.next()).getLabelIndex()) >= 0 && var10 < var14.length) {
				var11 = var14[var10];
			} else {
				var11 = "UNKNOWN:" + var10;
			}
		}

		Log.i("InferManager segment", "segment result size " + var12.size());
		if (var3 != null) {
			var3.setPreprocessTime(var15);
			var3.setForwardTime(var6);
			var3.setResultModel(var12);
		}

		this.c();
		return var12;
	}

	public List<OcrResultModel> ocr(Bitmap var1) throws BaseException {
		return this.ocr(var1, this.h.getRecommendedConfidence());
	}

	public List<OcrResultModel> ocr(Bitmap var1, float var2) throws BaseException {
		return this.ocrInternal(var1, var2, (IStatisticsResultModel)null);
	}

	public List<OcrResultModel> ocrInternal(Bitmap var1, float var2, IStatisticsResultModel var3) throws BaseException {
		this.a();
		TimeRecorderNew var4;
		TimeRecorderNew var10001 = var4 = new TimeRecorderNew();
		int[] var5 = new int[2];
		JniParam var11 = this.a(var1, 100, var2, var5);
		float[] var10003 = InferLiteJni.predictImageOcrNew(this.i, var1, var11);
		long var9 = var4.checkpoint(var11.getLong("preprocessEndTime"));
		Log.i("InferManager", "[stat]preprocess time: " + var9);
		long var10 = var4.checkpoint();
		Log.i("InferManager", "[stat]forward time: " + var10);
		ArrayList var6 = this.a(var10003, var2);
		long var7 = var10001.end();
		Log.i("InferManager", "[stat] ocr postprocess time:" + var7);
		if (var3 != null) {
			var3.setPreprocessTime(var9);
			var3.setForwardTime(var10);
			var3.setPostprocessTime(var7);
			var3.setResultModel(var6);
		}

		this.c();
		return var6;
	}

	public List<PoseResultModel> pose(Bitmap var1) throws BaseException {
		return this.a(var1, 0.0F);
	}

	protected List<PoseResultModel> a(Bitmap var1, float var2) throws BaseException {
		return this.b(var1, var2, (IStatisticsResultModel)null);
	}

	public e posePro(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();

		this.b(var1, this.h.getRecommendedConfidence(), var2);
		return var10000;
	}

	public e segmentPro(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();

		this.segmentInternal(var1, this.h.getRecommendedConfidence(), var2);
		return var10000;
	}

	public List<OcrResultModel> ocrInternalOld(Bitmap var1, float var2, IStatisticsResultModel var3) throws BaseException {
		this.a();
		TimerRecorder.start();
		Bitmap var4;
		float[] var5 = InferLiteJni.getPixels(var4 = ImageUtil.resizeWithStep(var1, this.h.getMaxSize(), 32), this.h.getImgMeans(), this.h.getScales(), this.h.isHWC(), this.h.isRGB(), 0);
		long var6 = TimerRecorder.end();
		Log.i("InferManager", "[stat] ocr preprocess time:" + var6);
		TimerRecorder.start();
		float[] var8;
		float[] var10005 = var8 = new float[4];
		var8[0] = 1.0F;
		var8[1] = 3.0F;
		var8[2] = (float)var4.getHeight();
		var10005[3] = (float)var4.getWidth();
		Log.i("pixel size", "" + var5.length + " height " + var4.getHeight() + " ;width " + var4.getWidth());
		int var11 = var1.getPixel(var1.getWidth() - 1, var1.getHeight() - 1);
		Log.i("Predictor", "pixels " + var11 + " " + Color.red(var11) + " " + Color.blue(var11) + " " + Color.green(var11));
		float[] var10002 = InferLiteJni.predictImageOcr(this.i, var5, var8, var1);
		long var10 = TimerRecorder.end();
		TimerRecorder.start();
		Log.i("InferManager", "[stat] ocr forward time:" + var10);
		ArrayList var12 = this.a(var10002, var2);
		long var13 = TimerRecorder.end();
		Log.i("InferManager", "[stat] ocr postprocess time:" + var13);
		if (var3 != null) {
			var3.setPreprocessTime(var6);
			var3.setForwardTime(var10);
			var3.setPostprocessTime(var13);
			var3.setResultModel(var12);
		}

		this.c();
		return var12;
	}

	public List<DetectionResultModel> detectOld(Bitmap var1, float var2) throws BaseException {
		return this.a((Bitmap)var1, var2, (IStatisticsResultModel)null);
	}

	public IStatisticsResultModel detectProOld(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();
		this.a((Bitmap)var1, this.h.getRecommendedConfidence(), (IStatisticsResultModel)var2);
		return var10000;
	}

	public List<ClassificationResultModel> classifyOld(Bitmap var1, float var2) throws BaseException {
		return this.c(var1, var2, (e)null);
	}

	public e classifyProOld(Bitmap var1) throws BaseException {
		e var2;
		e var10000 = var2 = new e();
		this.c(var1, this.h.getRecommendedConfidence(), var2);
		return var10000;
	}

	public List<SegmentationResultModel> segmentOld(Bitmap var1, float var2) throws BaseException {
		return this.segmentInternalOld(var1, var2, (e)null);
	}

	public List<SegmentationResultModel> segmentInternalOld(Bitmap var1, float var2, e var3) throws BaseException {
		int var4 = this.h.getTargetSize();
		int var5 = this.h.getMaxSize();
		float[] var6;
		float[] var10001 = var6 = new float[4];
		Pair var10005 = ImageUtil.resizeTarget(var1, var4, var5);
		var1 = (Bitmap)var10005.first;
		float var14 = (Float)var10005.second;
		var10001[0] = 1.0F;
		var10001[1] = 3.0F;
		var10001[2] = (float)var1.getHeight();
		var10001[3] = (float)var1.getWidth();
		TimerRecorder.start();
		byte var16 = 0;
		if (this.h.getNType() == 2010) {
			var16 = 32;
		}

		float[] var11 = InferLiteJni.getPixels(ImageUtil.resize(var1, var1.getWidth(), var1.getHeight()), this.h.getImgMeans(), this.h.getScales(), this.h.isHWC(), this.h.isRGB(), var16);
		long var7 = TimerRecorder.end();
		Log.i("InferManager segment", "[stat] preprocess time:" + var7);
		Log.i("InferManager segment", "pixels：" + var11.length);
		TimerRecorder.start();
		ArrayList var12;
		ArrayList var10000 = var12 = InferLiteJni.predictImageSegment(this.i, var11, var6, var14, var2);
		long var15 = TimerRecorder.end();
		Log.i("InferManager segment", "[stat] forward time:" + var15);
		TimerRecorder.start();
		String[] var13 = this.h.getLabels();
		Iterator var17 = var10000.iterator();

		while(var17.hasNext()) {
			SegmentationResultModel var18 = (SegmentationResultModel)var17.next();
			var18.setLabel(var13[var18.getLabelIndex()]);
		}

		long var9 = TimerRecorder.end();
		Log.i("InferManager segment", "segment result size " + var12.size());
		if (var3 != null) {
			var3.setPreprocessTime(var7);
			var3.setForwardTime(var15);
			var3.setPostprocessTime(var9);
			var3.setResultModel(var12);
		}

		this.c();
		return var12;
	}

	public List<OcrResultModel> ocrOld(Bitmap var1, float var2) throws BaseException {
		return this.ocrInternalOld(var1, var2, (IStatisticsResultModel)null);
	}
}
