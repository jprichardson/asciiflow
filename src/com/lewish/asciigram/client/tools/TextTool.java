package com.lewish.asciigram.client.tools;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.inject.Inject;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Cell;
import com.lewish.asciigram.client.CssStyles;

public class TextTool implements Tool {

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

	public void selectCell(Cell cell) {
		if (currentCell != null) {
			currentCell.removeStyleName(CssStyles.Selected);
		}
		currentCell = cell;
		if (currentCell != null) {
			currentCell.addStyleName(CssStyles.Selected);
		}
	}

	@Override
	public void cleanup() {
		canvas.clearDraw();
		selectCell(null);
	}

	@Override
	public void keyDown(KeyDownEvent event) {
		int keyCode = event.getNativeKeyCode();
		if (currentCell == null)
			return;
		if (keyCode == KeyCodes.KEY_DOWN) {
			selectCell(canvas.getCell(currentCell.getX(),
					currentCell.getY() + 1));
		} else if (keyCode == KeyCodes.KEY_UP) {
			selectCell(canvas.getCell(currentCell.getX(),
					currentCell.getY() - 1));
		} else if (keyCode == KeyCodes.KEY_LEFT) {
			selectCell(canvas.getCell(currentCell.getX() - 1,
					currentCell.getY()));
		} else if (keyCode == KeyCodes.KEY_RIGHT) {
			selectCell(canvas.getCell(currentCell.getX() + 1,
					currentCell.getY()));
		} else if (keyCode == KeyCodes.KEY_DELETE) {
			currentCell.setDrawValue(null, false);
			currentCell.refreshDraw();
			selectCell(canvas.getCell(currentCell.getX() + 1,
					currentCell.getY()));
		} else if (keyCode == KeyCodes.KEY_BACKSPACE) {
			selectCell(canvas.getCell(currentCell.getX() - 1,
					currentCell.getY()));
			currentCell.setDrawValue(null, false);
			currentCell.refreshDraw();
		} else if (keyCode == KeyCodes.KEY_ENTER) {
			canvas.commitDraw();
		}
	}

	@Override
	public void keyPress(char character) {
		if(character > 31 && character < 127) {
			currentCell.setDrawValue(String.valueOf(character));
			currentCell.refreshDraw();
			selectCell(canvas.getCell(currentCell.getX() + 1,
					currentCell.getY()));
		}
	}

	@Override
	public void mouseUp(Cell cell) {
	}

	@Override
	public void mouseOver(Cell cell) {
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
