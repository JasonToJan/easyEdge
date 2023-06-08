// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.pose;

import android.graphics.Point;
import com.baidu.ai.edge.core.base.IBaseResultModel;
import java.util.List;

public interface IPoseResultModel
	extends IBaseResultModel
{

	public abstract List getPairs();

	public abstract int getIndex();

	public abstract Point getPoint();
}
