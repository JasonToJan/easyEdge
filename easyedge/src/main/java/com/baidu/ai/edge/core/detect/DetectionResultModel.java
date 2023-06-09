package com.baidu.ai.edge.core.detect;

import android.graphics.Rect;
import android.util.Log;
import com.baidu.ai.edge.core.base.BaseResultModel;
import org.json.JSONObject;

public class DetectionResultModel extends BaseResultModel implements IDetectionResultModel {
    private Rect d;

    public DetectionResultModel() {
    }
    public DetectionResultModel(String str, float f, Rect rect) {
        super(str, f);
        this.d = rect;
    }

    public Rect getBounds() {
        return this.d;
    }

    public void setBounds(Rect rect) {
        this.d = rect;
    }

    public JSONObject toJsonObject() {
        JSONObject toJsonObject = super.toJsonObject();
        try {
            toJsonObject.put("bounds_left", this.d.left);
            toJsonObject.put("bounds_top", this.d.top);
            toJsonObject.put("bounds_right", this.d.right);
            toJsonObject.put("bounds_bottom", this.d.bottom);
        } catch (Throwable e) {
            Log.e("DetectionResultModel", "json serialize error", e);
        }
        return toJsonObject;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.b);
        String str = "][";
        stringBuilder.append(str);
        stringBuilder.append(this.c);
        stringBuilder.append(str);
        stringBuilder.append(this.d.toString());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
