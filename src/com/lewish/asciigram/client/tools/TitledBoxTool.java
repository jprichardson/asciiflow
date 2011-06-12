package com.lewish.asciigram.client.tools;

import com.google.inject.Inject;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Drag;

public class TitledBoxTool extends DragTool implements Tool {

	@Inject
	public TitledBoxTool(Canvas canvas) {
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

		boolean showTitle = (y2 - y1) > 2;

		canvas.getCell(x1, y1).setDrawValue("+");
		canvas.getCell(x1, y2).setDrawValue("+");
		canvas.getCell(x2, y1).setDrawValue("+");
		canvas.getCell(x2, y2).setDrawValue("+");
		for (int x = x1 + 1; x < x2; x++) {
			canvas.getCell(x, y1).setDrawValue("-");
			canvas.getCell(x, y2).setDrawValue("-");
			if (showTitle) {
				canvas.getCell(x, y1+2).setDrawValue("-");
			}
		}
		for (int y = y1 + 1; y < y2; y++) {
			canvas.getCell(x1, y).setDrawValue("|");
			canvas.getCell(x2, y).setDrawValue("|");
		}
		if (showTitle) {
			canvas.getCell(x1, y1+2).setDrawValue("+");
			canvas.getCell(x2, y1+2).setDrawValue("+");
		}
		canvas.refreshDraw();
	}

	@Override
	public String getDescription() {
		return "Click and drag to draw a box with header";
	}

	@Override
	public String getImageUrl() {
		return "images/boxtool2.png";
	}
}
