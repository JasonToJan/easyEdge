//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package a.a.a.a.a.a;

import android.content.Context;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class ddd {
	private String a;
	private Map<String, a> b;
	private Map<String, Integer> c;

	public ddd(Context var1, String var2, String var3) {
		this.a = var2;
		this.b = new HashMap();
	}

	public void a(int var1, int var2, String var3, Integer var4) {
		String var5 = var1 + "_" + var2;
		a var6;
		if ((var6 = (a)this.b.get(var5)) == null) {
			var6 = new a(var1, var2);
			this.b.put(var5, var6);
		}

		var6.a(var3, var4);
	}

	public Map<String, Integer> c() {
		return this.c;
	}

	public void a(Map<String, Integer> var1) {
		this.c = var1;
	}

	public String a() {
		return this.a;
	}

	public Collection<a> b() {
		return this.b.values();
	}

	public static class a {
		private int a;
		private int b;
		private Map<String, Integer> c;

		public a(int var1, int var2) {
			this.a = var1;
			this.b = var2;
			this.c = new HashMap();
		}

		public int b() {
			return this.a;
		}

		public int c() {
			return this.b;
		}

		public void a(String var1, Integer var2) {
			this.c.put(var1, var2);
		}

		public Map<String, Integer> a() {
			return this.c;
		}
	}
}
