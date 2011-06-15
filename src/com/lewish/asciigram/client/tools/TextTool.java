//Copyright Lewis Hemens 2011
package com.lewish.asciigram.client.tools;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.inject.Inject;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Cell;
import com.lewish.asciigram.client.CssStyles;

public class TextTool extends Tool {

	private final Canvas canvas;
	private Cell currentCell;

	@Inject
	public TextTool(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseDown(Cell cell) {
		selectCell(cell);
	}

	private void selectCell(Cell cell) {
		if (currentCell != null) {
			currentCell.removeStyleName(CssStyles.Selected);
		}
		currentCell = cell;
		if (currentCell != null) {
			currentCell.addStyleName(CssStyles.Selected);
		}
	}

	private void moveSelect(int dx, int dy) {
		if (currentCell != null) {
			selectCell(canvas.getCell(currentCell.getX() + dx, currentCell.getY() + dy));
		}
	}

	@Override
	public void cleanup() {
		canvas.commitDraw();
		selectCell(null);
	}

	@Override
	public void keyPress(KeyPressEvent event) {
			if (event.getCharCode() > 31 && event.getCharCode() < 127) {
				currentCell.setDrawValue(String.valueOf(event.getCharCode()));
				currentCell.refreshDraw();
				moveSelect(1, 0);
			}
		}
	
	@Override
	public void specialKeyPress(int keyCode) {
		if (currentCell == null)
			return;
		switch (keyCode) {
		case KeyCodes.KEY_DOWN:
			moveSelect(0, 1);
			break;
		case KeyCodes.KEY_UP:
			moveSelect(0, -1);
			break;
		case KeyCodes.KEY_LEFT:
			moveSelect(-1, 0);
			break;
		case KeyCodes.KEY_RIGHT:
			moveSelect(1, 0);
			break;
		case KeyCodes.KEY_DELETE:
			currentCell.setDrawValue(null, false);
			currentCell.refreshDraw();
			moveSelect(1, 0);
			break;
		case KeyCodes.KEY_BACKSPACE:
			moveSelect(-1, 0);
			currentCell.setDrawValue(null, false);
			currentCell.refreshDraw();
			break;
		case KeyCodes.KEY_ENTER:
			canvas.commitDraw();
			break;
		}
	}

	@Override
	public String getLabel() {
		return "Text";
	}

	@Override
	public String getDescription() {
		return "Click and type to add text, press return to commit changes";
	}

	@Override
	public String getImageUrl() {
		return "images/texttool.png";
	}
}
