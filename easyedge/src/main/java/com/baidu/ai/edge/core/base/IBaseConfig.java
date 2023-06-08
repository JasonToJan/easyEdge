// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.base;


public interface IBaseConfig
{

	public abstract int getMid();

	public abstract int getRid();

	public abstract String getProduct();

	public abstract String getSoc();

	public abstract String getModelFileAssetPath();

	public abstract void setModelFileAssetPath(String s);

	public abstract boolean isAcceleration();

	public abstract String getUserDeviceId();

	public abstract String getAuthMode();

	public abstract long getAuthInterval();

	public abstract String getAuthDomain();

	public abstract String getDeviceLicenseUri();
}
