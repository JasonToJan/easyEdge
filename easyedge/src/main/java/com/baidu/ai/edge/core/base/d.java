package com.baidu.ai.edge.core.base;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d {
    protected int a;
    protected int b;
    protected float[] c;
    protected float[] d;
    protected String e;
    protected String f;
    protected String g = "keep_size";
    protected boolean h;
    protected String i;
    protected int j;
    protected int k;
    private int l;
    private final boolean m;
    private boolean n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private boolean t;
    private String u;
    private int v;
    private int w;
    private int[] x;

    public d(String str, int i, int i2) throws CallException {
        String str2 = "padding_mode";
        String str3 = "target_size";
        String str4 = "resize";
        String str5 = "padding_fill_size";
        String str6 = "";
        this.i = str6;
        this.l = -1;
        this.o = 320;
        this.p = 32;
        this.q = 1;
        String str7 = "padding_align32";
        this.u = str7;
        this.v = 0;
        this.w = 0;
        this.x = new int[]{114, 114, 114};
        try {
            JSONArray optJSONArray;
            JSONObject jSONObject = new JSONObject(str);
            String str8 = "max_size";
            if (i == 100) {
                this.k = jSONObject.getInt(str8);
                optJSONArray = jSONObject.optJSONArray("ocr_rec_resize");
                if (optJSONArray != null) {
                    this.o = optJSONArray.getInt(0);
                    this.p = optJSONArray.getInt(1);
                }
                this.q = jSONObject.optInt("ocr_rec_batch_num", this.q);
            } else {
                if (jSONObject.has(str4)) {
                    optJSONArray = jSONObject.getJSONArray(str4);
                    this.a = optJSONArray.optInt(0);
                    this.b = optJSONArray.optInt(1);
                }
                this.g = jSONObject.optString("rescale_mode", this.g);
                str = "keep_ratio";
                if (i2 == 102) {
                    this.g = str;
                }
                if (this.g.equals(str)) {
                    this.j = jSONObject.has(str3) ? jSONObject.getInt(str3) : this.a;
                    this.k = jSONObject.has(str8) ? jSONObject.getInt(str8) : this.b;
                } else if (this.g.equals("warp_affine") && jSONObject.has("warp_affine_keep_res")) {
                    this.h = jSONObject.getBoolean("warp_affine_keep_res");
                }
            }
            if (i2 == 102 || i2 == Consts.NTYPE_MRCNN_R50_VD_FPN) {
                this.t = true;
            }
            Log.e("TEST##", "go to d ...111");
            if (jSONObject.has(str2)) {
                this.u = jSONObject.getString(str2);
                if (this.u.equals(str7)) {
                    this.t = true;
                } else if (this.u.equals(str5)) {
                    this.t = true;
                    if (jSONObject.has(str5)) {
                        optJSONArray = jSONObject.getJSONArray(str5);
                        this.v = optJSONArray.optInt(0);
                        this.w = optJSONArray.optInt(1);
                    }
                    Log.e("TEST##", "go to d ...112");
                    if (jSONObject.has("padding_fill_value")) {
                        optJSONArray = jSONObject.getJSONArray("padding_fill_value");
                        if (optJSONArray.length() == 1) {
                            int optInt = optJSONArray.optInt(0);
                            this.x = new int[]{optInt, optInt, optInt};
                        } else if (optJSONArray.length() == 3) {
                            for (i2 = 0; i2 < 3; i2++) {
                                this.x[i2] = optJSONArray.optInt(i2);
                            }
                        } else {
                            Log.e("PreprocessConfig", "Padding fill value dims must be 1 or 3.");
                        }
                    }
                }
            }
            optJSONArray = jSONObject.optJSONArray("img_mean");
            if (optJSONArray == null) {
                optJSONArray = jSONObject.getJSONArray("mean");
            }
            Log.e("TEST##", "go to d ...114");
            this.c = BaseConfig.getArrayFloatValues(optJSONArray);
            this.d = BaseConfig.getArrayFloatValues(jSONObject.getJSONArray("scale"));

            Log.e("TEST##", "go to d ...115");
            this.n = jSONObject.optBoolean("skip_norm", false);
            if (this.n) {
                this.d = new float[]{1.0f, 1.0f, 1.0f};
                this.c = new float[]{0.0f, 0.0f, 0.0f};
                Log.i("PreprocessConfig", "skip_norm");
            }
            this.e = jSONObject.optString("colorFormat");
            if (TextUtils.isEmpty(this.e)) {
                this.e = jSONObject.getString("color_format");
            }
            Log.e("TEST##", "go to d ...116");
            this.f = jSONObject.optString("channelOrder");
            if (TextUtils.isEmpty(this.f)) {
                this.f = jSONObject.optString("channel_order", str6);
            }
            if (jSONObject.has("extra")) {
                this.i = jSONObject.getJSONObject("extra").optString("detection", str6);
            }
            Log.e("TEST##", "go to d ...117");
            this.m = jSONObject.optBoolean("letterbox");
            if (jSONObject.has("center_crop_size")) {
                optJSONArray = jSONObject.getJSONArray("center_crop_size");
                this.r = optJSONArray.getInt(0);
                this.s = optJSONArray.getInt(1);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            throw new CallException(1002, " preprocess_args parse json error ", e);
        }
    }

    public int a() {
        return this.s;
    }

    public void a(int i) {
        this.l = i;
    }

    public void a(String str) {
        this.f = str;
    }

    public int b() {
        return this.r;
    }

    public String c() {
        return this.f;
    }

    public String d() {
        return this.e;
    }

    public String e() {
        return this.i;
    }

    public float[] f() {
        return this.c;
    }

    public int g() {
        return this.k;
    }

    public int h() {
        return this.q;
    }

    public int i() {
        return this.p;
    }

    public int j() {
        return this.o;
    }

    public int k() {
        return this.w;
    }

    public int l() {
        return this.v;
    }

    public String m() {
        return this.u;
    }

    public int[] n() {
        return this.x;
    }

    public int o() {
        return this.l;
    }

    public int p() {
        return this.b;
    }

    public int q() {
        return this.a;
    }

    public String r() {
        return this.g;
    }

    public float[] s() {
        return this.d;
    }

    public int t() {
        return this.j;
    }

    public boolean u() {
        return this.m;
    }

    public boolean v() {
        return this.t;
    }

    public boolean w() {
        return this.n;
    }

    public boolean x() {
        return this.h;
    }
}
