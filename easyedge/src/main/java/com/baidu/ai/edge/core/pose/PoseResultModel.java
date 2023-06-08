//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.pose;

import android.graphics.Point;
import com.baidu.ai.edge.core.base.BaseResultModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PoseResultModel extends BaseResultModel implements IPoseResultModel {
	private int d;
	private int e;
	private boolean f = false;
	private Point g;
	private List<IPoseResultModel> h;

	public PoseResultModel() {
	}

	public void setPairs(List<PoseResultModel> var1) {
		this.h = new ArrayList();
		this.h.addAll(var1);
	}

	public List<IPoseResultModel> getPairs() {
		return this.h;
	}

	public void setPoint(Point var1) {
		this.g = var1;
	}

	public Point getPoint() {
		return this.g;
	}

	public List<Point> getPoints() {
		ArrayList var1;
		ArrayList var10001 = var1 = new ArrayList();
		var10001.add(this.g);
		Iterator var2 = this.getPairs().iterator();

		while(var2.hasNext()) {
			var1.add(((IPoseResultModel)var2.next()).getPoint());
		}

		return var1;
	}

	public int getIndex() {
		return this.d;
	}

	public void setIndex(int var1) {
		this.d = var1;
	}

	public int getGroupIndex() {
		return this.e;
	}

	public void setGroupIndex(int var1) {
		this.e = var1;
	}

	public boolean hasGroups() {
		return this.f;
	}

	public void setHasGroups(boolean var1) {
		this.f = var1;
	}
}
