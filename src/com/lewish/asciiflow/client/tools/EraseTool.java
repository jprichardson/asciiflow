//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client.tools;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.Drag;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

public class EraseTool extends DragTool {

	@Inject
	public EraseTool(Canvas canvas, HistoryManager historyManager, AsciiflowClientBundle clientBundle) {
		super(canvas, historyManager, clientBundle);
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
	public ImageResource getImageResource() {
		return clientBundle.eraseToolImage();
	}
}
