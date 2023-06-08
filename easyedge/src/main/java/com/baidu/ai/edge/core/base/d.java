//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
	protected String i = "";
	protected int j;
	protected int k;
	private int l = -1;
	private boolean m;
	private boolean n;
	private int o = 320;
	private int p = 32;
	private int q = 1;
	private int r;
	private int s;
	private boolean t;
	private String u = "padding_align32";
	private int v = 0;
	private int w = 0;
	private int[] x = new int[]{114, 114, 114};

	public d(String var1, int var2, int var3) throws CallException {
		Exception var63;
		label383: {
			boolean var10001;
			JSONObject var64;
			try {
				var64 = new JSONObject();
			} catch (Exception var57) {
				var63 = var57;
				var10001 = false;
				break label383;
			}
			Log.e("TEST##", "BaseConfig...101.7"+var1);
			JSONObject var61;

			try {
				var61 = new JSONObject(var1);
			} catch (JSONException var56) {
				var63 = var56;
				var10001 = false;
				break label383;
			}

			JSONArray var59;
			JSONArray var65;
			d var66;
			JSONArray var67;
			boolean var69;
			JSONArray var70;
			int var72;
			if (var2 == 100) {
				try {
					this.k = var61.getInt("max_size");
					var65 = var61.optJSONArray("ocr_rec_resize");
				} catch (JSONException var55) {
					var63 = var55;
					var10001 = false;
					break label383;
				}
				var59 = var65;
				if (var65 != null) {
					try {
						this.o = var59.getInt(0);
						this.p = var59.getInt(1);
					} catch (JSONException var54) {
						var63 = var54;
						var10001 = false;
						break label383;
					}
				}

				var66 = this;
				var64 = var61;
				String var10002 = "ocr_rec_batch_num";

				try {
					var66.q = var64.optInt(var10002, this.q);
				} catch (Exception var53) {
					var63 = var53;
					var10001 = false;
					break label383;
				}
			} else {
				try {
					var69 = var61.has("resize");
				} catch (Exception var52) {
					var63 = var52;
					var10001 = false;
					break label383;
				}

				if (var69) {
					try {
						var66 = this;
						var70 = var61.getJSONArray("resize");
					} catch (JSONException var51) {
						var63 = var51;
						var10001 = false;
						break label383;
					}

					var67 = var70;

					try {
						this.a = var67.optInt(0);
						var66.b = var70.optInt(1);
					} catch (Exception var50) {
						var63 = var50;
						var10001 = false;
						break label383;
					}
				}

				d var71 = this;
				JSONObject var68 = var61;
				String var10003 = "rescale_mode";

				try {
					var71.g = var68.optString(var10003, this.g);
				} catch (Exception var49) {
					var63 = var49;
					var10001 = false;
					break label383;
				}
				Log.e("TEST##", "BaseConfig...101.9");
				if (var3 == 102) {
					try {
						this.g = "keep_ratio";
					} catch (Exception var48) {
						var63 = var48;
						var10001 = false;
						break label383;
					}
				}

				try {
					var69 = this.g.equals("keep_ratio");
				} catch (Exception var47) {
					var63 = var47;
					var10001 = false;
					break label383;
				}

				if (var69) {
					try {
						var69 = var61.has("target_size");
					} catch (Exception var46) {
						var63 = var46;
						var10001 = false;
						break label383;
					}

					if (var69) {
						try {
							var66 = this;
							var72 = var61.getInt("target_size");
						} catch (JSONException var45) {
							var63 = var45;
							var10001 = false;
							break label383;
						}
					} else {
						try {
							var66 = this;
							var72 = this.a;
						} catch (Exception var44) {
							var63 = var44;
							var10001 = false;
							break label383;
						}
					}

					try {
						var66.j = var72;
						var69 = var61.has("max_size");
					} catch (Exception var43) {
						var63 = var43;
						var10001 = false;
						break label383;
					}

					if (var69) {
						try {
							var66 = this;
							var72 = var61.getInt("max_size");
						} catch (JSONException var42) {
							var63 = var42;
							var10001 = false;
							break label383;
						}
					} else {
						try {
							var66 = this;
							var72 = this.b;
						} catch (Exception var41) {
							var63 = var41;
							var10001 = false;
							break label383;
						}
					}

					try {
						var66.k = var72;
					} catch (Exception var40) {
						var63 = var40;
						var10001 = false;
						break label383;
					}
				} else {
					try {
						var69 = this.g.equals("warp_affine");
					} catch (Exception var39) {
						var63 = var39;
						var10001 = false;
						break label383;
					}

					if (var69) {
						try {
							var69 = var61.has("warp_affine_keep_res");
						} catch (Exception var38) {
							var63 = var38;
							var10001 = false;
							break label383;
						}

						if (var69) {
							try {
								this.h = var61.getBoolean("warp_affine_keep_res");
							} catch (JSONException var37) {
								var63 = var37;
								var10001 = false;
								break label383;
							}
						}
					}
				}
			}

			if (var3 == 102 || var3 == 2010) {
				try {
					this.t = true;
				} catch (Exception var36) {
					var63 = var36;
					var10001 = false;
					break label383;
				}
			}

			try {
				var69 = var61.has("padding_mode");
			} catch (Exception var35) {
				var63 = var35;
				var10001 = false;
				break label383;
			}

			if (var69) {
				try {
					this.u = var61.getString("padding_mode");
					var69 = this.u.equals("padding_align32");
				} catch (JSONException var34) {
					var63 = var34;
					var10001 = false;
					break label383;
				}

				if (var69) {
					try {
						this.t = true;
					} catch (Exception var33) {
						var63 = var33;
						var10001 = false;
						break label383;
					}
				} else {
					try {
						var69 = this.u.equals("padding_fill_size");
					} catch (Exception var32) {
						var63 = var32;
						var10001 = false;
						break label383;
					}

					if (var69) {
						try {
							this.t = true;
							var69 = var61.has("padding_fill_size");
						} catch (Exception var31) {
							var63 = var31;
							var10001 = false;
							break label383;
						}

						if (var69) {
							try {
								var66 = this;
								var70 = var61.getJSONArray("padding_fill_size");
							} catch (JSONException var30) {
								var63 = var30;
								var10001 = false;
								break label383;
							}

							var67 = var70;

							try {
								this.v = var67.optInt(0);
								var66.w = var70.optInt(1);
							} catch (Exception var29) {
								var63 = var29;
								var10001 = false;
								break label383;
							}
						}

						try {
							var69 = var61.has("padding_fill_value");
						} catch (Exception var28) {
							var63 = var28;
							var10001 = false;
							break label383;
						}

						if (var69) {
							try {
								var65 = var61.getJSONArray("padding_fill_value");
							} catch (JSONException var27) {
								var63 = var27;
								var10001 = false;
								break label383;
							}

							var59 = var65;

							int var10000;
							try {
								var10000 = var65.length();
							} catch (Exception var26) {
								var63 = var26;
								var10001 = false;
								break label383;
							}

							if (var10000 == 1) {
								try {
									var66 = this;
									var72 = var59.optInt(0);
								} catch (Exception var25) {
									var63 = var25;
									var10001 = false;
									break label383;
								}

								int var60 = var72;

								try {
									var66.x = new int[]{var60, var60, var60};
								} catch (Exception var24) {
									var63 = var24;
									var10001 = false;
									break label383;
								}
							} else {
								try {
									var10000 = var59.length();
								} catch (Exception var23) {
									var63 = var23;
									var10001 = false;
									break label383;
								}

								if (var10000 == 3) {
									for(var3 = 0; var3 < 3; ++var3) {
										try {
											this.x[var3] = var59.optInt(var3);
										} catch (Exception var22) {
											var63 = var22;
											var10001 = false;
											break label383;
										}
									}
								} else {
									try {
										Log.e("PreprocessConfig", "Padding fill value dims must be 1 or 3.");
									} catch (Exception var21) {
										var63 = var21;
										var10001 = false;
										break label383;
									}
								}
							}
						}
					}
				}
			}

			try {
				var65 = var61.optJSONArray("img_mean");
			} catch (Exception var20) {
				var63 = var20;
				var10001 = false;
				break label383;
			}

			var59 = var65;
			if (var65 == null) {
				try {
					var65 = var61.getJSONArray("mean");
				} catch (JSONException var19) {
					var63 = var19;
					var10001 = false;
					break label383;
				}

				var59 = var65;
			}

			try {
				var66 = this;
				var64 = var61;
				this.c = BaseConfig.getArrayFloatValues(var59);
			} catch (JSONException var18) {
				var63 = var18;
				var10001 = false;
				break label383;
			}

			label381: {
				label382: {
					double var73;
					try {
						var73 = var64.getDouble("scale");
					} catch (JSONException var17) {
						var10001 = false;
						break label382;
					}

					float var62 = (float)var73;

					try {
						var66.d = new float[]{var62, var62, var62};
						break label381;
					} catch (Exception var16) {
						var10001 = false;
					}
				}

				try {
					this.d = BaseConfig.getArrayFloatValues(var61.getJSONArray("scale"));
				} catch (JSONException var15) {
					var63 = var15;
					var10001 = false;
					break label383;
				}
			}

			try {
				this.n = var61.optBoolean("skip_norm", false);
				var69 = this.n;
			} catch (Exception var14) {
				var63 = var14;
				var10001 = false;
				break label383;
			}

			if (var69) {
				try {
					this.d = new float[]{1.0F, 1.0F, 1.0F};
					this.c = new float[]{0.0F, 0.0F, 0.0F};
					Log.i("PreprocessConfig", "skip_norm");
				} catch (Exception var13) {
					var63 = var13;
					var10001 = false;
					break label383;
				}
			}

			try {
				this.e = var61.optString("colorFormat");
				var69 = TextUtils.isEmpty(this.e);
			} catch (Exception var12) {
				var63 = var12;
				var10001 = false;
				break label383;
			}

			if (var69) {
				try {
					this.e = var61.getString("color_format");
				} catch (JSONException var11) {
					var63 = var11;
					var10001 = false;
					break label383;
				}
			}

			try {
				this.f = var61.optString("channelOrder");
				var69 = TextUtils.isEmpty(this.f);
			} catch (Exception var10) {
				var63 = var10;
				var10001 = false;
				break label383;
			}

			if (var69) {
				try {
					this.f = var61.optString("channel_order", "");
				} catch (Exception var9) {
					var63 = var9;
					var10001 = false;
					break label383;
				}
			}

			try {
				var69 = var61.has("extra");
			} catch (Exception var8) {
				var63 = var8;
				var10001 = false;
				break label383;
			}

			if (var69) {
				try {
					this.i = var61.getJSONObject("extra").optString("detection", "");
				} catch (JSONException var7) {
					var63 = var7;
					var10001 = false;
					break label383;
				}
			}

			try {
				this.m = var61.optBoolean("letterbox");
				var69 = var61.has("center_crop_size");
			} catch (Exception var6) {
				var63 = var6;
				var10001 = false;
				break label383;
			}

			if (!var69) {
				return;
			}

			try {
				var66 = this;
				var70 = var61.getJSONArray("center_crop_size");
			} catch (JSONException var5) {
				var63 = var5;
				var10001 = false;
				break label383;
			}

			var67 = var70;

			try {
				this.r = var67.getInt(0);
				var66.s = var70.getInt(1);
				return;
			} catch (JSONException var4) {
				var63 = var4;
				var10001 = false;
			}
		}

		Exception var58 = var63;
		var58.printStackTrace();
		throw new CallException(1002, " preprocess_args parse json error ", var58);
	}

	public int q() {
		return this.a;
	}

	public int p() {
		return this.b;
	}

	public float[] f() {
		return this.c;
	}

	public float[] s() {
		return this.d;
	}

	public String d() {
		return this.e;
	}

	public String r() {
		return this.g;
	}

	public int t() {
		return this.j;
	}

	public int g() {
		return this.k;
	}

	public String c() {
		return this.f;
	}

	public void a(String var1) {
		this.f = var1;
	}

	public int o() {
		return this.l;
	}

	public void a(int var1) {
		this.l = var1;
	}

	public String e() {
		return this.i;
	}

	public boolean u() {
		return this.m;
	}

	public boolean w() {
		return this.n;
	}

	public int j() {
		return this.o;
	}

	public int i() {
		return this.p;
	}

	public int h() {
		return this.q;
	}

	public int b() {
		return this.r;
	}

	public int a() {
		return this.s;
	}

	public boolean x() {
		return this.h;
	}

	public boolean v() {
		return this.t;
	}

	public String m() {
		return this.u;
	}

	public int l() {
		return this.v;
	}

	public int k() {
		return this.w;
	}

	public int[] n() {
		return this.x;
	}
}
