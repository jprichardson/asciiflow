package com.lewish.asciiflow.client.tools;

import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.Drag;
import com.lewish.asciiflow.client.HistoryManager;

public class BoxTool extends BaseBoxTool {

	@Inject
	public BoxTool(Canvas canvas, HistoryManager historyManager) {
		super(canvas, historyManager);
	}

	@Override
	public String getLabel() {
		return "Box";
	}

	@Override
	public void draw(Drag box, Canvas canvas) {
		draw(box, canvas, false);
	}

	@Override
	public String getDescription() {
		return "Click and drag to draw a box";
	}

	@Override
	public String getImageResource() {
		return "images/boxtool.png";
	}
}
