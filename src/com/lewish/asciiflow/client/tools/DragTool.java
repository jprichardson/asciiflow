package com.lewish.asciiflow.client.tools;

import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.Tool;
import com.lewish.asciiflow.client.common.Box;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

public abstract class DragTool extends Tool {

	public DragTool(Canvas canvas, HistoryManager historyManager, AsciiflowClientBundle clientBundle) {
		super(canvas, historyManager, clientBundle);
	}

	protected Box currentBox;

	@Override
	public void mouseOver(int x, int y) {
		if (currentBox != null) {
			currentBox.setFinish(x, y);
			draw(currentBox);
			refreshDraw();
		}
	}

	@Override
	public void mouseDown(int x, int y) {
		if (currentBox == null) {
			currentBox = new Box(x, y);
			draw(currentBox);
			refreshDraw();
		}
	}

	@Override
	public void mouseUp(int x, int y) {
		if (currentBox != null) {
			currentBox.setFinish(x, y);
			currentBox = null;
			commitDraw();
		}
	}

	@Override
	public void cleanup() {
		if (currentBox != null) {
			canvas.clearDraw();
			currentBox = null;
		}
	}

	protected abstract void draw(Box box);
}
