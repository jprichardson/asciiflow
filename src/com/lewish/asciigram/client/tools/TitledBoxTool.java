//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client.tools;

import com.google.inject.Inject;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Drag;
import com.lewish.asciigram.client.HistoryManager;

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
	public String getImageUrl() {
		return "images/boxtool2.png";
	}
}
