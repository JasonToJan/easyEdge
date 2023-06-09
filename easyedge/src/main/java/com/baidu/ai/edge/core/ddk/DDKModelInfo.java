package com.baidu.ai.edge.core.ddk;

public class DDKModelInfo {
    private boolean a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;

    public DDKModelInfo() {
        this.b = 1;
        this.c = 3;
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

    public DDKModelInfo(int i, int i2, int i3) {
        this.b = 1;
        this.c = 3;
        this.d = i;
        this.e = i2;
        this.f = 1;
        this.g = 1;
        this.h = i3;
        this.i = 1;
        this.j = 1;
        this.k = 1;
        this.a = false;
    }

    public int getInput_C() {
        return this.c;
    }

    public int getInput_H() {
        return this.d;
    }

    public int getInput_N() {
        return this.b;
    }

    public int getInput_Number() {
        return this.f;
    }

    public int getInput_W() {
        return this.e;
    }

    public int getOutput_C() {
        return this.h;
    }

    public int getOutput_H() {
        return this.i;
    }

    public int getOutput_N() {
        return this.g;
    }

    public int getOutput_Number() {
        return this.k;
    }

    public int getOutput_W() {
        return this.j;
    }

    public boolean isAddSoftmaxFlag() {
        return this.a;
    }

    public void setAddSoftmaxFlag(boolean z) {
        this.a = z;
    }

    public void setInput_C(int i) {
        this.c = i;
    }

    public void setInput_H(int i) {
        this.d = i;
    }

    public void setInput_N(int i) {
        this.b = i;
    }

    public void setInput_Number(int i) {
        this.f = i;
    }

    public void setInput_W(int i) {
        this.e = i;
    }

    public void setOutput_C(int i) {
        this.h = i;
    }

    public void setOutput_H(int i) {
        this.i = i;
    }

    public void setOutput_N(int i) {
        this.g = i;
    }

    public void setOutput_Number(int i) {
        this.k = i;
    }

    public void setOutput_W(int i) {
        this.j = i;
    }
}
