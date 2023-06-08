
// IntelliJ API Decompiler stub source generated from a class file
// Implementation of methods is not available

package com.baidu.ai.edge.core.base;

public final class ActivateManager {
	public static final int JNILIB_TYPE_ARM = 100;
	public static final int JNILIB_TYPE_SNPE = 101;
	public static final int JNILIB_TYPE_DDK = 102;
	public static final int JNILIB_TYPE_XEYE = 103;
	public static final int JNILIB_TYPE_DAVINCI = 104;
	private static final java.lang.String URL_DEVICE_ACTIVATE = "/offline-auth/v2/key/activate/edge";
	private static final java.lang.String URL_GENERATE_KEY = "/instance-auth/v1/key/generate";
	private static final java.lang.String URL_INSTANCE_ACTIVATE = "/instance-auth/v1/key/activate/edge";
	private static final java.lang.String URL_INSTANCE_DEACTIVATE = "/instance-auth/v1/key/deactivate/edge";
	private static java.lang.String domain = "https://verify.baidubce.com";
	private static java.lang.String deviceActivateUri = "/offline-auth/v2/key/activate/edge";
	private static final java.lang.String TAG = "ActivateManager";
	private static volatile java.util.concurrent.ScheduledThreadPoolExecutor instanceActivateExecutor;
	private android.content.Context context;
	private com.baidu.ai.edge.core.base.IBaseConfig config;

	public ActivateManager(android.content.Context context, com.baidu.ai.edge.core.base.IBaseConfig iBaseConfig) { }

	static byte[] generateKeyByHTTP(android.content.Context context, java.lang.String s) throws com.baidu.ai.edge.core.base.BaseException, java.io.IOException {return null; }

	static byte[] instanceActivateByHttp(android.content.Context context, java.lang.String s, java.lang.String s1) throws com.baidu.ai.edge.core.base.BaseException, java.io.IOException { return null;  }

	static byte[] deactivateInstanceByHttp(android.content.Context context, java.lang.String s, java.lang.String s1) throws com.baidu.ai.edge.core.base.BaseException, java.io.IOException { return null; }

	static byte[] deviceActivateByHttp(android.content.Context context, java.lang.String s) throws com.baidu.ai.edge.core.base.BaseException, java.io.IOException {return null;  }

	private static byte[] requestByHttp(android.content.Context context, java.lang.String s, java.lang.String s1) throws com.baidu.ai.edge.core.base.BaseException, java.io.IOException {return null;  }

	private org.json.JSONObject mergeActiveJSON(com.baidu.ai.edge.core.base.IBaseConfig iBaseConfig, java.lang.String s) { return null; }

	private org.json.JSONObject mergeKeyJSON(java.lang.String s) { return null;  }

	static java.lang.String getBase64(byte[] bytes) {return null;  }

	private java.lang.String activateInternal(java.lang.String s, int i, boolean b) throws java.io.IOException, com.baidu.ai.edge.core.base.CallException, com.baidu.ai.edge.core.base.BaseException {return null; }

	private void scheduleInstanceActivation(java.lang.String s, int i, boolean b) throws com.baidu.ai.edge.core.base.BaseException {  }

	public void terminate() { /* compiled code */ }

	public com.baidu.ai.edge.core.base.JniParam fillCommonAuthParam(java.lang.String s) {return null;  }

	public java.lang.String activate(java.lang.String s, int i, boolean b) throws java.io.IOException, com.baidu.ai.edge.core.base.CallException, com.baidu.ai.edge.core.base.BaseException { return null;  }

	private class c implements java.lang.Runnable {
		private  java.lang.String a;
		private  int b;
		private  boolean c;

		public c(java.lang.String s, int i, boolean b) { /* compiled code */ }

		public void run() { /* compiled code */ }
	}
}