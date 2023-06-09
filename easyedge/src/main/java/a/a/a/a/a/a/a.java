package a.a.a.a.a.a;

import android.content.Context;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.Consts;
import com.baidu.ai.edge.core.base.ISDKJni;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;

public class a {
    private static AtomicBoolean k = new AtomicBoolean(false);
    private static AtomicBoolean l = new AtomicBoolean(false);
    private static volatile long m = 0;
    private static volatile long n = 0;
    private SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
    private volatile b b;
    private volatile b c;
    private String d;
    private int e;
    private int f;
    private Context g;
    private String h;
    private ISDKJni i;
    private BaseConfig j;

    private class InnerB implements Runnable {
        private InnerB() {
        }

        private d a() {
            d dVar = new d(a.this.g, a.this.d, a.this.h);
            dVar.a(a.this.b.b());
            for (Entry entry : a.this.c.b().entrySet()) {
                String str = (String) entry.getKey();
                String[] split = str.split("_");
                StringBuilder stringBuilder;
                if (split.length >= 2) {
                    int parseInt = Integer.parseInt(split[0]);
                    int parseInt2 = Integer.parseInt(split[1]);
                    String str2 = "modelId is negative:";
                    if (parseInt < 0) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str2);
                        stringBuilder.append(parseInt);
                        throw new RuntimeException(stringBuilder.toString());
                    } else if (parseInt2 >= 0) {
                        dVar.a(parseInt, parseInt2, split[2], (Integer) entry.getValue());
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str2);
                        stringBuilder.append(parseInt);
                        throw new RuntimeException(stringBuilder.toString());
                    }
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("internal stat key error:");
                stringBuilder.append(str);
                throw new RuntimeException(stringBuilder.toString());
            }
            return dVar;
        }

        public void run() {
            Throwable e = null;
            try {
                new c(a.this.g, a.this.i).a(a(), a.this.j, a.this.d);
                a.this.c.a();
                a.this.b.a();
                m = System.currentTimeMillis();
                e = null;
            } catch (JSONException e2) {
                e = e2;
                e.printStackTrace();
            } catch (IOException e3) {
                e = e3;
            } catch (Throwable th) {
                k.set(false);
            }
            k.set(false);
            if (e != null) {
                a.this.b();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stat connection error :");
                stringBuilder.append(e.getClass().getSimpleName());
                stringBuilder.append(":");
                stringBuilder.append(e.getMessage());
                Log.e("StatManager", stringBuilder.toString(), e);
            }
        }
    }

    public a(Context context, ISDKJni iSDKJni, BaseConfig baseConfig, String str) {
        if (str == null || str.isEmpty()) {
            throw new RuntimeException("deviceId is empty");
        }
        this.j = baseConfig;
        this.g = context;
        this.i = iSDKJni;
        this.d = str;
        this.e = baseConfig.getMid();
        this.f = baseConfig.getRid();
        this.h = baseConfig.getProduct();
        try {
            this.b = b.a(context, "start");
            this.c = b.a(context, Consts.ASSETS_DIR_ARM);
        } catch (Throwable e) {
            throw new RuntimeException(e);
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
            new Thread(new InnerB()).start();
        }
    }

    public void a() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.e);
        String str = "_";
        stringBuilder.append(str);
        stringBuilder.append(this.f);
        stringBuilder.append(str);
        stringBuilder.append(e());
        this.c.a(stringBuilder.toString());
        long currentTimeMillis = System.currentTimeMillis();
        if (n == 0 || currentTimeMillis < n || currentTimeMillis > n + 86400000) {
            f();
        } else if (currentTimeMillis > m + 30000) {
            try {
                this.c.c();
                m = System.currentTimeMillis();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void b() {
        try {
            this.c.c();
            this.b.c();
            m = System.currentTimeMillis();
        } catch (Throwable e) {
            e.printStackTrace();
            Log.e("StatManager", "save error", e);
        }
    }

    public void c() {
        boolean andSet = l.getAndSet(true);
        this.b.a(e());
        if (andSet) {
            try {
                this.b.c();
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        f();
    }
}
