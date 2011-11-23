package com.lewish.asciiflow.client.tools;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.Tool;
import com.lewish.asciiflow.client.common.Coordinate;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

public class TextTool extends Tool {

	private Coordinate coordinate;

	@Inject
	public TextTool(Canvas canvas, HistoryManager historyManager, AsciiflowClientBundle clientBundle) {
		super(canvas, historyManager, clientBundle);
	}

	@Override
	public void mouseDown(int x, int y) {
		if (coordinate != null) {
			commitDraw();
			canvas.highlight(coordinate.x, coordinate.y, false);
			canvas.refreshDraw();
		}
		coordinate = new Coordinate(x, y);
		if (coordinate != null) {
			canvas.highlight(coordinate.x, coordinate.y, true);
			canvas.refreshDraw();
		}
	}

	@Override
	public void cleanup() {
		canvas.refreshDraw(false);
		commitDraw();
		coordinate = null;
	}

	@Override
	public void keyPress(KeyPressEvent event) {
		if (event.getUnicodeCharCode() < 32)
			return;
		char[] chars = Character.toChars(event.getUnicodeCharCode());
		String s = new String(chars);
		if (s.length() != 1)
			return;
		canvas.draw(coordinate.x, coordinate.y, s);
		canvas.highlight(coordinate.x, coordinate.y, true);
		canvas.refreshDraw(true);
		coordinate.x++;
	}

	
	@Override
	public void specialKeyPress(int keyCode) {
		if (coordinate == null)
			return;
		switch (keyCode) {
		case KeyCodes.KEY_DOWN:
			coordinate.y++;
			break;
		case KeyCodes.KEY_UP:
			coordinate.y--;
			break;
		case KeyCodes.KEY_LEFT:
			coordinate.x--;
			break;
		case KeyCodes.KEY_RIGHT:
			coordinate.x++;
			break;
		case KeyCodes.KEY_DELETE:
			canvas.draw(coordinate.x, coordinate.y, null);
			canvas.highlight(coordinate.x, coordinate.y, false);
			canvas.refreshDraw(true);
			coordinate.x++;
			break;
		case KeyCodes.KEY_BACKSPACE:
			coordinate.x--;
			canvas.draw(coordinate.x, coordinate.y, null);
			canvas.highlight(coordinate.x, coordinate.y, false);
			canvas.refreshDraw(true);
			break;
		case KeyCodes.KEY_ENTER:
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
