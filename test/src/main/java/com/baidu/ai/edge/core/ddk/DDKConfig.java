// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.ddk;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.*;
import com.baidu.ai.edge.core.util.FileUtil;
import java.io.IOException;
import org.json.*;

public class DDKConfig extends BaseConfig
{

	public static final String SDK_CONFIG_PATH = "sdk-config.json";

	public DDKConfig(AssetManager assetmanager, String s)
		throws CallException
	{
		s;
		super(assetmanager, s);
		"file:///";
		startsWith();
		JVM INSTR ifeq 52;
		   goto _L1 _L2
_L1:
		JVM INSTR new #27  <Class StringBuilder>;
		JVM INSTR dup ;
		s;
		JVM INSTR swap ;
		StringBuilder();
		7;
		substring();
		append();
		"/";
		append();
		"sdk-config.json";
		append();
		toString();
		FileUtil.readFile();
_L4:
		assetmanager;
		  goto _L3
_L2:
		assetmanager;
		JVM INSTR new #27  <Class StringBuilder>;
		JVM INSTR dup ;
		s;
		JVM INSTR swap ;
		StringBuilder();
		append();
		"/";
		append();
		"sdk-config.json";
		append();
		toString();
		FileUtil.readAssetFileUtf8String();
		  goto _L4
_L3:
		JSONArray jsonarray = JVM INSTR new #58  <Class JSONArray>;
		assetmanager;
		JSONArray();
		assetmanager = 0;
_L11:
		if (assetmanager >= jsonarray.length()) goto _L6; else goto _L5
_L5:
		Object obj = jsonarray.getJSONObject(assetmanager);
		"id";
		has();
		JVM INSTR ifeq 195;
		   goto _L7 _L8
_L7:
		break MISSING_BLOCK_LABEL_119;
_L8:
		continue; /* Loop/switch isn't completed */
		if (!((JSONObject) (obj)).getString("id").equals("ddk200"))
			continue; /* Loop/switch isn't completed */
		this;
		JVM INSTR new #27  <Class StringBuilder>;
		JVM INSTR dup ;
		s;
		JVM INSTR swap ;
		StringBuilder();
		append();
		"/";
		append();
		((JSONObject) (obj)).getString("params");
		append();
		obj;
		isEnc();
		JVM INSTR ifeq 177;
		   goto _L9 _L10
_L9:
		break MISSING_BLOCK_LABEL_170;
_L10:
		break MISSING_BLOCK_LABEL_177;
		String s1;
		s1 = ".enc";
		break MISSING_BLOCK_LABEL_181;
		s1 = "";
		super.a = ((StringBuilder) (obj)).append(s1).toString();
		assetmanager++;
		  goto _L11
_L6:
		return;
		this;
		printStackTrace();
		throw new CallException(1002, " sdk-config parse json error ", this);
		this;
		JVM INSTR new #15  <Class CallException>;
		JVM INSTR dup ;
		(new StringBuilder()).append("sdk-config read asset file error ").append(s).toString();
		1001;
		JVM INSTR swap ;
		this;
		CallException();
		throw ;
	}

	protected void a(JSONObject jsonobject, JSONObject jsonobject1)
		throws JSONException
	{
		super.a(jsonobject, jsonobject1);
		super.v.a("CHW");
	}

	public String getSoc()
	{
		return "npu";
	}
}
