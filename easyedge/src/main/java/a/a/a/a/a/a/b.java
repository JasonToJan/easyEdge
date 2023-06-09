package a.a.a.a.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.baidu.ai.edge.core.util.Util;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

public class b {
    private static ConcurrentHashMap<String, b> d = new ConcurrentHashMap();
    private ConcurrentHashMap<String, AtomicInteger> a = new ConcurrentHashMap();
    private SharedPreferences b;
    private String c;

    private b(Context context, String str) throws JSONException {
        this.c = str;
        this.b = context.getSharedPreferences("easyedge-app", 0);
        SharedPreferences sharedPreferences = this.b;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("statMap-");
        stringBuilder.append(str);
        String string = sharedPreferences.getString(stringBuilder.toString(), "");
        if (!string.isEmpty()) {
            for (Entry entry : Util.jsonObjectToIntMap(new JSONObject(string)).entrySet()) {
                this.a.put(entry.getKey().toString(), new AtomicInteger(((Integer) entry.getValue()).intValue()));
            }
        }
    }

    public static b a(Context context, String str) throws JSONException {
        b bVar = new b(context, str);
        b bVar2 = (b) d.putIfAbsent(str, bVar);
        return bVar2 == null ? bVar : bVar2;
    }

    public void a() {
        b("");
        this.a.clear();
    }

    public void a(String str) {
        AtomicInteger atomicInteger = (AtomicInteger) this.a.get(str);
        if (atomicInteger == null) {
            this.a.put(str, new AtomicInteger(1));
        } else {
            atomicInteger.incrementAndGet();
        }
    }

    public Map<String, Integer> b() {
        Map<String, Integer> hashMap = new HashMap();
        for (Entry entry : this.a.entrySet()) {
            hashMap.put(entry.getKey().toString(), Integer.valueOf(((AtomicInteger) entry.getValue()).get()));
        }
        return hashMap;
    }

    public void b(String str) {
        synchronized (b.class) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("statMap-");
            stringBuilder.append(this.c);
            String stringBuilder2 = stringBuilder.toString();
            Editor edit = this.b.edit();
            edit.putString(stringBuilder2, str);
            edit.commit();
            String str2 = "StatMap";
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("save ");
            stringBuilder3.append(stringBuilder2);
            stringBuilder3.append(":");
            stringBuilder3.append(str);
            Log.i(str2, stringBuilder3.toString());
        }
    }

    public void c() throws JSONException {
        b(Util.mapToJsonObject(b()).toString());
    }
}
