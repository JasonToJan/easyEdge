// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.ddk;

import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseConfig;
import com.baidu.ai.edge.core.base.CallException;

public class DDKDaVinciConfig extends BaseConfig
{

	private boolean w;

	public DDKDaVinciConfig(AssetManager assetmanager, String s)
		throws CallException
	{
		super(assetmanager, s);
		w = true;
		if (!super.m) goto _L2; else goto _L1
_L1:
		this;
		JVM INSTR new #22  <Class StringBuilder>;
		JVM INSTR dup ;
		s;
		JVM INSTR swap ;
		StringBuilder();
		append();
		"/params.enc";
		append();
		toString();
		a;
		  goto _L3
_L2:
		this;
		JVM INSTR new #22  <Class StringBuilder>;
		JVM INSTR dup ;
		s;
		JVM INSTR swap ;
		StringBuilder();
		append();
		"/params";
		append();
		toString();
		a;
_L3:
	}

	public boolean isAutoCheckNpu()
	{
		return w;
	}

	public void setAutoCheckNpu(boolean flag)
	{
		w = flag;
	}

	public boolean isSupportDavinciNpu()
		throws CallException
	{
		if (w && !contains("kirin810") && !contains("kirin990") && !contains("kirin820") && !contains("kirin985")) goto _L2; else goto _L1
_L2:
		(new StringBuilder()).append("Your device does NOT support Davinci:").append(this).toString();
		"DDKDaVinciConfig";
		JVM INSTR swap ;
		Log.e();
		JVM INSTR pop ;
		JVM INSTR new #13  <Class CallException>;
		JVM INSTR dup ;
		(new StringBuilder()).append("Your device does NOT support Davinci: ").append(this).toString();
		7001;
		JVM INSTR swap ;
		CallException();
		throw ;
_L1:
		return true;
	}

	public String getSoc()
	{
		return "npu-vinci";
	}
}
