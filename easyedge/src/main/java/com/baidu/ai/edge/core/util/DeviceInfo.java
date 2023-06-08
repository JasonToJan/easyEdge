//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.File;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class DeviceInfo {
	private static final byte[] b = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
	private Context a;

	public DeviceInfo(Context var1) {
		this.a = var1;
	}

	public static String getAndroidVersion() {
		return VERSION.RELEASE;
	}

	public static String getSystemModel() {
		return Build.MODEL;
	}

	public static String getDeviceBrand() {
		return Build.BRAND;
	}

	private static String a(byte[] var0) {
		if (var0 != null && var0.length == 6) {
			byte[] var1 = new byte[17];
			int var2 = 0;

			for(int var3 = 0; var3 <= 5; ++var3) {
				byte var4 = var0[var3];
				byte[] var5;
				var1[var2] = (var5 = b)[(var4 & 240) >> 4];
				var1[var2 + 1] = var5[var4 & 15];
				if (var3 != 5) {
					var1[var2 + 2] = 58;
					var2 += 3;
				}
			}

			return new String(var1);
		} else {
			return "";
		}
	}

	public static Map<String, String> getMacArray() {
		LinkedHashMap var0;
		var0 = new LinkedHashMap();

		Exception var10000;
		label61: {
			Enumeration var11;
			boolean var10001;
			try {
				var11 = NetworkInterface.getNetworkInterfaces();
			} catch (Exception var9) {
				var10000 = var9;
				var10001 = false;
				break label61;
			}

			Enumeration var1 = var11;
			if (var11 == null) {
				return null;
			}

			while(true) {
				boolean var12;
				try {
					var12 = var1.hasMoreElements();
				} catch (Exception var8) {
					var10000 = var8;
					var10001 = false;
					break;
				}

				if (!var12) {
					return var0;
				}

				NetworkInterface var13;
				try {
					var13 = (NetworkInterface)var1.nextElement();
				} catch (Exception var7) {
					var10000 = var7;
					var10001 = false;
					break;
				}

				NetworkInterface var2 = var13;

				String var14;
				try {
					var14 = a(var13.getHardwareAddress());
				} catch (Exception var6) {
					var10000 = var6;
					var10001 = false;
					break;
				}

				String var3 = var14;

				try {
					var12 = var14.isEmpty();
				} catch (Exception var5) {
					var10000 = var5;
					var10001 = false;
					break;
				}

				if (!var12) {
					try {
						var0.put(var2.getName(), var3);
					} catch (Exception var4) {
						var10000 = var4;
						var10001 = false;
						break;
					}
				}
			}
		}

		Exception var10 = var10000;
		Log.e("tag", var10.getMessage(), var10);
		return var0;
	}

	public static String getSerial() {
		String var0;
		if (VERSION.SDK_INT < 26) {
			var0 = Build.SERIAL;
		} else {
			String var10000 = "android.os.Build";

			label36: {
				label32: {
					boolean var10001;
					Class var4;
					try {
						var4 = Class.forName(var10000);
					} catch (Exception var2) {
						var10001 = false;
						break label32;
					}

					Class var3 = var4;
					String var5 = "getSerial";

					try {
						var10000 = (String)var4.getMethod(var5).invoke(var3);
						break label36;
					} catch (Exception var1) {
						var10001 = false;
					}
				}

				var0 = "unknown";
				return var0;
			}

			var0 = var10000;
		}

		return var0;
	}

	public static String getPingModifiedTime() {
		long var0 = (new File("/bin/ping")).lastModified();
		SimpleDateFormat var2;
		SimpleDateFormat var10000 = var2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return var10000.format(var0);
	}

	public String getBundleId() {
		return this.a.getPackageName();
	}

	public String getDeviceId() throws Throwable{
		DeviceInfo var10000 = this;
		Class var59 = DeviceInfo.class;
		synchronized(DeviceInfo.class){}

		boolean var10001;
		Throwable var60;
		SharedPreferences var61;
		try {
			var61 = var10000.a.getSharedPreferences("easydl-app", 0);
		} catch (Throwable var58) {
			var60 = var58;
			var10001 = false;
			throw var60;
		}

		SharedPreferences var1 = var61;

		String var62;
		try {
			var62 = var61.getString("id", "");
		} catch (Throwable var57) {
			var60 = var57;
			var10001 = false;
			throw var60;
		}

		String var2 = var62;

		boolean var63;
		try {
			var63 = var62.isEmpty();
		} catch (Throwable var56) {
			var60 = var56;
			var10001 = false;
			throw var60;
		}

		if (var63) {
			String var64;
			try {
				var61 = var1;
				var64 = UUID.randomUUID().toString();
			} catch (Throwable var55) {
				var60 = var55;
				var10001 = false;
				throw var60;
			}

			var2 = var64;

			SharedPreferences.Editor var65;
			try {
				var65 = var61.edit();
				var65.putString("id", var2);
			} catch (Throwable var54) {
				var60 = var54;
				var10001 = false;
				throw var60;
			}

			try {
				var65.commit();
			} catch (Throwable var53) {
				var60 = var53;
				var10001 = false;
				throw var60;
			}
		}

		try {
			return var2;
		} catch (Throwable var52) {
			var60 = var52;
			var10001 = false;
			throw var60;
		}
	}
}
