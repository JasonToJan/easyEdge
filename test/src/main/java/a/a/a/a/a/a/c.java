//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a.a.a.a;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.baidu.ai.edge.core.base.IBaseConfig;
import com.baidu.ai.edge.core.base.ISDKJni;
import com.baidu.ai.edge.core.util.HttpUtil;
import com.baidu.ai.edge.core.util.Util;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
	private static String c;
	private ISDKJni a;
	private String b;

	public c(Context var1, @NonNull ISDKJni var2) {
		this.a = var2;
		this.b = var1.getPackageName();
	}

	private JSONObject a(d var1) throws JSONException {
		JSONObject var6;
		var6 = new JSONObject();
		JSONArray var2;
		var2 = new JSONArray();
		Iterator var3 = var1.b().iterator();

		while(var3.hasNext()) {
			d.a var4 = (d.a)var3.next();
			JSONObject var5;
			JSONObject var10001 = var5 = new JSONObject();

			var5.put("modelId", var4.b());
			var5.put("releaseId", var4.c());
			var10001.put("data", Util.mapToJsonObject(var4.a()));
			var2.put(var10001);
		}

		var6.put("modelInvoke", var2);
		var6.put("sdkLaunch", Util.mapToJsonObject(var1.c()));
		return var6;
	}

	public void a(d var1, IBaseConfig var2, String var3) throws JSONException, IOException {
		c var10001 = this;
		JSONObject var4;
		JSONObject var10002 = var4 = Util.getBaseInfoJson(var2, false, var1.a());
		c var10006 = this;
		var4.put("bundleId", this.b);
		long var9 = (new Date()).getTime() / 1000L;
		var4.put("timestamp", var9);
		var10002.put("data", var10006.a(var1));
		var10002.put("version", 2);
		var10002.getJSONObject("terminalInfo").put("deviceId", var3);
		String var10 = var10002.toString();
		var10 = var10001.a.getStatJson(var10);
		HashMap var11;
		HashMap var16 = var11 = new HashMap();

		var16.put("ConnectTimeout", "4000");
		var16.put("ReadTimeout", "3000");
		var16.put("Content-Type", "text/plain");
		if (!TextUtils.isEmpty(var2.getAuthDomain())) {
			c = var2.getAuthDomain();
		}

		String var10000 = var10 = HttpUtil.post(c + "/offline-auth/v2/usage/edge", var10, var11);
		Log.i("StatRequest", "http result:" + var10);
		if (var10000 != null && !var10.isEmpty()) {
			Exception var14;
			label50: {
				JSONObject var15;
				boolean var18;
				try {
					var15 = new JSONObject();
				} catch (Exception var8) {
					var14 = var8;
					var18 = false;
					break label50;
				}

				JSONObject var12;
				JSONObject var19 = var12 = var15;

				boolean var17;
				try {
					var19 = new JSONObject(var10);
					var17 = var15.getBoolean("success");
				} catch (JSONException var7) {
					var14 = var7;
					var18 = false;
					break label50;
				}

				if (var17) {
					int var20;
					try {
						var20 = var12.getInt("status");
					} catch (JSONException var6) {
						var14 = var6;
						var18 = false;
						break label50;
					}

					if (var20 == 0) {
						return;
					}
				}

				try {
					throw new IOException("Request content is not successful");
				} catch (Exception var5) {
					var14 = var5;
					var18 = false;
				}
			}

			Exception var13 = var14;
			throw new IOException("Request content is not json", var13);
		} else {
			throw new IOException("Request content not correct");
		}
	}
}
