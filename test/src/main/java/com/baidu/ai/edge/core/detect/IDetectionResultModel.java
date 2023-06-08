// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.detect;

import android.graphics.Rect;
import com.baidu.ai.edge.core.base.IBaseResultModel;

public interface IDetectionResultModel
	extends IBaseResultModel
{

	public abstract void setBounds(Rect rect);

	public abstract Rect getBounds();
}
