package com.baidu.ai.edge.core.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import java.io.PrintStream;

public class ImageUtil {

    public static class ResizedBitmap {
        Bitmap a;
        float b;
        float c;
        float d;

        public ResizedBitmap(Bitmap bitmap, float f, float f2, float f3) {
            this.a = bitmap;
            this.b = f2;
            this.c = f3;
            this.d = f;
        }

        public Bitmap getBitmap() {
            return this.a;
        }

        public float getResizedHeight() {
            return this.c;
        }

        public float getResizedWidth() {
            return this.b;
        }

        public float getScale() {
            return this.d;
        }
    }

    private static double a(int i, int i2) {
        double d = (double) (i2 * i);
        double d2 = 0.5d;
        double pow = Math.pow(3721808.746967071d / d, 0.5d);
        d = Math.pow(2777088.0d / d, 0.5d);
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("maxShrinkV1:");
        stringBuilder.append(pow);
        stringBuilder.append("   ");
        stringBuilder.append(d);
        printStream.println(stringBuilder.toString());
        d = ((double) (Double.valueOf(Math.min(pow, d) * 100.0d).intValue() - 30)) / 100.0d;
        PrintStream printStream2 = System.out;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("maxShrink double:");
        stringBuilder2.append(d);
        printStream2.println(stringBuilder2.toString());
        if (d >= 5.0d) {
            d2 = d - 0.5d;
        } else {
            if (d < 5.0d && d >= 4.0d) {
                pow = 0.4d;
            } else if (d < 4.0d && d >= 3.0d) {
                pow = 0.3d;
            } else if (d < 3.0d && d >= 2.0d) {
                pow = 0.2d;
            } else if (d < 2.0d && d >= 1.5d) {
                d2 = d - 0.1d;
            } else if (d >= 1.5d || d < 0.75d) {
                d2 = (d >= 0.75d || d <= 0.1d) ? d <= 0.1d ? 0.1d : d : 0.5d * d;
            }
            d2 = d - pow;
        }
        d = 1.0d;
        if (d2 < 1.0d) {
            d = d2;
        }
        printStream2 = System.out;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("shrink:");
        stringBuilder2.append(d);
        printStream2.println(stringBuilder2.toString());
        return d;
    }

    public static int calPaddedValue(int i, int i2) {
        if (i2 == 0) {
            return i;
        }
        i2 = i % i2;
        return i2 == 0 ? i : i + i2;
    }

    public static Pair<Integer, Integer> calcShrinkSize(int i, int i2) {
        double a = a(i, i2);
        return new Pair(Integer.valueOf(Double.valueOf(((double) i) * a).intValue()), Integer.valueOf(Double.valueOf(a * ((double) i2)).intValue()));
    }

    public static Pair<Integer, Integer> calcTarget(Bitmap bitmap, int i, int i2) {
        int i3;
        int i4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[preprocess] calcTarget wh");
        stringBuilder.append(bitmap.getWidth());
        stringBuilder.append(" ");
        stringBuilder.append(bitmap.getHeight());
        Log.i("ImageUtil", stringBuilder.toString());
        if (width > height) {
            i3 = width;
            i4 = height;
        } else {
            i4 = width;
            i3 = height;
        }
        float f = i > 0 ? (((float) i) * 1.0f) / ((float) i4) : 1.0f;
        float f2 = (float) i3;
        float f3 = (float) i2;
        if (f * f2 > f3) {
            f = (f3 * 1.0f) / f2;
        }
        return new Pair(Integer.valueOf(Math.round(((float) width) * f)), Integer.valueOf(Math.round(f * ((float) height))));
    }

    public static Pair<Integer, Integer> calcTargetSizeForKeepRatio2(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[preprocess] calcTargetForKeepRatio2 ");
        stringBuilder.append(bitmap.getWidth());
        stringBuilder.append(" * ");
        stringBuilder.append(bitmap.getHeight());
        Log.i("ImageUtil", stringBuilder.toString());
        float f = (float) width;
        float f2 = (float) height;
        float min = Math.min((((float) i) * 1.0f) / f, (((float) i2) * 1.0f) / f2);
        return new Pair(Integer.valueOf(Math.round(f * min)), Integer.valueOf(Math.round(min * f2)));
    }

    public static Pair<Integer, Integer> calcWithStep(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int max = Math.max(width, height);
        if (max > i) {
            float f = (((float) i) * 1.0f) / ((float) max);
            width = (int) Math.floor((double) (((float) width) * f));
            height = (int) Math.floor((double) (f * ((float) height)));
        }
        i = width - (width % i2);
        if (i == 0) {
            i = i2;
        }
        height -= height % i2;
        if (height == 0) {
            height = i2;
        }
        return new Pair(Integer.valueOf(i), Integer.valueOf(height));
    }

    public static float[] getPixels(Bitmap bitmap, float[] fArr, float[] fArr2, boolean z, boolean z2) {
        Bitmap bitmap2 = bitmap;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float[] fArr3 = new float[((width * 3) * height)];
        int i = 0;
        int i2;
        int i3;
        int pixel;
        int i4;
        if (z) {
            i2 = 0;
            while (i < height) {
                i3 = i2;
                for (i2 = 0; i2 < width; i2++) {
                    pixel = bitmap.getPixel(i2, i);
                    fArr3[i3] = (float) (z2 ? Color.red(pixel) : Color.blue(pixel));
                    fArr3[i3] = (fArr3[i3] - fArr[0]) * fArr2[0];
                    i4 = i3 + 1;
                    fArr3[i4] = (float) Color.green(pixel);
                    fArr3[i4] = (fArr3[i4] - fArr[1]) * fArr2[1];
                    i4 = i3 + 2;
                    fArr3[i4] = (float) (z2 ? Color.blue(pixel) : Color.red(pixel));
                    fArr3[i4] = (fArr3[i4] - fArr[2]) * fArr2[2];
                    i3 += 3;
                }
                i++;
                i2 = i3;
            }
        } else {
            while (i < height) {
                for (i2 = 0; i2 < width; i2++) {
                    i3 = (i * width) + i2;
                    pixel = width * height;
                    i4 = i3 + pixel;
                    pixel += i4;
                    int pixel2 = bitmap.getPixel(i2, i);
                    if (z2) {
                        fArr3[i3] = (((float) Color.red(pixel2)) - fArr[0]) * fArr2[0];
                        fArr3[i4] = (((float) Color.green(pixel2)) - fArr[1]) * fArr2[1];
                        fArr3[pixel] = (((float) Color.blue(pixel2)) - fArr[2]) * fArr2[2];
                    } else {
                        fArr3[i3] = (((float) Color.blue(pixel2)) - fArr[0]) * fArr2[0];
                        fArr3[i4] = (((float) Color.green(pixel2)) - fArr[1]) * fArr2[1];
                        fArr3[pixel] = (((float) Color.red(pixel2)) - fArr[2]) * fArr2[2];
                    }
                }
                i++;
            }
        }
        return fArr3;
    }

    public static Bitmap resize(Bitmap bitmap, int i, int i2) {
        return Bitmap.createScaledBitmap(bitmap, i, i2, true);
    }

    public static Pair<Bitmap, Float> resizeTarget(Bitmap bitmap, int i, int i2) {
        int i3;
        int i4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[preprocess] origin bitmap wh");
        stringBuilder.append(bitmap.getWidth());
        stringBuilder.append(" ");
        stringBuilder.append(bitmap.getHeight());
        String str = "ImageUtil";
        Log.i(str, stringBuilder.toString());
        if (width > height) {
            i3 = width;
            i4 = height;
        } else {
            i4 = width;
            i3 = height;
        }
        float f = i > 0 ? (((float) i) * 1.0f) / ((float) i4) : 1.0f;
        float f2 = (float) i3;
        float f3 = (float) i2;
        if (f * f2 > f3) {
            f = (f3 * 1.0f) / f2;
        }
        bitmap = resize(bitmap, Math.round(((float) width) * f), Math.round(((float) height) * f));
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[preprocess] scale :");
        stringBuilder2.append(f);
        stringBuilder2.append(" ; maxSize");
        stringBuilder2.append(i2);
        stringBuilder2.append(" ; targetSize");
        stringBuilder2.append(i);
        Log.i(str, stringBuilder2.toString());
        return new Pair(bitmap, Float.valueOf(f));
    }

    public static Bitmap resizeWithStep(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int max = Math.max(width, height);
        if (max > i) {
            float f = (((float) i) * 1.0f) / ((float) max);
            width = (int) Math.floor((double) (((float) width) * f));
            height = (int) Math.floor((double) (f * ((float) height)));
        }
        i = width - (width % i2);
        if (i == 0) {
            i = i2;
        }
        width = height - (height % i2);
        if (width != 0) {
            i2 = width;
        }
        return resize(bitmap, i, i2);
    }
}
