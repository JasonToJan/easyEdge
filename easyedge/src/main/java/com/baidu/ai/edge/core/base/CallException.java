// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.base;


// Referenced classes of package com.baidu.ai.edge.core.base:
//			BaseException

public class CallException extends BaseException
{

	public CallException(int i, String s, Throwable throwable)
	{
		super(i, s, throwable);
	}

	public CallException(int i, String s)
	{
		super(i, s);
	}
}
