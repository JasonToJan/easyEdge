package com.baidu.ai.edge.core.detect;

import android.content.Context;
import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.IStatisticsResultModel;
import com.baidu.ai.edge.core.base.a;
import java.util.List;

@Deprecated
public class DetectOnline extends a implements DetectInterface {
    public DetectOnline(String str, String str2, String str3, Context context) {
        super(str, str2, str3);
    }

    public void destroy() throws BaseException {
    }

    public List<DetectionResultModel> detect(Bitmap bitmap) throws BaseException {
        return null;
    }

    public List<DetectionResultModel> detect(Bitmap bitmap, float f) throws BaseException {
        return null;
    }

    public IStatisticsResultModel detectPro(Bitmap bitmap) throws BaseException {
        return null;
    }
}
