//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.util;

public class TimerRecorder {
	private static long a;

	public TimerRecorder() {
	}

	public static synchronized void start() {
		a = System.currentTimeMillis();
	}

	public static synchronized long end() {
		return System.currentTimeMillis() - a;
	}
}
