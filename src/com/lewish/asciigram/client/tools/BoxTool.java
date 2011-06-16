package com.lewish.asciigram.client.tools;

import com.google.inject.Inject;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Drag;
import com.lewish.asciigram.client.HistoryManager;

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
	public String getImageUrl() {
		return "images/boxtool.png";
	}
}
