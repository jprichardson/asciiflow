//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client.tools;

import java.io.UnsupportedEncodingException;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.Cell;
import com.lewish.asciiflow.client.CssStyles;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.Tool;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

//TODO: full of hacks! change to use State class.
public class TextTool extends Tool {

	private Cell currentCell;

	@Inject
	public TextTool(Canvas canvas, HistoryManager historyManager, AsciiflowClientBundle clientBundle) {
		super(canvas, historyManager, clientBundle);
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
			selectCell(canvas.getCell(currentCell.x + dx, currentCell.y + dy));
		}
	}

	@Override
	public void cleanup() {
		canvas.refreshDraw();
		canvas.commitDraw();
		selectCell(null);
	}

	@Override
	public void keyPress(KeyPressEvent event) {
		//TODO: Unicode
		int value = event.getUnicodeCharCode();
		byte[] str =  new byte[] {
                (byte)(value),
                (byte)(value >>> 8),
                (byte)(value >>> 16),
                (byte)(value >>> 24)};
		try {
			String s = new String(str, "UTF16");
			canvas.draw(currentCell, s);
			currentCell.pushValue(s);
			currentCell.pushHighlight();
			moveSelect(1, 0);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			//canvas.refreshDraw();
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
			canvas.draw(currentCell, null);
			canvas.highlight(currentCell, false);
			currentCell.pushHighlight();
			currentCell.pushValue(null);
			moveSelect(1, 0);
			break;
		case KeyCodes.KEY_BACKSPACE:
			moveSelect(-1, 0);
			canvas.draw(currentCell, null);
			canvas.highlight(currentCell, false);
			currentCell.pushHighlight();
			currentCell.pushValue(currentCell.commitValue);
			break;
		case KeyCodes.KEY_ENTER:
			refreshDraw();
			commitDraw();
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
	public ImageResource getImageResource() {
		return clientBundle.textToolImage();
	}
}
