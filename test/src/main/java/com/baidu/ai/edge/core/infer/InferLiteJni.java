//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.infer;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.ISDKJni;
import com.baidu.ai.edge.core.base.JniParam;
import java.io.IOException;
import java.util.ArrayList;

public class InferLiteJni implements ISDKJni {
	public static boolean a;
	private static CallException b;

	public InferLiteJni() {
	}

	public static void a(int var0) throws CallException {
		Throwable var10000;
		boolean var10001;
		if (var0 != 0) {
			label124: {
				if (var0 != 3) {
					return;
				}

				boolean var14;
				try {
					var14 = a;
				} catch (Throwable var11) {
					var10000 = var11;
					var10001 = false;
					break label124;
				}

				if (var14) {
					return;
				}

				label113:
				try {
					System.loadLibrary("edge-infer-gpu");
					a = true;
					return;
				} catch (Throwable var10) {
					var10000 = var10;
					var10001 = false;
					break label113;
				}
			}
		} else {
			label120:
			try {
				System.loadLibrary("edge-infer");
				return;
			} catch (Throwable var12) {
				var10000 = var12;
				var10001 = false;
				break label120;
			}
		}

		Throwable var13 = var10000;
		b = new CallException(3000, "加载paddle so文件失败", var13);
		throw b;
	}

	public static native boolean checkOpenclSupport();

	public static boolean a() throws CallException {
		a(3);
		return checkOpenclSupport();
	}

	public static native float[] getPixels(Bitmap var0, float[] var1, float[] var2, boolean var3, boolean var4, int var5);

	public static native long loadCombinedMemoryUC(Context var0, AssetManager var1, JniParam var2);

	public static native long loadCombinedMemoryNB(Context var0, AssetManager var1, JniParam var2);

	public static native float[] predictImage(long var0, float[] var2, float[] var3, int var4) throws BaseException;

	public static native float[] predictImageOcr(long var0, float[] var2, float[] var3, Bitmap var4) throws BaseException;

	public static native float[] predictImageOcrNew(long var0, Bitmap var2, JniParam var3) throws BaseException;

	public static native ArrayList predictImageSegment(long var0, float[] var2, float[] var3, float var4, float var5) throws BaseException;

	public static native float[] predictNew(long var0, Bitmap var2, JniParam var3) throws BaseException;

	public static native ArrayList predictImageSegmentNew(long var0, Bitmap var2, JniParam var3) throws BaseException;

	public static native boolean clear(long var0);

	public static native String activate(Context var0, AssetManager var1, JniParam var2) throws BaseException, IOException;

	public static native void deactivateInstance(Context var0);

	public native String getStatJson(String var1);
}
