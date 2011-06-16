//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client.tools;

import javax.inject.Inject;

import com.lewish.asciigram.client.AsciiKeyCodes;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Cell;
import com.lewish.asciigram.client.Drag;
import com.lewish.asciigram.client.HistoryManager;
import com.lewish.asciigram.client.State;
import com.lewish.asciigram.client.Tool;
import com.lewish.asciigram.client.State.CellState;

public class SelectTool extends Tool {

	public static enum SelectState {
		Dragging, Nothing, Selected;
	}

	private SelectState state = SelectState.Nothing;
	private State clipboard;
	private Drag currentBox;

	@Inject
	public SelectTool(Canvas canvas, HistoryManager historyManager) {
		super(canvas, historyManager);
	}

	@Override
	public void mouseOver(Cell cell) {
		if (state == SelectState.Dragging) {
			currentBox.setFinish(cell);
			draw();
		}
	}

	@Override
	public void mouseDown(Cell cell) {
		if (state != SelectState.Dragging) {
			state = SelectState.Dragging;
			currentBox = new Drag();
			currentBox.setStart(cell);
			currentBox.setFinish(cell);
			draw();
		}
	}

	@Override
	public void mouseUp(Cell cell) {
		if (state == SelectState.Dragging) {
			state = SelectState.Selected;
			currentBox.setFinish(cell);
		}
	}

	@Override
	public void cleanup() {
		state = SelectState.Nothing;
		if (currentBox != null) {
			canvas.clearDraw();
			currentBox = null;
		}
	}

	private void copy(boolean cut) {
		if (currentBox == null)
			return;
		clipboard = new State();
		for (int x = currentBox.topLeftX(); x <= currentBox.bottomRightX(); x++) {
			for (int y = currentBox.topLeftY(); y <= currentBox.bottomRightY(); y++) {
				int dx = x - currentBox.topLeftX();
				int dy = y - currentBox.topLeftY();
				String val = canvas.getValue(x, y);
				// Move to origin
				if (val != null) {
					clipboard.add(new CellState(dx, dy, val));
					if (cut) {
						canvas.draw(x, y, " ");
					}
				}
			}
		}
		if (cut) {
			refreshDraw();
			commitDraw();
		}
	}

	private void paste() {
		State pasteState = new State();
		if (currentBox != null && state == SelectState.Selected) {
			for (CellState cs : clipboard.getStates()) {
				// Move to select position
				pasteState.add(new CellState(cs.x + currentBox.topLeftX(), cs.y
						+ currentBox.topLeftY(), cs.value));
			}
			canvas.drawState(pasteState);
			refreshDraw();
			commitDraw();
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
		refreshDraw();
	}

	@Override
	public String getDescription() {
		return "Select, copy (ctrl+c), cut (ctrl+x) and paste (ctrl+v). This is only internal to the app!";
	}

	@Override
	public String getImageUrl() {
		return "images/selecttool.png";
	}

	@Override
	public void specialKeyPress(int keyCode) {
		switch (keyCode) {
		case AsciiKeyCodes.COPY:
			copy(false);
			break;
		case AsciiKeyCodes.PASTE:
			paste();
			break;
		case AsciiKeyCodes.CUT:
			copy(true);
			break;
		}
	}
}
