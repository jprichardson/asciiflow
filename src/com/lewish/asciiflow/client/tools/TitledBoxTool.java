//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client.tools;

import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.Drag;
import com.lewish.asciiflow.client.HistoryManager;

public class TitledBoxTool extends BaseBoxTool {

	@Inject
	public TitledBoxTool(Canvas canvas, HistoryManager historyManager) {
		super(canvas, historyManager);
	}

	@Override
	public String getLabel() {
		return "Box";
	}

	@Override
	public void draw(Drag box, Canvas canvas) {
		draw(box, canvas, true);
	}

	@Override
	public String getDescription() {
		return "Click and drag to draw a box with header";
	}

	@Override
	public String getImageResource() {
		return "images/boxtool2.png";
	}
}
