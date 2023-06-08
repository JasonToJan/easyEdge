// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.pose;

import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.e;
import java.util.List;

public interface PoseInterface
{

	public static final int POSE_TYPE = 402;

	public abstract List pose(Bitmap bitmap)
		throws BaseException;

	public abstract e posePro(Bitmap bitmap)
		throws BaseException;

	public abstract void destroy()
		throws BaseException;
}
