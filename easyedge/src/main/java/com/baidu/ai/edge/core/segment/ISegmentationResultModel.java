package com.baidu.ai.edge.core.segment;

import android.graphics.Rect;
import com.baidu.ai.edge.core.base.IBaseResultModel;

public interface ISegmentationResultModel extends IBaseResultModel {
    Rect getBox();

    byte[] getMask();

    String getMaskLEcode();

    void setBox(Rect rect);

    void setMask(byte[] bArr);
}
