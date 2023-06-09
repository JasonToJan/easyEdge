package com.baidu.ai.edge.core.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class FileUtil {
    public static boolean hasLf(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        stringBuilder.append("/");
        stringBuilder.append(str);
        new File(stringBuilder.toString()).exists();
        return true;
    }

    public static boolean makeDir(String str) {
        File file = new File(str);
        return !file.exists() ? file.mkdirs() : true;
    }

    public static byte[] readAssetFileContent(AssetManager assetManager, String str) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("try to read asset file: ");
        stringBuilder.append(str);
        Log.i("FileUtil", stringBuilder.toString());
        InputStream open = assetManager.open(str);
        int available = open.available();
        byte[] bArr = new byte[available];
        int read = open.read(bArr);
        open.close();
        if (read == available) {
            return bArr;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("realSize is not equal to size: ");
        stringBuilder.append(read);
        stringBuilder.append(" : ");
        stringBuilder.append(available);
        throw new IOException(stringBuilder.toString());
    }

    public static String readAssetFileUtf8String(AssetManager assetManager, String str) throws IOException {
        return new String(readAssetFileContent(assetManager, str), Charset.forName("UTF-8"));
    }

    public static String readAssetsFileUTF8StringIfExists(AssetManager assetManager, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("try to read asset file: ");
        stringBuilder.append(fileName);
        String logMessage = stringBuilder.toString();
        Log.i("FileUtil", logMessage);

        InputStream inputStream = null;
        String result = null;

        try {
            inputStream = assetManager.open(fileName);
            int availableBytes = inputStream.available();
            byte[] buffer = new byte[availableBytes];
            int bytesRead = inputStream.read(buffer);

            if (bytesRead == availableBytes) {
                result = new String(buffer, Charset.forName("UTF-8"));
            } else {
                throw new IOException("realSize is not equal to size: " + bytesRead + " : " + availableBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static String readFile(String str) throws IOException {
        Log.e("TEST##", "str=="+str);
        FileInputStream fileInputStream = new FileInputStream(str);
        int available = fileInputStream.available();
        byte[] bArr = new byte[available];
        int read = fileInputStream.read(bArr);
        fileInputStream.close();
        if (read == available) {
            return new String(bArr);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("readFile realSize is not equal to size: ");
        stringBuilder.append(read);
        stringBuilder.append(" : ");
        stringBuilder.append(available);
        throw new IOException(stringBuilder.toString());
    }

//    public static String readFile(String filePath) throws IOException {
//        Log.e("TEST##", "filePath==" + filePath);
//        AssetManager assetManager = context.getAssets();
//        InputStream inputStream = assetManager.open(filePath);
//        int available = inputStream.available();
//        byte[] buffer = new byte[available];
//        int bytesRead = inputStream.read(buffer);
//        inputStream.close();
//
//        if (bytesRead == available) {
//            return new String(buffer);
//        } else {
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("readFile realSize is not equal to size: ");
//            stringBuilder.append(bytesRead);
//            stringBuilder.append(" : ");
//            stringBuilder.append(available);
//            throw new IOException(stringBuilder.toString());
//        }
//    }

    public static String readFileIfExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            int size = fis.available();
            byte[] buffer = new byte[size];
            int bytesRead = fis.read(buffer);

            if (bytesRead != size) {
                throw new IOException("readFile realSize is not equal to size: " + bytesRead + " : " + size);
            }

            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
