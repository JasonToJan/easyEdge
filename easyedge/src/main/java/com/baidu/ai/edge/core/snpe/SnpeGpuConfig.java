// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.snpe;

import android.content.res.AssetManager;
import com.baidu.ai.edge.core.base.CallException;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.baidu.ai.edge.core.snpe:
//			SnpeConfig

public class SnpeGpuConfig extends SnpeConfig
{

	public SnpeGpuConfig(AssetManager assetmanager, String s)
		throws Throwable
	{
		super(assetmanager, s);
	}

	protected void a(JSONObject jsonobject, JSONObject jsonobject1)
		throws JSONException
	{
		if (super.x == null)
			super.x = (new int[] {
				1, 3, 0
			});
		super.a(jsonobject, jsonobject1);
	}
}
