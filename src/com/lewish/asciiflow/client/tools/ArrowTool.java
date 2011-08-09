package com.lewish.asciiflow.client.tools;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.common.Box;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

public class ArrowTool extends BaseLineTool {

	@Inject
	public ArrowTool(Canvas canvas, HistoryManager historyManager,
			AsciiflowClientBundle clientBundle) {
		super(canvas, historyManager, clientBundle);
	}

	@Override
	protected void draw(Box box) {
		draw(box, canvas, isClockwise, true, "-", "|", "+");
	}

	@Override
	public ImageResource getImageResource() {
		return clientBundle.arrowToolImage();
	}
}
