//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.ddk;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.CallException;
import com.baidu.ai.edge.core.util.FileUtil;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DDKConfig extends BaseConfig {
	public static final String SDK_CONFIG_PATH = "sdk-config.json";

	public DDKConfig(AssetManager var1, String var2) throws Throwable {
		super(var1, var2);
		String var10000 = var2;

		Exception var41;
		label150: {
			IOException var42;
			label155: {
				boolean var10001;
				boolean var43;
				try {
					var43 = var2.startsWith("file:///");
				} catch (Exception var35) {
					var41 = var35;
					var10001 = false;
					break label150;
				}

				StringBuilder var10002;
				StringBuilder var45;
				if (var43) {
					StringBuilder var44;
					try {
						var44 = new StringBuilder();
					} catch (Exception var33) {
						var41 = var33;
						var10001 = false;
						break label150;
					}

					var45 = var44;

					try {
						var45 = new StringBuilder();
						var10000 = FileUtil.readFile(var44.append(var2.substring(7)).append("/").append("sdk-config.json").toString());
					} catch (IOException var30) {
						var42 = var30;
						var10001 = false;
						break label155;
					} catch (Exception var31) {
						var41 = var31;
						var10001 = false;
						break label150;
					}
				} else {
					AssetManager var46;
					try {
						var46 = var1;
						var45 = new StringBuilder();
					}  catch (Exception var29) {
						var41 = var29;
						var10001 = false;
						break label150;
					}

					var10002 = var45;

					try {
						var10002 = new StringBuilder();
						var10000 = FileUtil.readAssetFileUtf8String(var46, var45.append(var2).append("/").append("sdk-config.json").toString());
					} catch (IOException var26) {
						var42 = var26;
						var10001 = false;
						break label155;
					} catch (Exception var27) {
						var41 = var27;
						var10001 = false;
						break label150;
					}
				}

				String var38 = var10000;

				JSONArray var47;
				try {
					var47 = new JSONArray();
				}  catch (Exception var25) {
					var41 = var25;
					var10001 = false;
					break label150;
				}

				JSONArray var3 = var47;

				try {
					var47 = new JSONArray(var38);
				} catch (JSONException var23) {
					var41 = var23;
					var10001 = false;
					break label150;
				}

				int var39 = 0;

				while(true) {
					int var48;
					int var49;
					try {
						var49 = var39;
						var48 = var3.length();
					}  catch (Exception var21) {
						var41 = var21;
						var10001 = false;
						break label150;
					}

					if (var49 >= var48) {
						return;
					}

					JSONObject var50;
					try {
						var50 = var3.getJSONObject(var39);
					}  catch (JSONException var19) {
						var41 = var19;
						var10001 = false;
						break label150;
					}

					JSONObject var4 = var50;

					try {
						var43 = var50.has("id");
					} catch (Exception var17) {
						var41 = var17;
						var10001 = false;
						break label150;
					}

					if (var43) {
						try {
							var43 = var4.getString("id").equals("ddk200");
						}  catch (JSONException var15) {
							var41 = var15;
							var10001 = false;
							break label150;
						}

						if (var43) {
							DDKConfig var51;
							try {
								var51 = this;
								var45 = new StringBuilder();
							} catch (Exception var13) {
								var41 = var13;
								var10001 = false;
								break label150;
							}

							var10002 = var45;

							try {
								var10002 = new StringBuilder();
								var45 = var45.append(var2).append("/").append(var4.getString("params"));
							}  catch (JSONException var11) {
								var41 = var11;
								var10001 = false;
								break label150;
							}

							StringBuilder var40 = var45;

							try {
								var43 = var51.isEnc();
							}  catch (Exception var9) {
								var41 = var9;
								var10001 = false;
								break label150;
							}

							String var5;
							if (var43) {
								var5 = ".enc";
							} else {
								var5 = "";
							}

							try {
								super.a = var40.append(var5).toString();
							}  catch (Exception var7) {
								var41 = var7;
								var10001 = false;
								break label150;
							}
						}
					}

					++var39;
				}
			}

			IOException var37 = var42;
			throw new CallException(1001, "sdk-config read asset file error " + var2, var37);
		}

		Exception var36 = var41;
		var36.printStackTrace();
		throw new CallException(1002, " sdk-config parse json error ", var36);
	}

	protected void a(JSONObject var1, JSONObject var2) throws JSONException {
		super.a(var1, var2);
		super.v.a("CHW");
	}

	public String getSoc() {
		return "npu";
	}
}
