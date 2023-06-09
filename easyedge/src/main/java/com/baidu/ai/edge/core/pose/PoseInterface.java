package com.baidu.ai.edge.core.pose;

import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.e;
import java.util.List;

public interface PoseInterface {
    public static final int POSE_TYPE = 402;

    void destroy() throws BaseException;

    List<PoseResultModel> pose(Bitmap bitmap) throws BaseException;

    e posePro(Bitmap bitmap) throws BaseException;
}
