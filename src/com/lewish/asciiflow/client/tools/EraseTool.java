//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client.tools;

import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.Drag;
import com.lewish.asciiflow.client.HistoryManager;

public class EraseTool extends DragTool {

	@Inject
	public EraseTool(Canvas canvas, HistoryManager historyManager) {
		super(canvas, historyManager);
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
