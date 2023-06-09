package com.baidu.ai.edge.core.pose;

import android.graphics.Point;
import com.baidu.ai.edge.core.base.BaseResultModel;
import java.util.ArrayList;
import java.util.List;

public class PoseResultModel extends BaseResultModel implements IPoseResultModel {
    private int d;
    private int e;
    private boolean f = false;
    private Point g;
    private List<IPoseResultModel> h;

    public PoseResultModel() {
    }

    public int getGroupIndex() {
        return this.e;
    }

    public int getIndex() {
        return this.d;
    }

    public List<IPoseResultModel> getPairs() {
        return this.h;
    }

    public Point getPoint() {
        return this.g;
    }

    public List<Point> getPoints() {
        List<Point> arrayList = new ArrayList();
        arrayList.add(this.g);
        for (IPoseResultModel point : getPairs()) {
            arrayList.add(point.getPoint());
        }
        return arrayList;
    }

    public boolean hasGroups() {
        return this.f;
    }

    public void setGroupIndex(int i) {
        this.e = i;
    }

    public void setHasGroups(boolean z) {
        this.f = z;
    }

    public void setIndex(int i) {
        this.d = i;
    }

    public void setPairs(List<PoseResultModel> list) {
        this.h = new ArrayList();
        this.h.addAll(list);
    }

    public void setPoint(Point point) {
        this.g = point;
    }
}
