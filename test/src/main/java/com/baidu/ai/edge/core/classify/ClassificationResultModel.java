// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.classify;

import com.baidu.ai.edge.core.base.BaseResultModel;

// Referenced classes of package com.baidu.ai.edge.core.classify:
//			IClassificationResultModel

public class ClassificationResultModel extends BaseResultModel
	implements IClassificationResultModel
{

	public ClassificationResultModel(String s, float f)
	{
		super(s, f);
	}

	public ClassificationResultModel(int i, float f)
	{
		super("not loaded", f);
		super.a = i;
	}
}
