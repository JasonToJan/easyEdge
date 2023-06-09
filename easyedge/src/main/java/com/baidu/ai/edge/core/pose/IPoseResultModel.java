package com.baidu.ai.edge.core.pose;

import android.graphics.Point;
import com.baidu.ai.edge.core.base.IBaseResultModel;
import java.util.List;

public interface IPoseResultModel extends IBaseResultModel {
    int getIndex();

    List<IPoseResultModel> getPairs();

    Point getPoint();
}
