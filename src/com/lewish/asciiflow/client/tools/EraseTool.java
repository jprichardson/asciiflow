package com.lewish.asciiflow.client.tools;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.common.Box;
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

	protected void draw(Box box) {
		draw(box, canvas);
	}

	public static void draw(Box box, Canvas canvas) {
		for (int x = box.topLeftX(); x <= box.bottomRightX(); x++) {
			for (int y = box.topLeftY(); y <= box.bottomRightY(); y++) {
					canvas.draw(x, y, " ");
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

	public static void draw(Canvas canvas) {
		Box box = new Box(0,0);
		box.setFinish(canvas.getWidth()-1, canvas.getHeight()-1);
		draw(box, canvas);
	}
}
