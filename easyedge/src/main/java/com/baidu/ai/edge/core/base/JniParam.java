//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.base;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class JniParam {
	private boolean a = false;
	private Map<String, Object> b = new HashMap();

	public JniParam() {
	}

	private void a(String var1) {
		if (this.a) {
			Log.i("JniParam", "Try to read key:  " + var1);
		}

	}

	public void put(String var1, Object var2) {
		this.b.put(var1, var2);
	}

	public void put(String var1, long var2) {
		this.b.put(var1, var2);
	}

	public int getInt(String var1) {
		this.a(var1);
		Object var2;
		if ((var2 = this.b.get(var1)) == null) {
			Log.e("JniParams", "value is null : " + var1);
			return 0;
		} else {
			return Integer.valueOf(var2.toString());
		}
	}

	public boolean getBool(String var1) {
		this.a(var1);
		return !this.b.containsKey(var1) ? false : (Boolean)this.b.get(var1);
	}

	public long getLong(String var1) {
		this.a(var1);
		return (Long)this.b.get(var1);
	}

	public float getFloat(String var1) {
		this.a(var1);
		return (Float)this.b.get(var1);
	}

	public double getDouble(String var1) {
		this.a(var1);
		return (Double)this.b.get(var1);
	}

	public String getString(String var1) {
		this.a(var1);
		return (String)this.b.get(var1);
	}

	public boolean isNull(String var1) {
		this.a(var1);
		return this.b.get(var1) == null;
	}

	public Object getObject(String var1) {
		this.a(var1);
		return this.b.get(var1);
	}

	public float[] getFloatArr(String var1) {
		this.a(var1);
		return (float[])this.b.get(var1);
	}

	public int[] getIntArr(String var1) {
		this.a(var1);
		return (int[])this.b.get(var1);
	}

	public boolean containsKey(String var1) {
		this.a(var1);
		return this.b.containsKey(var1);
	}
}
