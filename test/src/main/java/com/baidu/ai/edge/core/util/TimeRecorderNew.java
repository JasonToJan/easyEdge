//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.util;

public class TimeRecorderNew {
	private long a;

	public TimeRecorderNew() {
		this.restart();
	}

	public void restart() {
		this.a = System.currentTimeMillis();
	}

	public long checkpoint() {
		return this.checkpoint(System.currentTimeMillis());
	}

	public long checkpoint(long var1) {
		long var10000 = var1 - this.a;
		this.a = var1;
		return var10000;
	}

	public long end() {
		return System.currentTimeMillis() - this.a;
	}
}
