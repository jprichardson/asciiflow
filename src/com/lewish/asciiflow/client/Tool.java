package com.lewish.asciiflow.client;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.resources.client.ImageResource;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

/**
 * Base class for all Tools available.
 * 
 * @author lewis
 */
public abstract class Tool {

	protected final Canvas canvas;
	protected final HistoryManager historyManager;
	protected final AsciiflowClientBundle clientBundle;

	public Tool(Canvas canvas, HistoryManager historyManager, AsciiflowClientBundle clientBundle) {
		this.canvas = canvas;
		this.historyManager = historyManager;
		this.clientBundle = clientBundle;
	}

	/**
	 * Commits the drawing to the canvas as well as storing it in the history manager.
	 */
	protected void commitDraw() {
		historyManager.save(canvas.commitDraw());
	}

	protected void refreshDraw() {
		canvas.refreshDraw();
	}

	public abstract String getLabel();

	public abstract String getDescription();

	public abstract ImageResource getImageResource();

	public void mouseOver(int x, int y) {
	}

	public void mouseDown(int x, int y) {
	}

	public void mouseUp(int x, int y) {
	}

	public void cleanup() {
	}

	public void keyPress(KeyPressEvent event) {
	}

	public void specialKeyPress(int keyCode) {
	}
}
