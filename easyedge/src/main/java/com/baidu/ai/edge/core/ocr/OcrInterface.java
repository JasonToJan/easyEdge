// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.ocr;

import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import java.util.List;

public interface OcrInterface
{

	public static final int OCR_TYPE = 100;

	public abstract List ocr(Bitmap bitmap)
		throws BaseException;

	public abstract List ocr(Bitmap bitmap, float f)
		throws BaseException;

	public abstract void destroy()
		throws BaseException;
}
