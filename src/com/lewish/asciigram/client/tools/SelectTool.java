//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client.tools;

import javax.inject.Inject;

import com.lewish.asciigram.client.AsciiKeyCodes;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Cell;
import com.lewish.asciigram.client.Drag;
import com.lewish.asciigram.client.State;
import com.lewish.asciigram.client.State.CellState;

public class SelectTool extends Tool {

	public static enum SelectState {
		Dragging,
		Nothing,
		Selected;
	}

	private final Canvas canvas;
	private SelectState state = SelectState.Nothing;
	private State clipboard;
	private Drag currentBox;

	@Inject
	public SelectTool(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseOver(Cell cell) {
		if(state == SelectState.Dragging) {
			currentBox.setFinish(cell);
			draw();
		}
	}

	@Override
	public void mouseDown(Cell cell) {
		if(state != SelectState.Dragging) {
			state = SelectState.Dragging;
			currentBox = new Drag();
			currentBox.setStart(cell);
			currentBox.setFinish(cell);
			draw();
		}
	}

	@Override
	public void mouseUp(Cell cell) {
		if(state == SelectState.Dragging) {
			state = SelectState.Selected;
			currentBox.setFinish(cell);
		}
	}

	@Override
	public void cleanup() {
		state = SelectState.Nothing;
		if(currentBox != null) {
			canvas.clearDraw();
			currentBox = null;
		}
	}

	private void copy() {
		if (currentBox != null) {
			clipboard = new State();
			for (int x = currentBox.topLeftX(); x <= currentBox.bottomRightX(); x++) {
				for (int y = currentBox.topLeftY(); y <= currentBox.bottomRightY(); y++) {
					clipboard.add(new CellState(x, y, canvas.getValue(x, y)));
				}
			}
		}
	}

	private void paste() {
		if(currentBox != null && state == SelectState.Selected) {
			canvas.loadState(clipboard);
			canvas.commitDraw();
		}
	}

	@Override
	public String getLabel() {
		return "+box";
	}

	public void draw() {
		for (int x = currentBox.topLeftX(); x <= currentBox.bottomRightX(); x++) {
			for (int y = currentBox.topLeftY(); y <= currentBox.bottomRightY(); y++) {
				canvas.highlight(x, y, true);
			}
		}
		canvas.refreshDraw();
	}

	@Override
	public String getDescription() {
		return "Select, copy (ctrl+c), and paste (ctrl+v). Note: this is only internal to the app!";
	}

	@Override
	public String getImageUrl() {
		return "images/selecttool.png";
	}

	@Override
	public void specialKeyPress(int keyCode) {
		switch(keyCode) {
		case AsciiKeyCodes.COPY:
			copy();
			break;
		case AsciiKeyCodes.PASTE:
			paste();
			break;
		}
	}
}
