package a.a.a.a.a.a;

import android.content.Context;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class d {
    private String a;
    private Map<String, a> b = new HashMap();
    private Map<String, Integer> c;

    public static class a {
        private int a;
        private int b;
        private Map<String, Integer> c = new HashMap();

        public a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public Map<String, Integer> a() {
            return this.c;
        }

        public void a(String str, Integer num) {
            this.c.put(str, num);
        }

        public int b() {
            return this.a;
        }

        public int c() {
            return this.b;
        }
    }

    public d(Context context, String str, String str2) {
        this.a = str;
    }

    public String a() {
        return this.a;
    }

    public void a(int i, int i2, String str, Integer num) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(i);
        stringBuilder.append("_");
        stringBuilder.append(i2);
        String stringBuilder2 = stringBuilder.toString();
        a aVar = (a) this.b.get(stringBuilder2);
        if (aVar == null) {
            aVar = new a(i, i2);
            this.b.put(stringBuilder2, aVar);
        }
        aVar.a(str, num);
    }

    public void a(Map<String, Integer> map) {
        this.c = map;
    }

    public Collection<a> b() {
        return this.b.values();
    }

    public Map<String, Integer> c() {
        return this.c;
    }
}
