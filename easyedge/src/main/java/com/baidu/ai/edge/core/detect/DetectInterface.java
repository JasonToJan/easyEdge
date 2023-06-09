package com.baidu.ai.edge.core.detect;

import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.IStatisticsResultModel;
import java.util.List;

public interface DetectInterface {
    public static final int DETECT_TYPE = 2;

    void destroy() throws BaseException;

    List<DetectionResultModel> detect(Bitmap bitmap) throws BaseException;

    List<DetectionResultModel> detect(Bitmap bitmap, float f) throws BaseException;

    IStatisticsResultModel detectPro(Bitmap bitmap) throws BaseException;
}
