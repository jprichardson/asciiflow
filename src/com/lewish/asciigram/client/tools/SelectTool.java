package com.lewish.asciigram.client.tools;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Cell;
import com.lewish.asciigram.client.Drag;

public class SelectTool implements Tool {

	public static enum State {
		Dragging,
		Nothing,
		Selected;
	}

	private final Canvas canvas;
	private State state = State.Nothing;
	private String[][] clipboard;
	private Drag currentBox;

	@Inject
	public SelectTool(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseOver(Cell cell) {
		if(state == State.Dragging) {
			currentBox.setFinish(cell);
			draw();
		}
	}

	@Override
	public void mouseDown(Cell cell) {
		if(state != State.Dragging) {
			state = State.Dragging;
			currentBox = new Drag();
			currentBox.setStart(cell);
			currentBox.setFinish(cell);
			draw();
		}
	}

	@Override
	public void mouseUp(Cell cell) {
		if(state == State.Dragging) {
			state = State.Selected;
			currentBox.setFinish(cell);
		}
	}

	@Override
	public void cleanup() {
		state = State.Nothing;
		if(currentBox != null) {
			canvas.clearDraw();
			currentBox = null;
		}
	}

	@Override
	public void keyDown(KeyDownEvent event) {
		if (event.isControlKeyDown()
				&& (event.getNativeKeyCode() == 'c' || event.getNativeKeyCode() == 'C')) {
			copy();
		} else if (event.isControlKeyDown()
				&& (event.getNativeKeyCode() == 'v' || event.getNativeKeyCode() == 'V')) {
			paste();
		}
	}

	private void copy() {
		clipboard = canvas.getState(currentBox);
	}

	private void paste() {
		if(currentBox != null && state == State.Selected) {
			canvas.loadState(currentBox, clipboard);
			canvas.commitDraw();
		}
	}

	@Override
	public void keyPress(char character) {
	}

	@Override
	public String getLabel() {
		return "+box";
	}

	public void draw() {
		for (int x = currentBox.topLeftX(); x <= currentBox.bottomRightX(); x++) {
			for (int y = currentBox.topLeftY(); y <= currentBox.bottomRightY(); y++) {
				canvas.getCell(x, y).setDrawValue(canvas.getCell(x, y).getValue());
			}
		}
		canvas.refreshDraw();
	}

	@Override
	public String getDescription() {
		return "Select, copy, and paste";
	}

	@Override
	public String getImageUrl() {
		return "images/selecttool.png";
	}

}
