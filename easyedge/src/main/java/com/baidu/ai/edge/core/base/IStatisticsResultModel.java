// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.base;

import java.util.List;

public interface IStatisticsResultModel
{

	public abstract long getPostprocessTime();

	public abstract void setPostprocessTime(long l);

	public abstract long getPreprocessTime();

	public abstract void setPreprocessTime(long l);

	public abstract long getForwardTime();

	public abstract void setForwardTime(long l);

	public abstract List getResultModel();

	public abstract void setResultModel(List<? extends IBaseResultModel> list);
}
