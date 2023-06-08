//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.ddk;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.base.ISDKJni;
import com.baidu.ai.edge.core.base.JniParam;
import com.baidu.ai.edge.core.classify.ClassificationResultModel;
import java.io.IOException;
import java.util.List;

public class DDKJni implements ISDKJni {
	private static CallException a;

	public DDKJni() {
	}

	public static CallException a() {
		return a;
	}

	public static boolean b() throws CallException {
		try {
			System.loadLibrary("edge-ddk");
			return true;
		} catch (Throwable var2) {
			a = new CallException(3000, "加载DDK so文件失败", var2);
			throw a;
		}
	}

	public static native float[] getPixels(Bitmap var0, float[] var1, float[] var2, boolean var3, boolean var4);

	public static native int loadMixModelSync(Context var0, AssetManager var1, JniParam var2);

	public static native int unloadMixModelSync();

	public static native List<ClassificationResultModel> runMixModelSync(DDKModelInfo var0, float[] var1) throws BaseException;

	public static native float[] runMixModelDetectSync(DDKModelInfo var0, float[] var1) throws BaseException;

	public static native String activate(Context var0, AssetManager var1, JniParam var2) throws BaseException, IOException;

	public static native void deactivateInstance(Context var0);

	public native String getStatJson(String var1);
}
