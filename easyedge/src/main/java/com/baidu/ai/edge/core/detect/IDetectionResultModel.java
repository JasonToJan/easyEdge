package com.baidu.ai.edge.core.detect;

import android.graphics.Rect;
import com.baidu.ai.edge.core.base.IBaseResultModel;

public interface IDetectionResultModel extends IBaseResultModel {
    Rect getBounds();

    void setBounds(Rect rect);
}
