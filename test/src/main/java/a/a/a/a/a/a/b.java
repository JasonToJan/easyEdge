//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a.a.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.baidu.ai.edge.core.util.Util;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

public class b {
	private static ConcurrentHashMap<String, b> d = new ConcurrentHashMap();
	private ConcurrentHashMap<String, AtomicInteger> a;
	private SharedPreferences b;
	private String c;

	private b(Context var1, String var2) throws JSONException {
		this.c = var2;
		this.b = var1.getSharedPreferences("easyedge-app", 0);
		String var3 = this.b.getString("statMap-" + var2, "");
		this.a = new ConcurrentHashMap();
		if (!var3.isEmpty()) {
			Iterator var4 = Util.jsonObjectToIntMap(new JSONObject(var3)).entrySet().iterator();

			while(var4.hasNext()) {
				Map.Entry var5 = (Map.Entry)var4.next();
				this.a.put(var5.getKey().toString(), new AtomicInteger((Integer)var5.getValue()));
			}
		}

	}

	public static b a(Context var0, String var1) throws JSONException {
		b var2;
		var2 = new b(var0, var1);
		b var3;
		if ((var3 = (b)d.putIfAbsent(var1, var2)) == null) {
			var3 = var2;
		}

		return var3;
	}

	public void a(String var1) {
		AtomicInteger var2;
		if ((var2 = (AtomicInteger)this.a.get(var1)) == null) {
			this.a.put(var1, new AtomicInteger(1));
		} else {
			var2.incrementAndGet();
		}

	}

	public void b(String var1) throws Throwable{
		Class var2;
		Class var10000 = var2 = b.class;
		b var10001 = this;
		synchronized(var2){}

		Throwable var34;
		boolean var35;
		String var10002;
		try {
			var10002 = "statMap-" + this.c;
		} catch (Throwable var32) {
			var34 = var32;
			var35 = false;
			throw var34;
		}

		String var33 = var10002;

		SharedPreferences.Editor var36;
		try {
			var36 = var10001.b.edit();
			var36.putString(var33, var1);
		} catch (Throwable var31) {
			var34 = var31;
			var35 = false;
			throw var34;
		}

		try {
			var36.commit();
		} catch (Throwable var30) {
			var34 = var30;
			var35 = false;
			throw var34;
		}

		String var37 = "StatMap";

		try {
			Log.i(var37, "save " + var33 + ":" + var1);
		} catch (Throwable var29) {
			var34 = var29;
			var35 = false;
			throw var34;
		}

		try {
			;
		} catch (Throwable var28) {
			var34 = var28;
			var35 = false;
			throw var34;
		}
	}

	public void c() throws Throwable {
		this.b(Util.mapToJsonObject(this.b()).toString());
	}

	public void a() throws Throwable {
		this.b("");
		this.a.clear();
	}

	public Map<String, Integer> b() {
		b var10000 = this;
		HashMap var3;
		var3 = new HashMap();
		Iterator var1 = var10000.a.entrySet().iterator();

		while(var1.hasNext()) {
			Map.Entry var2;
			var3.put((var2 = (Map.Entry)var1.next()).getKey(), ((AtomicInteger)var2.getValue()).get());
		}

		return var3;
	}
}
