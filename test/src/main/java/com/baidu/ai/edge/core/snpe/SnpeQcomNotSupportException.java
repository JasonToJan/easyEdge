// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.snpe;

import com.baidu.ai.edge.core.base.CallException;

public class SnpeQcomNotSupportException extends CallException
{

	public SnpeQcomNotSupportException(int i, String s, Throwable throwable)
	{
		super(i, s, throwable);
	}

	public SnpeQcomNotSupportException(int i, String s)
	{
		super(i, s);
	}
}