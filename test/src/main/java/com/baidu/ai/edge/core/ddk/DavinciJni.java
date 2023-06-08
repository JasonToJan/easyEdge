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
import java.io.IOException;

public class DavinciJni implements ISDKJni {
	private static CallException a;

	public DavinciJni() {
	}

	public static void a() throws CallException {
		try {
			System.loadLibrary("hiai-500");
			System.loadLibrary("edge-davinci");
		} catch (Throwable var2) {
			a = new CallException(3000, "加载DDK-Davinci so文件失败", var2);
			throw a;
		}
	}

	public static native String activate(Context var0, AssetManager var1, JniParam var2) throws BaseException, IOException;

	public static native long loadModelSync(Context var0, AssetManager var1, JniParam var2) throws BaseException;

	public static native float[] runModelSync(long var0, JniParam var2, Bitmap var3) throws BaseException;

	public static native int unloadModelSync(long var0);

	public static native void deactivateInstance(Context var0);

	public native String getStatJson(String var1);
}
