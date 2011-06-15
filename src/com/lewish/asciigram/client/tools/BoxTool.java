//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client.tools;

import com.google.inject.Inject;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Drag;

public class BoxTool extends DragTool {

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

		canvas.draw(x1, y1,"+");
		canvas.draw(x1, y2,"+");
		canvas.draw(x2, y1,"+");
		canvas.draw(x2, y2,"+");
		for (int x = x1 + 1; x < x2; x++) {
			canvas.draw(x, y1,"-");
			canvas.draw(x, y2,"-");
		}
		for (int y = y1 + 1; y < y2; y++) {
			canvas.draw(x1, y,"|");
			canvas.draw(x2, y,"|");
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
