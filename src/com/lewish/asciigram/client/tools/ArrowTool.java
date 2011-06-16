package com.lewish.asciigram.client.tools;

import com.google.inject.Inject;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.HistoryManager;

public class ArrowTool extends BaseLineTool {

	@Inject
	public ArrowTool(Canvas canvas, HistoryManager historyManager) {
		super(canvas, historyManager, false);
	}
}
