// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.segment;

import android.graphics.Rect;
import com.baidu.ai.edge.core.base.BaseResultModel;

// Referenced classes of package com.baidu.ai.edge.core.segment:
//			ISegmentationResultModel

public class SegmentationResultModel extends BaseResultModel
	implements ISegmentationResultModel
{

	Rect d;
	byte e[];
	String f;

	public SegmentationResultModel(int i, float f1, int j, int k, int l, int i1)
	{
		super(i, f1);
		d = new Rect(j, k, l, i1);
	}

	public void setBox(Rect rect)
	{
		d = rect;
	}

	public String getMaskLEcode()
	{
		return f;
	}

	public void setMaskLEcode(String s)
	{
		f = s;
	}

	public byte[] getMask()
	{
		return e;
	}

	public void setMask(byte abyte0[])
	{
		e = abyte0;
	}

	public Rect getBox()
	{
		return d;
	}
}
