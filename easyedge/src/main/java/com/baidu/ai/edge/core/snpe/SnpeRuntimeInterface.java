package com.baidu.ai.edge.core.snpe;

public interface SnpeRuntimeInterface {
    public static final int CPU = 0;
    public static final int DSP = 2;
    public static final int GPU = 1;
    public static final int GPU_FLOAT16 = 3;
    public static final int[] RUNTIMES = new int[]{0, 1, 2, 3};
}
