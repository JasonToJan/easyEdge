// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.segment;

import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.e;
import java.util.List;

public interface SegmentInterface
{

	public static final int SEGEMENT_TYPE = 6;

	public abstract List segment(Bitmap bitmap)
		throws BaseException;

	public abstract List segment(Bitmap bitmap, float f)
		throws BaseException;

	public abstract e segmentPro(Bitmap bitmap)
		throws BaseException;

	public abstract void destroy()
		throws BaseException;
}
