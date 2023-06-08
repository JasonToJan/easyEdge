// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.segment;

import android.graphics.Rect;
import com.baidu.ai.edge.core.base.IBaseResultModel;

public interface ISegmentationResultModel
	extends IBaseResultModel
{

	public abstract void setBox(Rect rect);

	public abstract byte[] getMask();

	public abstract String getMaskLEcode();

	public abstract void setMask(byte abyte0[]);

	public abstract Rect getBox();
}
