//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.base;

public class BaseException extends Exception {
	protected int a;

	public BaseException(int var1, String var2, Throwable var3) {
		super(var2, var3);
		this.a = var1;
	}

	public BaseException(int var1, String var2) {
		super(var2);
		this.a = var1;
	}

	public static BaseException transform(OutOfMemoryError var0) {
		return new BaseException(2012, "SDKException: out of memory:", var0);
	}

	public static BaseException transform(Exception var0, String var1) {
		String var2;
		int var3;
		if ((var3 = (var2 = var0.getMessage()).indexOf(58)) > 0) {
			int var4 = Integer.parseInt(var2.substring(0, var3));
			var2 = var2.substring(var3 + 1);
			return new BaseException(var4, var1 + var2);
		} else {
			return new BaseException(2901, var1 + var0.getMessage(), var0);
		}
	}

	public int getErrorCode() {
		return this.a;
	}
}
