package com.lewish.asciiflow.client.tools;

import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.common.Box;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

public class DottedLineTool extends BaseLineTool {

	@Inject
	public DottedLineTool(Canvas canvas, HistoryManager historyManager, AsciiflowClientBundle clientBundle) {
		super(canvas, historyManager, clientBundle);
	}

	@Override
	protected void draw(Box box) {
		draw(box, canvas, isClockwise, false, ".", ".", ".");
	}
}
