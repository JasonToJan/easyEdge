package com.baidu.ai.edge.core.base;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class JniParam {
    private boolean a = false;
    private Map<String, Object> b = new HashMap();

    private void a(String str) {
        if (this.a) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Try to read key:  ");
            stringBuilder.append(str);
            Log.i("JniParam", stringBuilder.toString());
        }
    }

    public boolean containsKey(String str) {
        a(str);
        return this.b.containsKey(str);
    }

    public boolean getBool(String str) {
        a(str);
        return !this.b.containsKey(str) ? false : ((Boolean) this.b.get(str)).booleanValue();
    }

    public double getDouble(String str) {
        a(str);
        return ((Double) this.b.get(str)).doubleValue();
    }

    public float getFloat(String str) {
        a(str);
        return ((Float) this.b.get(str)).floatValue();
    }

    public float[] getFloatArr(String str) {
        a(str);
        return (float[]) this.b.get(str);
    }

    public int getInt(String str) {
        a(str);
        Object obj = this.b.get(str);
        if (obj != null) {
            return Integer.valueOf(obj.toString()).intValue();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("value is null : ");
        stringBuilder.append(str);
        Log.e("JniParams", stringBuilder.toString());
        return 0;
    }

    public int[] getIntArr(String str) {
        a(str);
        return (int[]) this.b.get(str);
    }

    public long getLong(String str) {
        a(str);
        return ((Long) this.b.get(str)).longValue();
    }

    public Object getObject(String str) {
        a(str);
        return this.b.get(str);
    }

    public String getString(String str) {
        a(str);
        return (String) this.b.get(str);
    }

    public boolean isNull(String str) {
        a(str);
        return this.b.get(str) == null;
    }

    public void put(String str, long j) {
        this.b.put(str, Long.valueOf(j));
    }

    public void put(String str, Object obj) {
        this.b.put(str, obj);
    }
}
