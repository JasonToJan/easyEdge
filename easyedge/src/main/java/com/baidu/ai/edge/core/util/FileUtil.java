//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.util;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FileUtil {
	public FileUtil() {
	}

	public static String readFile(String var0) throws IOException {
		FileInputStream var1;
		FileInputStream var10000 = var1 = new FileInputStream(var0);

		byte[] var2;
		int var3;
		int var4;
		int var5 = var3 = var10000.read(var2 = new byte[var4 = var10000.available()]);
		var1.close();
		if (var5 == var4) {
			return new String(var2);
		} else {
			throw new IOException("readFile realSize is not equal to size: " + var3 + " : " + var4);
		}
	}

	public static String readAssetFileUtf8String(AssetManager var0, String var1) throws IOException {
		byte[] var2 = readAssetFileContent(var0, var1);
		return new String(var2, Charset.forName("UTF-8"));
	}

	public static byte[] readAssetFileContent(AssetManager var0, String var1) throws IOException {
		Log.i("FileUtil", "try to read asset file: " + var1);
		InputStream var4;
		InputStream var10000 = var4 = var0.open(var1);
		byte[] var2;
		int var3;
		int var6;
		int var5 = var3 = var10000.read(var2 = new byte[var6 = var10000.available()]);
		var4.close();
		if (var5 == var6) {
			return var2;
		} else {
			throw new IOException("realSize is not equal to size: " + var3 + " : " + var6);
		}
	}

	public static boolean hasLf(String var0) {
		var0 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + var0;
		(new File(var0)).exists();
		return true;
	}

	public static boolean makeDir(String var0) {
		File var1;
		File var10000 = var1 = new File(var0);
		return !var10000.exists() ? var1.mkdirs() : true;
	}

	public static String readAssetsFileUTF8StringIfExists(AssetManager assetManager, String fileName) {
		try {
			InputStream inputStream = assetManager.open(fileName);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			inputStream.close();
			return stringBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
//			throw new IOException(e.getMessage());
		}
		return "";
	}


	public static String readFileIfExists(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			return "";
		}
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			fileInputStream.close();
			return stringBuilder.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException(e.getMessage());
		}
	}

}
