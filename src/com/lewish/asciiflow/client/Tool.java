//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client;

import com.google.gwt.event.dom.client.KeyPressEvent;

public abstract class Tool {

	protected final Canvas canvas;
	protected final HistoryManager historyManager;

	public Tool(Canvas canvas, HistoryManager historyManager) {
		this.canvas = canvas;
		this.historyManager = historyManager;
	}

	protected void commitDraw() {
		historyManager.save(canvas.commitDraw());
	}

	protected void refreshDraw() {
		canvas.refreshDraw();
	}

	public abstract String getLabel();

	public abstract String getDescription();

	public abstract String getImageUrl();

	public void mouseOver(Cell cell) {
	}

	public void mouseDown(Cell cell) {
	}

	public void mouseUp(Cell cell) {
	}

	public void cleanup() {
	}

	public void keyPress(KeyPressEvent event) {
	}

	public void specialKeyPress(int keyCode) {
	}
}
