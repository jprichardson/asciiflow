//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client.tools;

import com.google.inject.Inject;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Drag;

public class BoxTool extends DragTool implements Tool {

	@Inject
	public BoxTool(Canvas canvas) {
		super(canvas);
	}

	@Override
	public String getLabel() {
		return "Box";
	}

	@Override
	public void draw(Drag box, Canvas canvas) {
		int x1 = box.topLeftX();
		int y1 = box.topLeftY();
		int x2 = box.bottomRightX();
		int y2 = box.bottomRightY();

		canvas.getCell(x1, y1).setDrawValue("+");
		canvas.getCell(x1, y2).setDrawValue("+");
		canvas.getCell(x2, y1).setDrawValue("+");
		canvas.getCell(x2, y2).setDrawValue("+");
		for (int x = x1 + 1; x < x2; x++) {
			canvas.getCell(x, y1).setDrawValue("-");
			canvas.getCell(x, y2).setDrawValue("-");
		}
		for (int y = y1 + 1; y < y2; y++) {
			canvas.getCell(x1, y).setDrawValue("|");
			canvas.getCell(x2, y).setDrawValue("|");
		}
		canvas.refreshDraw();
	}

	@Override
	public String getDescription() {
		return "Click and drag to draw a box";
	}

	@Override
	public String getImageUrl() {
		return "images/boxtool.png";
	}
}
