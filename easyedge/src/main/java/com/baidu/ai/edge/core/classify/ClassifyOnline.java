package com.baidu.ai.edge.core.classify;

import android.content.Context;
import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import com.baidu.ai.edge.core.base.a;
import com.baidu.ai.edge.core.base.e;
import java.util.List;

@Deprecated
public class ClassifyOnline extends a implements ClassifyInterface {
    public ClassifyOnline(String str, String str2, String str3, Context context) {
        super(str, str2, str3);
    }

    public List<ClassificationResultModel> classify(Bitmap bitmap) throws BaseException {
        return null;
    }

    public List<ClassificationResultModel> classify(Bitmap bitmap, float f) throws BaseException {
        return null;
    }

    public e classifyPro(Bitmap bitmap) throws BaseException {
        return null;
    }

    public void destroy() throws BaseException {
    }
}
