//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.base;

import java.util.List;

public class e implements IStatisticsResultModel {
	private List<? extends IBaseResultModel> a;
	private long b = 0L;
	private long c = 0L;
	private long d = 0L;

	public e() {
	}

	public long getPostprocessTime() {
		return this.d;
	}

	public void setPostprocessTime(long var1) {
		this.d = var1;
	}

	public long getPreprocessTime() {
		return this.b;
	}

	public void setPreprocessTime(long var1) {
		this.b = var1;
	}

	public long getForwardTime() {
		return this.c;
	}

	public void setForwardTime(long var1) {
		this.c = var1;
	}

	public List<? extends IBaseResultModel> getResultModel() {
		return this.a;
	}

	public void setResultModel(List<? extends IBaseResultModel> var1) {
		this.a = var1;
	}
}
