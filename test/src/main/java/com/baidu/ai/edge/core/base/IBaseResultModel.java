// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.base;


public interface IBaseResultModel
{

	public abstract int getLabelIndex();

	public abstract void setLabelIndex(int i);

	public abstract String getLabel();

	public abstract void setLabel(String s);

	public abstract float getConfidence();

	public abstract void setConfidence(float f);
}
