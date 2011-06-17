package com.lewish.asciiflow.client.tools;

import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;

public class ArrowTool extends BaseLineTool {

	@Inject
	public ArrowTool(Canvas canvas, HistoryManager historyManager) {
		super(canvas, historyManager, false);
	}
}
