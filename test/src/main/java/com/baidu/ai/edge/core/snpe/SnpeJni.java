//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.snpe;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.ISDKJni;
import com.baidu.ai.edge.core.base.JniParam;
import java.io.IOException;
import java.util.ArrayList;

public class SnpeJni implements ISDKJni, SnpeRuntimeInterface {
	private static CallException a;

	public SnpeJni() {
	}

	public static boolean b() throws CallException {
		try {
			System.loadLibrary("edge-snpe");
			return true;
		} catch (Throwable var2) {
			a = new CallException(3000, "加载snpe so文件失败", var2);
			throw a;
		}
	}

	public static ArrayList<Integer> a() {
		int var0 = getAvailableRuntimeInt();
		Log.i("SnpeJni", "runtime Int" + var0);
		ArrayList var1;
		var1 = new ArrayList();
		int[] var2;
		int var3 = (var2 = SnpeRuntimeInterface.RUNTIMES).length;

		for(int var4 = 0; var4 < var3; ++var4) {
			int var5 = var2[var4];
			if ((var0 & 1 << var5) != 0) {
				var1.add(var5);
			}
		}

		return var1;
	}

	public static long a(Context var0, AssetManager var1, JniParam var2) throws BaseException {
		try {
			return init(var0, var1, var2);
		} catch (OutOfMemoryError var3) {
			throw BaseException.transform(var3);
		} catch (Exception var4) {
			throw BaseException.transform(var4, "");
		}
	}

	public static native float[] getPixels(Bitmap var0, float[] var1, float[] var2, boolean var3, boolean var4);

	public static native boolean setDspRuntimePath(String var0);

	private static native int getAvailableRuntimeInt();

	public static native long init(Context var0, AssetManager var1, JniParam var2);

	public static float[] a(long var0, float[] var2, int var3) throws BaseException {
		return execute(var0, var2, var3, 0.0F);
	}

	public static native float[] execute(long var0, float[] var2, int var3, float var4) throws BaseException;

	public static native void destory(long var0);

	public static native String activate(Context var0, AssetManager var1, JniParam var2) throws BaseException, IOException;

	public static native void deactivateInstance(Context var0);

	public native String getStatJson(String var1);
}
