package com.baidu.ai.edge.core.segment;

import android.graphics.Rect;
import com.baidu.ai.edge.core.base.BaseResultModel;

public class SegmentationResultModel extends BaseResultModel implements ISegmentationResultModel {
    Rect d;
    byte[] e;
    String f;

    public SegmentationResultModel(int i, float f, int i2, int i3, int i4, int i5) {
        super(i, f);
        this.d = new Rect(i2, i3, i4, i5);
    }

    public Rect getBox() {
        return this.d;
    }

    public byte[] getMask() {
        return this.e;
    }

    public String getMaskLEcode() {
        return this.f;
    }

    public void setBox(Rect rect) {
        this.d = rect;
    }

    public void setMask(byte[] bArr) {
        this.e = bArr;
    }

    public void setMaskLEcode(String str) {
        this.f = str;
    }
}
