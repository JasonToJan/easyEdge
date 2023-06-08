//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.baidu.ai.edge.core.ocr;

import android.graphics.Point;
import com.baidu.ai.edge.core.base.BaseResultModel;
import java.util.ArrayList;
import java.util.List;

public class OcrResultModel extends BaseResultModel {
	private List<Point> d = new ArrayList();
	private List<Integer> e = new ArrayList();

	public OcrResultModel() {
	}

	public void addPoints(int var1, int var2) {
		OcrResultModel var10000 = this;
		Point var3;
		var3 = new Point(var1, var2);
		var10000.d.add(var3);
	}

	public void addWordIndex(int var1) {
		this.e.add(var1);
	}

	public List<Point> getPoints() {
		return this.d;
	}

	public List<Integer> getWordIndex() {
		return this.e;
	}
}
