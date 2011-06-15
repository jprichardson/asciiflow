//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client.tools;

import com.google.inject.Inject;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Drag;

public class EraseTool extends DragTool {

	@Inject
	public EraseTool(Canvas canvas) {
		super(canvas);
	}

	@Override
	public String getLabel() {
		return "Erase";
	}

	@Override
	public void draw(Drag box, Canvas canvas) {
		for (int x = box.topLeftX(); x <= box.bottomRightX(); x++) {
			for (int y = box.topLeftY(); y <= box.bottomRightY(); y++) {
				canvas.draw(x, y," ");
			}
		}
		canvas.refreshDraw();
	}

	@Override
	public String getDescription() {
		return "Click and drag to erase cells";
	}

	@Override
	public String getImageUrl() {
		return "images/erasetool.png";
	}
}
