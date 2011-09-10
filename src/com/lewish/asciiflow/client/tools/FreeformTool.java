package com.lewish.asciiflow.client.tools;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.Tool;
import com.lewish.asciiflow.client.common.Coordinate;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

public class FreeformTool extends Tool {

	private Coordinate coordinate;
	private String currentString;

	@Inject
	public FreeformTool(Canvas canvas, HistoryManager historyManager,
			AsciiflowClientBundle clientBundle) {
		super(canvas, historyManager, clientBundle);
		currentString = "x";
	}

	@Override
	public String getLabel() {
		return "";
	}

	@Override
	public String getDescription() {
		return "Press key to set draw character, then click and drag to freeform draw";
	}

	@Override
	public void mouseDown(int x, int y) {
		coordinate = new Coordinate(x, y);
		mouseOver(x, y);
	}

	@Override
	public void mouseOver(int x, int y) {
		if (coordinate != null) {
			coordinate.x = x;
			coordinate.y = y;
			canvas.draw(coordinate.x, coordinate.y, currentString);
			canvas.refreshDraw(true);
		}
	}

	@Override
	public void mouseUp(int x, int y) {
		coordinate = null;
		refreshDraw();
		commitDraw();
	}

	@Override
	public void keyPress(KeyPressEvent event) {
		currentString = new String(Character.toChars(event.getUnicodeCharCode()));
	}

	@Override
	public ImageResource getImageResource() {
		return clientBundle.freeformToolImage();
	}
}
