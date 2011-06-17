package com.lewish.asciiflow.client.tools;

import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;

public class LineTool extends BaseLineTool {

	@Inject
	public LineTool(Canvas canvas, HistoryManager historyManager) {
		super(canvas, historyManager, true);
	}
}
