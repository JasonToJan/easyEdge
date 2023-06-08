//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;

public class ImageUtil {
	public ImageUtil() {
	}

	public static Bitmap resize(Bitmap var0, int var1, int var2) {
		return Bitmap.createScaledBitmap(var0, var1, var2, true);
	}

	public static Pair<Integer, Integer> calcTarget(Bitmap var0, int var1, int var2) {
		int var3;
		int var10000 = var3 = var0.getWidth();
		int var4;
		int var10001 = var4 = var0.getHeight();
		Log.i("ImageUtil", "[preprocess] calcTarget wh" + var0.getWidth() + " " + var0.getHeight());
		int var5;
		int var7;
		if (var10000 > var10001) {
			var5 = var3;
			var7 = var4;
		} else {
			var5 = var4;
			var7 = var3;
		}

		float var6 = 1.0F;
		if (var1 > 0) {
			var6 = (float)var1 * 1.0F / (float)var7;
		}

		float var8;
		float var9;
		if (var6 * (var8 = (float)var5) > (var9 = (float)var2)) {
			var6 = var9 * 1.0F / var8;
		}

		var8 = var6 * (float)var3;
		var9 = var6 * (float)var4;
		return new Pair(Math.round(var8), Math.round(var9));
	}

	public static Pair<Integer, Integer> calcTargetSizeForKeepRatio2(Bitmap var0, int var1, int var2) {
		int var10000 = var1;
		var1 = var0.getWidth();
		int var3 = var0.getHeight();
		Log.i("ImageUtil", "[preprocess] calcTargetForKeepRatio2 " + var0.getWidth() + " * " + var0.getHeight());
		float var4;
		float var6;
		float var5 = Math.min((float)var10000 * 1.0F / (var4 = (float)var1), (float)var2 * 1.0F / (var6 = (float)var3));
		var4 = var5 * var4;
		var6 = var5 * var6;
		return new Pair(Math.round(var4), Math.round(var6));
	}

	public static Pair<Bitmap, Float> resizeTarget(Bitmap var0, int var1, int var2) {
		int var3;
		int var10000 = var3 = var0.getWidth();
		int var4;
		int var10001 = var4 = var0.getHeight();
		Log.i("ImageUtil", "[preprocess] origin bitmap wh" + var0.getWidth() + " " + var0.getHeight());
		int var5;
		int var6;
		if (var10000 > var10001) {
			var6 = var3;
			var5 = var4;
		} else {
			var6 = var4;
			var5 = var3;
		}

		float var7 = 1.0F;
		if (var1 > 0) {
			var7 = (float)var1 * 1.0F / (float)var5;
		}

		float var10;
		float var11;
		if (var7 * (var10 = (float)var6) > (var11 = (float)var2)) {
			var7 = var11 * 1.0F / var10;
		}

		Bitmap var9 = var0;
		float var8 = var7 * (float)var3;
		var0 = resize(var9, Math.round(var8), Math.round(var7 * (float)var4));
		Log.i("ImageUtil", "[preprocess] scale :" + var7 + " ; maxSize" + var2 + " ; targetSize" + var1);
		return new Pair(var0, var7);
	}

	public static int calPaddedValue(int var0, int var1) {
		if (var1 == 0) {
			return var0;
		} else {
			return (var1 = var0 % var1) == 0 ? var0 : var0 + var1;
		}
	}

	public static Pair<Integer, Integer> calcShrinkSize(int var0, int var1) {
		double var2;
		double var10000 = var2 = a(var0, var1);
		var0 = Double.valueOf(var2 * (double)var0).intValue();
		var1 = Double.valueOf(var10000 * (double)var1).intValue();
		return new Pair(var0, var1);
	}

	public static Pair<Integer, Integer> calcWithStep(Bitmap var0, int var1, int var2) {
		int var3;
		int var4;
		int var5;
		if ((var4 = Math.max(var3 = var0.getWidth(), var5 = var0.getHeight())) > var1) {
			float var10000 = (float)var1 * 1.0F / (float)var4;
			var3 = (int)Math.floor((double)((float)var1 * 1.0F / (float)var4 * (float)var3));
			var5 = (int)Math.floor((double)(var10000 * (float)var5));
		}

		if ((var1 = var3 - var3 % var2) == 0) {
			var1 = var2;
		}

		if ((var5 -= var5 % var2) == 0) {
			var5 = var2;
		}

		return new Pair(var1, var5);
	}

	public static Bitmap resizeWithStep(Bitmap var0, int var1, int var2) {
		int var3;
		int var4;
		int var5;
		if ((var5 = Math.max(var3 = var0.getWidth(), var4 = var0.getHeight())) > var1) {
			float var10000 = (float)var1 * 1.0F / (float)var5;
			var3 = (int)Math.floor((double)((float)var1 * 1.0F / (float)var5 * (float)var3));
			var4 = (int)Math.floor((double)(var10000 * (float)var4));
		}

		if ((var1 = var3 - var3 % var2) == 0) {
			var1 = var2;
		}

		if ((var3 = var4 - var4 % var2) == 0) {
			var3 = var2;
		}

		return resize(var0, var1, var3);
	}

	private static double a(int var0, int var1) {
		double var4 = (double)(var1 * var0);
		double var2;
		double var10000 = var2 = Math.pow(3721808.746967071 / var4, 0.5);
		double var10001 = var4 = Math.pow(2777088.0 / var4, 0.5);
		System.out.println("maxShrinkV1:" + var2 + "   " + var4);
		var10000 = var4 = (double)(Double.valueOf(Math.min(var10000, var10001) * 100.0).intValue() - 30) / 100.0;
		System.out.println("maxShrink double:" + var4);
		if (var10000 >= 5.0) {
			var4 -= 0.5;
		} else if (var4 < 5.0 && var4 >= 4.0) {
			var4 -= 0.4;
		} else if (var4 < 4.0 && var4 >= 3.0) {
			var4 -= 0.3;
		} else if (var4 < 3.0 && var4 >= 2.0) {
			var4 -= 0.2;
		} else if (var4 < 2.0 && var4 >= 1.5) {
			var4 -= 0.1;
		} else if (var4 < 1.5 && var4 >= 0.75) {
			var4 = 0.5;
		} else if (var4 < 0.75 && var4 > 0.1) {
			var4 *= 0.5;
		} else if (var4 <= 0.1) {
			var4 = 0.1;
		}

		if (!(var4 < 1.0)) {
			var4 = 1.0;
		}

		System.out.println("shrink:" + var4);
		return var4;
	}

	public static float[] getPixels(Bitmap var0, float[] var1, float[] var2, boolean var3, boolean var4) {
		int var13;
		int var10001 = var13 = var0.getWidth();
		int var5 = var0.getHeight();
		float[] var6 = new float[var10001 * 3 * var5];
		int var7;
		int var8;
		int var9;
		int var10 = 0;
		int var11;
		if (!var3) {
			for(var7 = 0; var7 < var5; ++var7) {
				for(var8 = 0; var8 < var13; ++var8) {
					var10 += var11 = (var9 = var7 * var13 + var8) + (var10 = var13 * var5);
					int var12 = var0.getPixel(var8, var7);
					if (var4) {
						var6[var9] = ((float)Color.red(var12) - var1[0]) * var2[0];
						var6[var11] = ((float)Color.green(var12) - var1[1]) * var2[1];
						var6[var10] = ((float)Color.blue(var12) - var1[2]) * var2[2];
					} else {
						var6[var9] = ((float)Color.blue(var12) - var1[0]) * var2[0];
						var6[var11] = ((float)Color.green(var12) - var1[1]) * var2[1];
						var6[var10] = ((float)Color.red(var12) - var1[2]) * var2[2];
					}
				}
			}
		} else {
			var7 = 0;

			for(var8 = 0; var8 < var5; ++var8) {
				for(var9 = 0; var9 < var13; ++var9) {
					var10 = var0.getPixel(var9, var8);
					float var15;
					if (var4) {
						var15 = (float)Color.red(var10);
					} else {
						var15 = (float)Color.blue(var10);
					}

					var6[var7] = var15;
					var6[var7] = (var6[var7] - var1[0]) * var2[0];
					int var10002 = var11 = var7 + 1;
					var6[var11] = (float)Color.green(var10);
					var6[var10002] = (var6[var11] - var1[1]) * var2[1];
					var11 = var7 + 2;
					float var14;
					if (var4) {
						var14 = (float)Color.blue(var10);
					} else {
						var14 = (float)Color.red(var10);
					}

					var6[var11] = var14;
					var6[var11] = (var6[var11] - var1[2]) * var2[2];
					var7 += 3;
				}
			}
		}

		return var6;
	}

	public static class ResizedBitmap {
		Bitmap a;
		float b;
		float c;
		float d;

		public ResizedBitmap(Bitmap var1, float var2, float var3, float var4) {
			this.a = var1;
			this.b = var3;
			this.c = var4;
			this.d = var2;
		}

		public Bitmap getBitmap() {
			return this.a;
		}

		public float getResizedWidth() {
			return this.b;
		}

		public float getResizedHeight() {
			return this.c;
		}

		public float getScale() {
			return this.d;
		}
	}
}
