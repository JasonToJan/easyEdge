//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.baidu.ai.edge.core.base.IBaseConfig;
import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {
	private static final FileFilter a = new FileFilter() {
		public boolean accept(File var1) {
			String var2;
			if (!(var2 = var1.getName()).startsWith("cpu")) {
				return false;
			} else {
				for(int var3 = 3; var3 < var2.length(); ++var3) {
					if (var2.charAt(var3) < '0' || var2.charAt(var3) > '9') {
						return false;
					}
				}

				return true;
			}
		}
	};

	private Util() {
	}

	public static String getTodayStr() {
		return (new SimpleDateFormat("yyyyMMdd")).format(new Date());
	}

	public static JSONObject getBaseInfoJson(IBaseConfig var0) {
		return getBaseInfoJson(var0, false, (String)null);
	}

	public static JSONObject getBaseInfoJson(IBaseConfig var0, boolean var1, String var2) {
		JSONObject var3;
		var3 = new JSONObject();

		Exception var10000;
		label211: {
			boolean var10001;
			JSONObject var35;
			try {
				var35 = new JSONObject();
			} catch (Exception var32) {
				var10000 = var32;
				var10001 = false;
				break label211;
			}

			JSONObject var10002 = var35;
			JSONObject var10003 = var35;
			JSONObject var4;
			JSONObject var10004 = var4 = var35;

			try {
				var10004 = new JSONObject();
				var10003.put("osType", "ANDROID");
			} catch (JSONException var31) {
				var10000 = var31;
				var10001 = false;
				break label211;
			}

			String var38 = "osVersion";

			try {
				var10002.put(var38, DeviceInfo.getAndroidVersion());
			} catch (JSONException var30) {
				var10000 = var30;
				var10001 = false;
				break label211;
			}

			String var37 = "manufacturer";

			try {
				var35.put(var37, DeviceInfo.getDeviceBrand() + " | " + DeviceInfo.getSystemModel());
			} catch (JSONException var29) {
				var10000 = var29;
				var10001 = false;
				break label211;
			}

			if (var2 != null) {
				try {
					var4.put("deviceId", var2);
				} catch (JSONException var28) {
					var10000 = var28;
					var10001 = false;
					break label211;
				}
			}

			IBaseConfig var36;
			try {
				var36 = var0;
				var35 = new JSONObject();
			} catch (Exception var27) {
				var10000 = var27;
				var10001 = false;
				break label211;
			}

			var10002 = var35;
			JSONObject var34;
			var10003 = var34 = var35;

			try {
				var10003 = new JSONObject();
				var10002.put("sdkVersion", "0.10.7");
			} catch (JSONException var26) {
				var10000 = var26;
				var10001 = false;
				break label211;
			}

			try {
				var35.put("sdkType", "Android");
			} catch (JSONException var25) {
				var10000 = var25;
				var10001 = false;
				break label211;
			}

			boolean var39;
			try {
				var39 = var36.getSoc().equals("xeye");
			} catch (Exception var24) {
				var10000 = var24;
				var10001 = false;
				break label211;
			}

			if (var39) {
				try {
					var34.put("sdkLevel", "XEYE");
				} catch (JSONException var23) {
					var10000 = var23;
					var10001 = false;
					break label211;
				}
			} else {
				try {
					var39 = var0.isAcceleration();
				} catch (Exception var22) {
					var10000 = var22;
					var10001 = false;
					break label211;
				}

				if (var39) {
					try {
						var34.put("sdkLevel", "ACCELERATION");
					} catch (JSONException var21) {
						var10000 = var21;
						var10001 = false;
						break label211;
					}
				} else {
					try {
						var34.put("sdkLevel", "BASIC");
					} catch (JSONException var20) {
						var10000 = var20;
						var10001 = false;
						break label211;
					}
				}
			}

			try {
				var39 = var1;
				var35 = var3;
				var10002 = var34;
				var10003 = var34;
				var34.put("os", "ANDROID");
			} catch (JSONException var19) {
				var10000 = var19;
				var10001 = false;
				break label211;
			}

			String var40 = "product";

			try {
				var10003.put(var40, var0.getProduct());
			} catch (JSONException var18) {
				var10000 = var18;
				var10001 = false;
				break label211;
			}

			var38 = "soc";

			try {
				var10002.put(var38, var0.getSoc());
			} catch (JSONException var17) {
				var10000 = var17;
				var10001 = false;
				break label211;
			}

			try {
				var35.put("sdkInfo", var34);
			} catch (JSONException var16) {
				var10000 = var16;
				var10001 = false;
				break label211;
			}

			JSONObject var41;
			if (var39) {
				try {
					var36 = var0;
					var35 = new JSONObject();
				} catch (Exception var15) {
					var10000 = var15;
					var10001 = false;
					break label211;
				}

				var10002 = var35;
				JSONObject var33;
				var10003 = var33 = var35;

				try {
					var10003 = new JSONObject();
				} catch (Exception var14) {
					var10000 = var14;
					var10001 = false;
					break label211;
				}

				var38 = "mId";

				try {
					var10002.put(var38, var0.getMid());
				} catch (JSONException var13) {
					var10000 = var13;
					var10001 = false;
					break label211;
				}

				var37 = "rId";

				try {
					var35.put(var37, var0.getRid());
				} catch (JSONException var12) {
					var10000 = var12;
					var10001 = false;
					break label211;
				}

				var2 = "authType";

				try {
					var39 = var36.isAcceleration();
				} catch (Exception var11) {
					var10000 = var11;
					var10001 = false;
					break label211;
				}

				String var5;
				if (var39) {
					var5 = "ACCELERATION";
				} else {
					var5 = "COMMON";
				}

				try {
					var41 = var4;
					var35 = var3;
					var33.put(var2, var5);
				} catch (JSONException var10) {
					var10000 = var10;
					var10001 = false;
					break label211;
				}

				try {
					var35.put("mInfo", var33);
				} catch (JSONException var9) {
					var10000 = var9;
					var10001 = false;
					break label211;
				}

				String var42 = "deviceIdDetail";

				try {
					var41.put(var42, a(var0));
				} catch (JSONException var8) {
					var10000 = var8;
					var10001 = false;
					break label211;
				}
			}

			try {
				var41 = var3;
				var35 = var3;
				var3.put("terminalInfo", var4);
			} catch (JSONException var7) {
				var10000 = var7;
				var10001 = false;
				break label211;
			}

			var37 = "logId";

			try {
				var35.put(var37, UUID.randomUUID().toString());
				return var41;
			} catch (JSONException var6) {
				var10000 = var6;
				var10001 = false;
			}
		}

		var10000.printStackTrace();
		return null;
	}

	private static JSONObject a(IBaseConfig var0) throws JSONException {
		JSONObject var1;
		var1 = new JSONObject();
		Map var10000 = DeviceInfo.getMacArray();
		JSONObject var2;
		var2 = new JSONObject();
		Iterator var3 = var10000.entrySet().iterator();

		while(var3.hasNext()) {
			Map.Entry var4;
			var2.put((String)(var4 = (Map.Entry)var3.next()).getKey(), var4.getValue());
		}

		JSONObject var6 = var1;
		JSONObject var10001 = var1;
		JSONObject var10003 = var1;
		JSONObject var10004 = var1;
		var1.put("sdk.version", "0.10.7");
		var1.put("macs", var2);
		var1.put("build_version_sdk_int", VERSION.SDK_INT);
		var1.put("java_serial", DeviceInfo.getSerial());
		var1.put("build.BRAND", Build.BRAND);
		var1.put("build.MODEL ", Build.MODEL);
		var1.put("build.DISPLAY", Build.DISPLAY);
		var1.put("build.HOST", Build.HOST);
		var1.put("build.MANUFACTURER", Build.MANUFACTURER);
		var1.put("build.FINGERPRINT", Build.FINGERPRINT);
		long var5 = Build.TIME;
		var10004.put("build.Time", var5);
		var10003.put("ping_modify_time", DeviceInfo.getPingModifiedTime());
		String var10002 = var0.getUserDeviceId().replaceAll("[^A-Za-z0-9\\-_]", "");
		var10001.put("userDeviceId", var10002.substring(0, Math.min(0, var10002.length())));
		return var6;
	}

	public static String getDeviceId(Context var0) {
		String var1;
		SharedPreferences var2;
		if (TextUtils.isEmpty(var1 = (var2 = var0.getSharedPreferences("edge_sdk_uuid", 0)).getString("uuid", ""))) {
			var1 = UUID.randomUUID().toString();
			SharedPreferences.Editor var10000 = var2.edit();
			var10000.putString("uuid", var1);
			var10000.commit();
		}

		return var1 == null ? "" : var1;
	}

	public static JSONObject mapToJsonObject(Map<String, ?> var0) throws JSONException {
		Map var10000 = var0;
		JSONObject var3;
		var3 = new JSONObject();
		Iterator var1 = var10000.entrySet().iterator();

		while(var1.hasNext()) {
			Map.Entry var2;
			var3.put((String)(var2 = (Map.Entry)var1.next()).getKey(), var2.getValue());
		}

		return var3;
	}

	public static Map<String, Integer> jsonObjectToIntMap(JSONObject var0) throws JSONException {
		HashMap var1;
		var1 = new HashMap();
		Iterator var2 = var0.keys();

		while(var2.hasNext()) {
			String var10001 = (String)var2.next();
			var1.put(var10001, var0.getInt(var10001));
		}

		return var1;
	}

	public static int getInferCores() {
		int var0;
		return (var0 = a()) > 1 ? var0 / 2 : 1;
	}

	private static int a() {
		int var10000;
		int var0;
		label23: {
			try {
				var10000 = (new File("/sys/devices/system/cpu/")).listFiles(a).length;
				break label23;
			} catch (SecurityException var1) {
			} catch (NullPointerException var2) {
			}

			var0 = 1;
			return var0;
		}

		var0 = var10000;
		return var0;
	}
}
