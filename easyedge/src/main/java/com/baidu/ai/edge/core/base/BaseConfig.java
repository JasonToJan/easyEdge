//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.base;

import android.content.res.AssetManager;
import android.text.TextUtils;
import com.baidu.ai.edge.core.util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseConfig implements IBaseConfig {
	public static final float DEFAULT_THRESHOLD = 0.3F;
	protected String a;
	protected String[] b;
	protected int c;
	protected int d;
	protected int e;
	protected int f;
	protected float g;
	protected String h;
	protected int i;
	protected String j;
	protected boolean k;
	protected boolean l;
	protected boolean m = true;
	protected int n = -1;
	private String o = "";
	protected String p;
	private String q = null;
	private String r = "device";
	private long s;
	private String t;
	private String u;
	protected d v;

	public BaseConfig() {
	}

	public BaseConfig(AssetManager var1, String var2) throws Throwable {
		boolean var3 = true;
		if (var2.startsWith("file:///")) {
			var2 = var2.substring(7);
			var3 = false;
		}

		String var4 = var2 + "/conf.json";
		String var5 = var2 + "/preprocess_args.json";
		String var6 = var2 + "/infer_cfg.json";
		String var7 = var2 + "/label_list.txt";
		if (var3) {
			var4 = FileUtil.readAssetsFileUTF8StringIfExists(var1, var4);
			var6 = FileUtil.readAssetsFileUTF8StringIfExists(var1, var6);
		} else {
			var4 = FileUtil.readFileIfExists(var4);
			var6 = FileUtil.readFileIfExists(var6);
		}

		if (TextUtils.isEmpty(var4) && TextUtils.isEmpty(var6)) {
			throw new CallException(1001, "No config file");
		} else {
			Object var18;
			Exception var21;
			label81: {
				Exception var10000;
				label73: {
					boolean var10001;
					String var19;
					String var23;
					String var24;
					if (var3) {
						String var10002;
						AssetManager var22;
						try {
							var22 = var1;
							var23 = var7;
							var10002 = FileUtil.readAssetsFileUTF8StringIfExists(var1, var5);
						} catch (Exception var17) {
							var10000 = var17;
							var10001 = false;
							break label73;
						}

						var19 = var10002;

						try {
							var24 = FileUtil.readAssetFileUtf8String(var22, var23);
						} catch (IOException var14) {
							var21 = var14;
							var10001 = false;
							break label81;
						} catch (Exception var15) {
							var10000 = var15;
							var10001 = false;
							break label73;
						}
					} else {
						try {
							var24 = var7;
							var23 = FileUtil.readFileIfExists(var5);
						} catch (IOException var12) {
							var21 = var12;
							var10001 = false;
							break label81;
						} catch (Exception var13) {
							var10000 = var13;
							var10001 = false;
							break label73;
						}

						var19 = var23;

						try {
							var24 = FileUtil.readFile(var24);
						} catch (IOException var10) {
							var21 = var10;
							var10001 = false;
							break label81;
						} catch (Exception var11) {
							var10000 = var11;
							var10001 = false;
							break label73;
						}
					}

					String var20 = var24;

					try {
						this.a(var4, var19, var20, var6);
						return;
					} catch (CallException var9) {
						var10000 = var9;
						var10001 = false;
					}
				}

				var18 = var10000;
				throw new CallException(1001, "config read asset file error " + var2, (Throwable)var18);
			}

			var18 = var21;
			throw new CallException(1001, "config read asset file error " + var2, (Throwable)var18);
		}
	}

	public static String[] getArrayStringValues(JSONArray var0) throws JSONException {
		int var1;
		String[] var2 = new String[var1 = var0.length()];

		for(int var3 = 0; var3 < var1; ++var3) {
			var2[var3] = var0.getString(var3);
		}

		return var2;
	}

	public static float[] getArrayFloatValues(JSONArray var0) throws JSONException {
		int var1;
		float[] var2 = new float[var1 = var0.length()];

		for(int var3 = 0; var3 < var1; ++var3) {
			var2[var3] = (float)var0.getDouble(var3);
		}

		return var2;
	}

	private JSONObject b(String var1) throws CallException {
		Exception var10000;
		label130: {
			boolean var10001;
			JSONObject var23;
			try {
				var23 = new JSONObject();
			} catch (Exception var20) {
				var10000 = var20;
				var10001 = false;
				break label130;
			}

			JSONObject var2 = var23;

			JSONObject var10002;
			BaseConfig var24;
			try {
				var24 = this;
				var2 = new JSONObject(var1);
				var10002 = var2.getJSONObject("model_info");
			} catch (JSONException var19) {
				var10000 = var19;
				var10001 = false;
				break label130;
			}

			JSONObject var22 = var10002;

			BaseConfig var10003;
			double var10004;
			try {
				var10003 = this;
				this.f = var22.optInt("n_type", 0);
				var10004 = var22.optDouble("best_threshold", 0.30000001192092896);
			} catch (Exception var18) {
				var10000 = var18;
				var10001 = false;
				break label130;
			}

			float var26 = (float)var10004;

			boolean var25;
			try {
				var10003.g = var26;
				var24.i = var10002.getInt("model_kind");
				var25 = var23.has("block");
			} catch (JSONException var17) {
				var10000 = var17;
				var10001 = false;
				break label130;
			}

			if (var25) {
				try {
					var23 = var2.getJSONObject("block");
				} catch (JSONException var16) {
					var10000 = var16;
					var10001 = false;
					break label130;
				}

				var22 = var23;

				try {
					var25 = var23.has("yolo");
				} catch (Exception var15) {
					var10000 = var15;
					var10001 = false;
					break label130;
				}

				if (var25) {
					try {
						this.p = var22.getJSONObject("yolo").optString("detection");
					} catch (JSONException var14) {
						var10000 = var14;
						var10001 = false;
						break label130;
					}
				}
			}

			try {
				var25 = var2.has("extra");
			} catch (Exception var13) {
				var10000 = var13;
				var10001 = false;
				break label130;
			}

			if (var25) {
				try {
					var23 = var2.getJSONObject("extra");
				} catch (JSONException var12) {
					var10000 = var12;
					var10001 = false;
					break label130;
				}

				var22 = var23;

				try {
					var25 = var23.has("fluid");
				} catch (Exception var11) {
					var10000 = var11;
					var10001 = false;
					break label130;
				}

				if (var25) {
					try {
						var23 = var22.getJSONObject("fluid");
					} catch (JSONException var10) {
						var10000 = var10;
						var10001 = false;
						break label130;
					}

					var22 = var23;

					try {
						var25 = var23.has("optType");
					} catch (Exception var9) {
						var10000 = var9;
						var10001 = false;
						break label130;
					}

					if (var25) {
						try {
							var25 = "nb".equals(var22.getString("optType"));
						} catch (JSONException var8) {
							var10000 = var8;
							var10001 = false;
							break label130;
						}

						if (var25) {
							try {
								this.l = true;
							} catch (Exception var7) {
								var10000 = var7;
								var10001 = false;
								break label130;
							}
						}
					}

					try {
						var25 = var22.has("quantization");
					} catch (Exception var6) {
						var10000 = var6;
						var10001 = false;
						break label130;
					}

					if (var25) {
						try {
							var25 = "int8".equals(var22.getString("quantization"));
						} catch (JSONException var5) {
							var10000 = var5;
							var10001 = false;
							break label130;
						}

						if (var25) {
							try {
								this.k = true;
							} catch (Exception var4) {
								var10000 = var4;
								var10001 = false;
								break label130;
							}
						}
					}
				}
			}

			try {
				this.j = "EasyEdge-Free";
				this.q = "no-auth";
				this.m = false;
				this.n = 0;
				return var2;
			} catch (Exception var3) {
				var10000 = var3;
				var10001 = false;
			}
		}

		Exception var21 = var10000;
		var21.printStackTrace();
		throw new CallException(1002, "infer_cfg.json parse error: " + var21.getMessage(), var21);
	}

	private void a(String var1, String var2, String var3, String var4) throws CallException {
		JSONObject var5 = null;
		JSONObject var6 = null;
		if (!TextUtils.isEmpty(var4)) {
			if (!TextUtils.isEmpty(var1)) {
				var5 = this.a(var1);
			}

			JSONObject var10000 = var6 = this.b(var4);

			String var9;
			try {
				var9 = var10000.getString("pre_process");
			} catch (JSONException var8) {
				var8.printStackTrace();
				throw new CallException(1001, "No pre_process config", var8);
			}

			var2 = var9;
		} else {
			if (TextUtils.isEmpty(var1) && TextUtils.isEmpty(var2)) {
				throw new CallException(1001, "No conf.json and preprocess.json");
			}

			var5 = this.a(var1);
		}

		BaseConfig var10 = this;
		JSONObject var10001 = var5;
		JSONObject var10002 = var6;
		this.d(var2);
		this.c(var3);

		try {
			var10.a(var10001, var10002);
		} catch (JSONException var7) {
			throw new CallException(1001, "parse JsonError ", var7);
		}
	}

	private void c(String var1) {
		String[] var6 = var1.replaceAll("\r", "").split("\n");
		if (this.i == 100 && this.v.h() == 6) {
			ArrayList var10001 = new ArrayList(Arrays.asList(var6));
			var10001.add(0, "#");
			var10001.add(" ");
			this.b = (String[])var10001.toArray(new String[0]);
		} else {
			ArrayList var2;
			var2 = new ArrayList();
			int var3 = var6.length;

			for(int var4 = 0; var4 < var3; ++var4) {
				String var5 = var6[var4];
				if (Pattern.matches("\\d+:.+", var5)) {
					var2.add(var5.substring(var5.indexOf(58) + 1));
				} else {
					var2.add(var5);
				}
			}

			this.b = (String[])var2.toArray(new String[0]);
		}

		this.c = this.b.length;
	}

	private void d(String var1) throws CallException {
		this.v = new d(var1, this.i, this.f);
	}

	public boolean isOptModel() {
		return this.l;
	}

	public boolean isEnc() {
		return this.m;
	}

	public int getMid() {
		return this.d;
	}

	public void setMid(int var1) {
		this.d = var1;
	}

	public int getRid() {
		return this.e;
	}

	public void setRid(int var1) {
		this.e = var1;
	}

	public float getRecommendedConfidence() {
		return this.g;
	}

	public void setRecommendedConfidence(int var1) {
		this.g = (float)var1;
	}

	public boolean isAcceleration() {
		return this.k;
	}

	public void setAcceleration(boolean var1) {
		this.k = var1;
	}

	public String getProduct() {
		return this.j;
	}

	public void setProduct(String var1) {
		this.j = var1;
	}

	public String getModelFileAssetPath() {
		return this.a;
	}

	public void setModelFileAssetPath(String var1) {
		this.a = var1;
	}

	public int getCateNum() {
		return this.c;
	}

	public void setCateNum(int var1) {
		this.c = var1;
	}

	public int getNType() {
		return this.f;
	}

	public String[] getLabels() {
		return this.b;
	}

	public void setLabels(String[] var1) {
		this.b = var1;
	}

	public int getModelType() {
		return this.i;
	}

	public void setModelType(int var1) {
		this.i = var1;
	}

	public boolean isHWC() {
		return this.v.c().equals("HWC");
	}

	public boolean isRGB() {
		return this.v.d().equals("RGB");
	}

	public int getModelEncValue() {
		return this.n;
	}

	public int getTargetSize() {
		return this.v.t();
	}

	public int getMaxSize() {
		return this.v.g();
	}

	public float[] getImgMeans() {
		return this.v.f();
	}

	public float[] getScales() {
		return this.v.s();
	}

	public String getColorFormat() {
		return this.v.d();
	}

	public String getChannelOrder() {
		return this.v.c();
	}

	public int getImageWidth() {
		return this.v.q();
	}

	public int getImageHeight() {
		return this.v.p();
	}

	public d getPreprocessConfig() {
		return this.v;
	}

	protected JSONObject a(String var1) throws CallException {
		Exception var10000;
		label229: {
			boolean var10001;
			JSONObject var41;
			try {
				var41 = new JSONObject();
			} catch (Exception var38) {
				var10000 = var38;
				var10001 = false;
				break label229;
			}

			JSONObject var2;
			JSONObject var43 = var2 = var41;

			boolean var42;
			try {
				var43 = new JSONObject(var1);
				var42 = var41.has("thresholdRec");
			} catch (JSONException var37) {
				var10000 = var37;
				var10001 = false;
				break label229;
			}

			if (var42) {
				BaseConfig var44;
				double var45;
				try {
					var44 = this;
					var45 = var2.getDouble("thresholdRec");
				} catch (JSONException var36) {
					var10000 = var36;
					var10001 = false;
					break label229;
				}

				float var46 = (float)var45;

				try {
					var44.g = var46;
				} catch (Exception var35) {
					var10000 = var35;
					var10001 = false;
					break label229;
				}
			} else {
				try {
					this.g = 0.3F;
				} catch (Exception var34) {
					var10000 = var34;
					var10001 = false;
					break label229;
				}
			}

			try {
				var42 = var2.has("nType");
			} catch (Exception var33) {
				var10000 = var33;
				var10001 = false;
				break label229;
			}

			if (var42) {
				try {
					this.f = var2.getInt("nType");
				} catch (JSONException var32) {
					var10000 = var32;
					var10001 = false;
					break label229;
				}
			} else {
				try {
					this.f = 0;
				} catch (Exception var31) {
					var10000 = var31;
					var10001 = false;
					break label229;
				}
			}

			try {
				var42 = var2.has("extra");
			} catch (Exception var30) {
				var10000 = var30;
				var10001 = false;
				break label229;
			}

			if (var42) {
				try {
					var41 = var2.getJSONObject("extra");
				} catch (JSONException var29) {
					var10000 = var29;
					var10001 = false;
					break label229;
				}

				JSONObject var40 = var41;

				try {
					var42 = var41.has("fluid");
				} catch (Exception var28) {
					var10000 = var28;
					var10001 = false;
					break label229;
				}

				if (var42) {
					try {
						var41 = var40.getJSONObject("fluid");
					} catch (JSONException var27) {
						var10000 = var27;
						var10001 = false;
						break label229;
					}

					JSONObject var3 = var41;

					try {
						var42 = var41.has("optType");
					} catch (Exception var26) {
						var10000 = var26;
						var10001 = false;
						break label229;
					}

					if (var42) {
						try {
							var42 = var3.getString("optType").equals("nb");
						} catch (JSONException var25) {
							var10000 = var25;
							var10001 = false;
							break label229;
						}

						if (var42) {
							try {
								this.l = true;
							} catch (Exception var24) {
								var10000 = var24;
								var10001 = false;
								break label229;
							}
						}
					}

					try {
						var42 = var3.has("quantization");
					} catch (Exception var23) {
						var10000 = var23;
						var10001 = false;
						break label229;
					}

					if (var42) {
						try {
							var42 = var3.getString("quantization").equals("int8");
						} catch (JSONException var22) {
							var10000 = var22;
							var10001 = false;
							break label229;
						}

						if (var42) {
							try {
								this.k = true;
							} catch (Exception var21) {
								var10000 = var21;
								var10001 = false;
								break label229;
							}
						}
					}
				}

				try {
					this.p = var40.optString("detection", "");
				} catch (Exception var20) {
					var10000 = var20;
					var10001 = false;
					break label229;
				}
			}

			try {
				var42 = var2.has("authType");
			} catch (Exception var19) {
				var10000 = var19;
				var10001 = false;
				break label229;
			}

			String var47;
			if (var42) {
				try {
					this.q = var2.getString("authType");
				} catch (JSONException var18) {
					var10000 = var18;
					var10001 = false;
					break label229;
				}

				var47 = "acceleration";

				try {
					var42 = var47.equals(this.q);
				} catch (Exception var17) {
					var10000 = var17;
					var10001 = false;
					break label229;
				}

				if (var42) {
					try {
						this.k = true;
					} catch (Exception var16) {
						var10000 = var16;
						var10001 = false;
						break label229;
					}
				}
			}

			try {
				this.i = var2.optInt("modelType");
				this.e = var2.optInt("releaseId");
				this.d = var2.optInt("modelId");
				this.j = var2.optString("product");
			} catch (Exception var15) {
				var10000 = var15;
				var10001 = false;
				break label229;
			}

			var47 = "no-auth";

			try {
				var42 = var47.equals(this.q);
			} catch (Exception var14) {
				var10000 = var14;
				var10001 = false;
				break label229;
			}

			label151: {
				if (!var42) {
					label234: {
						try {
							var47 = this.q;
						} catch (Exception var13) {
							var10000 = var13;
							var10001 = false;
							break label229;
						}

						if (var47 == null) {
							try {
								var42 = this.j.equals("EasyEdge-Free");
							} catch (Exception var12) {
								var10000 = var12;
								var10001 = false;
								break label229;
							}

							if (var42) {
								break label234;
							}
						}

						try {
							var42 = this.j.equals("BML-Free");
						} catch (Exception var11) {
							var10000 = var11;
							var10001 = false;
							break label229;
						}

						if (!var42) {
							break label151;
						}
					}
				}

				try {
					this.m = false;
				} catch (Exception var10) {
					var10000 = var10;
					var10001 = false;
					break label229;
				}
			}

			try {
				var42 = var2.has("modelEnc");
			} catch (Exception var9) {
				var10000 = var9;
				var10001 = false;
				break label229;
			}

			if (var42) {
				try {
					this.n = var2.getInt("modelEnc");
				} catch (JSONException var8) {
					var10000 = var8;
					var10001 = false;
					break label229;
				}
			} else {
				try {
					var42 = this.j.equals("EasyDL-Pro");
				} catch (Exception var7) {
					var10000 = var7;
					var10001 = false;
					break label229;
				}

				if (var42) {
					try {
						this.n = 2;
					} catch (Exception var6) {
						var10000 = var6;
						var10001 = false;
						break label229;
					}
				} else {
					try {
						this.n = 1;
					} catch (Exception var5) {
						var10000 = var5;
						var10001 = false;
						break label229;
					}
				}
			}

			try {
				this.h = var2.optString("modelName", "");
				return var2;
			} catch (Exception var4) {
				var10000 = var4;
				var10001 = false;
			}
		}

		Exception var39 = var10000;
		var39.printStackTrace();
		throw new CallException(1002, " conf.json parse error " + var39.getMessage(), var39);
	}

	protected void a(JSONObject var1, JSONObject var2) throws JSONException {
	}

	public String getUserDeviceId() {
		return this.o;
	}

	public void setUserDeviceId(String var1) {
		this.o = var1;
	}

	public String getAuthType() {
		return this.q;
	}

	public void setAuthType(String var1) {
		this.q = var1;
	}

	public String getAuthMode() {
		return this.r;
	}

	public long getAuthInterval() {
		return this.s;
	}

	public void setInstanceAuthMode() {
		this.setInstanceAuthMode(3600L);
	}

	public void setInstanceAuthMode(long var1) {
		this.s = 3600L;
		if (var1 > 5L) {
			this.s = var1;
		}

		this.r = "instance";
	}

	public void setAuthDomain(String var1) {
		this.t = var1;
	}

	public String getAuthDomain() {
		return this.t;
	}

	public void setDeviceLicenseUri(String var1) {
		this.u = var1;
	}

	public String getDeviceLicenseUri() {
		return this.u;
	}

	public String getExtraDetectionJson() {
		return !TextUtils.isEmpty(this.p) ? this.p : this.v.e();
	}

	public String getModelName() {
		return this.h;
	}

	public String getModelInfo() {
		JSONObject var10000 = new JSONObject();
		JSONObject var10001 = var10000;
		JSONObject var10002 = var10000;
		JSONObject var1;
		JSONObject var10003 = var1 = var10000;

		String var10004 = "modelId";

		Exception var7;
		label53: {
			boolean var8;
			try {
				var10003.put(var10004, this.getMid());
			} catch (JSONException var6) {
				var7 = var6;
				var8 = false;
				break label53;
			}

			String var13 = "modelName";

			try {
				var10002.put(var13, this.getModelName());
			} catch (JSONException var5) {
				var7 = var5;
				var8 = false;
				break label53;
			}

			String var9 = "modelType";

			try {
				var10001.put(var9, this.getModelType());
			} catch (JSONException var4) {
				var7 = var4;
				var8 = false;
				break label53;
			}

			String var10 = "thresholdRec";

			float var11;
			try {
				var11 = this.getRecommendedConfidence();
			} catch (Exception var3) {
				var7 = var3;
				var8 = false;
				break label53;
			}

			double var12 = (double)var11;

			try {
				var10000.put(var10, var12);
				return var1.toString();
			} catch (JSONException var2) {
				var7 = var2;
				var8 = false;
			}
		}

		var7.printStackTrace();
		return var1.toString();
	}
}
