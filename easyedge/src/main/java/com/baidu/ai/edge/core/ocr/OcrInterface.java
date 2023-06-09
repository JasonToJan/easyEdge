package com.baidu.ai.edge.core.ocr;

import android.graphics.Bitmap;
import com.baidu.ai.edge.core.base.BaseException;
import java.util.List;

public interface OcrInterface {
    public static final int OCR_TYPE = 100;

    void destroy() throws BaseException;

    List<OcrResultModel> ocr(Bitmap bitmap) throws BaseException;

    List<OcrResultModel> ocr(Bitmap bitmap, float f) throws BaseException;
}
