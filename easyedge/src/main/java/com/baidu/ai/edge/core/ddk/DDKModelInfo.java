//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.ddk;

public class DDKModelInfo {
	private boolean a;
	private int b = 1;
	private int c = 3;
	private int d;
	private int e;
	private int f;
	private int g;
	private int h;
	private int i;
	private int j;
	private int k;

	public DDKModelInfo() {
		this.d = 0;
		this.e = 0;
		this.f = 1;
		this.g = 1;
		this.h = 0;
		this.i = 1;
		this.j = 1;
		this.k = 1;
		this.a = false;
	}

	public DDKModelInfo(int var1, int var2, int var3) {
		this.d = var1;
		this.e = var2;
		this.f = 1;
		this.g = 1;
		this.h = var3;
		this.i = 1;
		this.j = 1;
		this.k = 1;
		this.a = false;
	}

	public boolean isAddSoftmaxFlag() {
		return this.a;
	}

	public void setAddSoftmaxFlag(boolean var1) {
		this.a = var1;
	}

	public int getInput_N() {
		return this.b;
	}

	public void setInput_N(int var1) {
		this.b = var1;
	}

	public int getInput_C() {
		return this.c;
	}

	public void setInput_C(int var1) {
		this.c = var1;
	}

	public int getInput_H() {
		return this.d;
	}

	public void setInput_H(int var1) {
		this.d = var1;
	}

	public int getInput_W() {
		return this.e;
	}

	public void setInput_W(int var1) {
		this.e = var1;
	}

	public int getInput_Number() {
		return this.f;
	}

	public void setInput_Number(int var1) {
		this.f = var1;
	}

	public int getOutput_N() {
		return this.g;
	}

	public void setOutput_N(int var1) {
		this.g = var1;
	}

	public int getOutput_C() {
		return this.h;
	}

	public void setOutput_C(int var1) {
		this.h = var1;
	}

	public int getOutput_H() {
		return this.i;
	}

	public void setOutput_H(int var1) {
		this.i = var1;
	}

	public int getOutput_W() {
		return this.j;
	}

	public void setOutput_W(int var1) {
		this.j = var1;
	}

	public int getOutput_Number() {
		return this.k;
	}

	public void setOutput_Number(int var1) {
		this.k = var1;
	}
}
