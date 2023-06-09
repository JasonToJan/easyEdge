package com.baidu.ai.edge.core.segment;

import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.e;
import java.util.List;

public interface SegmentInterface {
    public static final int SEGEMENT_TYPE = 6;

    void destroy() throws BaseException;

    List<SegmentationResultModel> segment(Bitmap bitmap) throws BaseException;

    List<SegmentationResultModel> segment(Bitmap bitmap, float f) throws BaseException;

    e segmentPro(Bitmap bitmap) throws BaseException;
}
