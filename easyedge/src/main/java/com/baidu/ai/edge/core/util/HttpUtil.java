//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpUtil {
	private HttpUtil() {
	}

	public static boolean isOnline(Context var0) {
		NetworkInfo var1;
		return (var1 = ((ConnectivityManager)var0.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()) != null && var1.isConnectedOrConnecting();
	}

	public static String get(String var0) throws IOException {
		Log.i("HttpUtil", "try to connect:" + var0 + " Thread id:" + Thread.currentThread().getId());
		HttpURLConnection var4;
		if ((var4 = (HttpURLConnection)(new URL(var0)).openConnection()) instanceof HttpsURLConnection) {
			label20: {
				Object var5;
				try {
					a((HttpsURLConnection)var4);
					break label20;
				} catch (NoSuchAlgorithmException var2) {
					var5 = var2;
				} catch (KeyManagementException var3) {
					var5 = var3;
				}

				Object[] var1;
				Object[] var10002 = var1 = new Object[2];
				var1[0] = var5.getClass().getSimpleName();
				var10002[1] = ((GeneralSecurityException)var5).getMessage();
				throw new IOException(String.format("SSL issue: %s | %s", var1));
			}
		}

		var4.setRequestProperty("Content-Type", "application/json");
		var4.setRequestMethod("GET");
		var4.setConnectTimeout(4000);
		var4.setReadTimeout(20000);
		var4.setDoOutput(false);
		return getResp(var4.getInputStream());
	}

	public static String post(String var0, String var1) throws IOException {
		return post(var0, (String)var1, new HashMap());
	}

	public static String postPlain(String var0, String var1) throws IOException {
		HashMap var10002 = new HashMap();
		var10002.put("Content-Type", "text/plain");
		return post(var0, (String)var1, var10002);
	}

	public static String post(String var0, String var1, Map<String, String> var2) throws IOException {
		String var10000 = var1;
		Log.i("HttpUtil", "try to connect:" + var0 + " Thread id:" + Thread.currentThread().getId());
		URL var8;
		var8 = new URL(var0);
		byte[] var6 = getBytes(var10000);
		HttpURLConnection var9;
		if ((var9 = (HttpURLConnection)var8.openConnection()) instanceof HttpsURLConnection) {
			label36: {
				Object var7;
				try {
					a((HttpsURLConnection)var9);
					break label36;
				} catch (NoSuchAlgorithmException var4) {
					var7 = var4;
				} catch (KeyManagementException var5) {
					var7 = var5;
				}

				Object[] var10;
				Object[] var10002 = var10 = new Object[2];
				var10[0] = var7.getClass().getSimpleName();
				var10002[1] = ((GeneralSecurityException)var7).getMessage();
				throw new IOException(String.format("SSL issue: %s | %s", var10));
			}
		}

		if (var2.containsKey("Content-Type")) {
			var9.setRequestProperty("Content-Type", (String)var2.get("Content-Type"));
		} else {
			var9.setRequestProperty("Content-Type", "application/json");
		}

		var9.setRequestMethod("POST");
		int var3;
		if (var2.containsKey("ConnectTimeout")) {
			var3 = Integer.valueOf((String)var2.get("ConnectTimeout"));
		} else {
			var3 = 4000;
		}

		int var11;
		if (var2.containsKey("ReadTimeout")) {
			var11 = Integer.valueOf((String)var2.get("ReadTimeout"));
		} else {
			var11 = 20000;
		}

		var9.setConnectTimeout(var3);
		var9.setReadTimeout(var11);
		var9.setDoOutput(true);
		var9.getOutputStream().write(var6);
		return getResp(var9.getInputStream());
	}

	public static String post(String var0, Bitmap var1, Map<String, Float> var2) throws IOException, JSONException {
		Log.i("HttpUtil", "try to connect:" + var0 + " Thread id:" + Thread.currentThread().getId());
		URL var3;
		var3 = new URL(var0);
		JSONObject var4;
		JSONObject var10001 = var4 = new JSONObject();

		var10001.put("image", bitmapToBase64(var1));
		if (var2 != null) {
			Iterator var6 = var2.entrySet().iterator();

			while(var6.hasNext()) {
				Map.Entry var7;
				var4.put((String)(var7 = (Map.Entry)var6.next()).getKey(), var7.getValue());
			}
		}

		byte[] var5 = getBytes(var4.toString());
		HttpURLConnection var10000 = (HttpURLConnection)var3.openConnection();
		var10000.setRequestProperty("Content-Type", "application/json");
		var10000.setRequestMethod("POST");
		var10000.setConnectTimeout(4000);
		var10000.setReadTimeout(20000);
		var10000.setDoOutput(true);
		var10000.getOutputStream().write(var5);
		return getResp(var10000.getInputStream());
	}

	public static String bitmapToBase64(Bitmap param0) {
		// $FF: Couldn't be decompiled
		return "";
	}

	public static String getResp(InputStream var0) throws IOException {
		BufferedReader var1;
		var1 = new BufferedReader(new InputStreamReader(var0));
		StringBuffer var4;
		var4 = new StringBuffer();
		char[] var2 = new char[512];

		int var3;
		while((var3 = var1.read(var2)) != -1) {
			var4.append(new String(var2, 0, var3));
		}

		var1.close();
		return var4.toString();
	}

	public static byte[] getBytes(String var0) throws UnsupportedEncodingException {
		return var0.getBytes("UTF-8");
	}

	private static void a(HttpsURLConnection var0) throws NoSuchAlgorithmException, KeyManagementException {
		HttpsURLConnection var10000 = var0;
		HttpsURLConnection var10001 = var0;
		SSLContext var10002 = SSLContext.getInstance("TLSv1.2");
		TrustManager[] var2;
		TrustManager[] var10004 = var2 = new TrustManager[1];
		c var1;
		var1 = new c();
		var10004[0] = var1;
		SecureRandom var3;
		var3 = new SecureRandom();
		var10002.init((KeyManager[])null, var2, var3);
		var10001.setSSLSocketFactory(var10002.getSocketFactory());
		var10000.setHostnameVerifier(new b());
	}

	private static class b implements HostnameVerifier {
		private b() {
		}

		public boolean verify(String var1, SSLSession var2) {
			return true;
		}
	}

	private static class c implements X509TrustManager {
		private c() {
		}

		public void checkClientTrusted(X509Certificate[] var1, String var2) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] var1, String var2) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}
}
