// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.detect;

import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.IStatisticsResultModel;
import java.util.List;

public interface DetectInterface
{

	public static final int DETECT_TYPE = 2;

	public abstract List detect(Bitmap bitmap, float f)
		throws BaseException;

	public abstract List detect(Bitmap bitmap)
		throws BaseException;

	public abstract IStatisticsResultModel detectPro(Bitmap bitmap)
		throws BaseException;

	public abstract void destroy()
		throws BaseException;
}
