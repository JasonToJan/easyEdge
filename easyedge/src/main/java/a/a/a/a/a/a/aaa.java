//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a.a.a.a;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.ISDKJni;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;

public class aaa {
	private static AtomicBoolean k = new AtomicBoolean(false);
	private static AtomicBoolean l = new AtomicBoolean(false);
	private static volatile long m = 0L;
	private static volatile long n = 0L;
	private SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
	private volatile bbb b;
	private volatile bbb c;
	private String d;
	private int e;
	private int f;
	private Context g;
	private String h;
	private ISDKJni i;
	private BaseConfig j;

	public aaa(Context var1, @NonNull ISDKJni var2, BaseConfig var3, String var4) {
		if (var4 != null && !var4.isEmpty()) {
			aaa var10000 = this;
			Context var10001 = var1;
			aaa var10002 = this;
			Context var10003 = var1;
			this.j = var3;
			this.g = var1;
			this.i = var2;
			this.d = var4;
			this.e = var3.getMid();
			this.f = var3.getRid();
			this.h = var3.getProduct();

			try {
				var10002.b = bbb.a(var10003, "start");
				var10000.c = bbb.a(var10001, "infer");
			} catch (JSONException var5) {
				throw new RuntimeException(var5);
			}
		} else {
			throw new RuntimeException("deviceId is empty");
		}
	}

	private String e() {
		return this.a.format(new Date());
	}

	private void f() {
		n = System.currentTimeMillis();
		if (k.getAndSet(true)) {
			Log.e("StatManager", "StatRequest is running");
		} else {
			(new Thread(new b())).start();
		}
	}

	public void c() {
		boolean var10000 = l.getAndSet(true);
		this.b.a(this.e());
		if (!var10000) {
			this.f();
		} else {
			try {
				this.b.c();
			} catch (Throwable var1) {
				var1.printStackTrace();
			}
		}

	}

	public void a() {
		String var1 = this.e + "_" + this.f + "_" + this.e();
		this.c.a(var1);
		long var4 = System.currentTimeMillis();
		if (n != 0L && var4 >= n && var4 <= n + 86400000L) {
			if (var4 > m + 30000L) {
				try {
					this.c.c();
					m = System.currentTimeMillis();
				} catch (Throwable var3) {
					var3.printStackTrace();
				}
			}
		} else {
			this.f();
		}

	}

	public void b() {
		try {
			this.c.c();
			this.b.c();
			m = System.currentTimeMillis();
		} catch (Throwable var1) {
			var1.printStackTrace();
			Log.e("StatManager", "save error", var1);
		}

	}

	private class b implements Runnable {
		private b() {
		}

		private ddd a() {
			ddd var1;
			ddd var10001 = var1 = new ddd(aaa.this.g, aaa.this.d, aaa.this.h);
			var10001.a(aaa.this.b.b());
			Iterator var6 = aaa.this.c.b().entrySet().iterator();

			while(var6.hasNext()) {
				Map.Entry var2;
				String var3;
				String[] var4;
				if ((var4 = (var3 = (String)(var2 = (Map.Entry)var6.next()).getKey()).split("_")).length < 2) {
					throw new RuntimeException("internal stat key error:" + var3);
				}

				int var7;
				int var10000 = var7 = Integer.parseInt(var4[0]);
				int var5 = Integer.parseInt(var4[1]);
				if (var10000 < 0) {
					throw new RuntimeException("modelId is negative:" + var7);
				}

				if (var5 < 0) {
					throw new RuntimeException("modelId is negative:" + var7);
				}

				var1.a(var7, var5, var4[2], (Integer)var2.getValue());
			}

			return var1;
		}

		public void run() {
			b var10000 = this;
			b var10001 = this;
			b var10003 = this;
			ccc var10002 = new ccc(aaa.this.g, aaa.this.i);
			Object var1 = null;

			try {
				try {
					var10002.a(var10003.a(), aaa.this.j, aaa.this.d);
					aaa.this.c.a();
					aaa.this.b.a();
					m = System.currentTimeMillis();
				} catch (JSONException var5) {
					var1 = var5;
					var5.printStackTrace();
				} catch (IOException var6) {
					var1 = var6;
				}
			} catch (Throwable var7) {
				k.set(false);
			}

			k.set(false);
			if (var1 != null) {
				aaa.this.b();
				Log.e("StatManager", "Stat connection error :" + var1.getClass().getSimpleName() + ":" + ((Exception)var1).getMessage(), (Throwable)var1);
			}

		}
	}
}
