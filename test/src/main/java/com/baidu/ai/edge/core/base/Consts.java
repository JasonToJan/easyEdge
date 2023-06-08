// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   

package com.baidu.ai.edge.core.base;


public class Consts
{

	public static final String AUTH_DOMAIN_DEF = "https://verify.baidubce.com";
	public static final String SOC_ARM = "arm";
	public static final String SOC_NPU = "npu";
	public static final String SOC_NPU_VINCI = "npu-vinci";
	public static final String SOC_DSP = "dsp";
	public static final String SOC_ADRENO_GPU = "adreno-gpu";
	public static final String SOC_ARM_GPU = "arm-gpu";
	public static final String ASSETS_DIR_ARM = "infer";
	public static final int TYPE_INFER = 0;
	public static final int TYPE_DDK200 = 11;
	public static final int TYPE_SNPE = 2;
	public static final int TYPE_ARM_GPU = 3;
	public static final int TYPE_SNPE_GPU = 4;
	public static final int TYPE_DDK_DAVINCI = 10;
	public static final int MODEL_TYPE_OCR = 100;
	public static final int MODEL_TYPE_SEG = 6;
	public static final int MODEL_TYPE_CLASSIFY = 1;
	public static final int MODEL_TYPE_DETECT = 2;
	public static final String PROD_PRO = "EasyDL-Pro";
	public static final String PROD_EASYDL = "EasyDL";
	public static final String PROD_EASYEDGE_FREE = "EasyEdge-Free";
	public static final String PROD_BML_FREE = "BML-Free";
	public static final String AUTH_MODE_DEVICE = "device";
	public static final String AUTH_MODE_INSTANCE = "instance";
	public static final boolean AUTH_REQUIRE_SDCARD = true;
	public static final int NTYPE_FRCNN = 102;
	public static final int NTYPE_MRCNN_R50_VD_FPN = 2010;
	public static final long AUTH_DEF_INTERVAL = 3600L;
	public static final int EC_BASE_INTERNAL_ERROR = 1900;
	public static final int EC_BASE_CONFIG_READ_ASSETS_FILE = 1001;
	public static final int EC_BASE_CONFIG_PARSE_JSON = 1002;
	public static final int EC_BASE_NO_PERMISSION = 1003;
	public static final int EC_BASE_MANAGER_MULTI_INSTANCES = 2001;
	public static final int EC_BASE_MANAGER_HAS_DETORYED = 2002;
	public static final int EC_BASE_CONFIG_CLASS = 2003;
	public static final int EC_BASE_JNI_SO_NOT_LOADED = 2011;
	public static final int EC_BASE_JNI_OUT_OF_MEMORY = 2012;
	public static final int EC_BASE_JNI_NOT_ACTIVATED = 2013;
	public static final int EC_BASE_JNI_MODELFILE_READERROR = 2701;
	public static final int EC_BASE_DETECT_MANAGER_ASSET_MODEL_NULL = 2702;
	public static final int EC_BASE_JNI_UNKNOWN = 2901;
	public static final int EC_LOAD_NATIVE_SO_LIB_FAIL = 3000;
	public static final int EC_LOAD_DDK_MODEL_FAIL = 3001;
	public static final int EC_UNLOAD_DDK_MODEL_FAIL = 3002;
	public static final int EC_LOAD_DAVINCI_MODEL_FAILED = 3003;
	public static final int EC_SNPE_NOT_QCOM = 4011;
	public static final int EC_SNPE_RUNTIME_ALLOWED_NONE = 4012;
	public static final int EC_ONLINE_AUTH_ERROR = 5000;
	public static final int EC_ONLINE_NETWORK_ERROR = 5001;
	public static final int EC_ONLINE_RESPONE_ERROR = 5002;
	public static final int EC_OFFLINE_AUTH_ERROR = 5003;
	public static final int EC_OFFLINE_SERIAL_NULL_ERROR = 5004;
	public static final int EC_CHECK_NPU_FAIL = 7001;
	public static final int EC_BASE_INFER_ANDROID_VERSION_NOT_SUPPORT = 8001;

	public Consts()
	{
	}
}
