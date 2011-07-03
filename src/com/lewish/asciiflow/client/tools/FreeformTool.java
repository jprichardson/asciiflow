package com.lewish.asciiflow.client.tools;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.Cell;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.Tool;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

public class FreeformTool extends Tool {

	private Cell currentCell;
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
	public void mouseDown(Cell cell) {
		currentCell = cell;
		mouseOver(cell);
	}

	@Override
	public void mouseOver(Cell cell) {
		if (currentCell != null) {
			currentCell = cell;
			canvas.draw(cell, currentString);
			cell.pushValue(currentString);
			cell.pushHighlight();
		}
	}

	@Override
	public void mouseUp(Cell cell) {
		currentCell = null;
		refreshDraw();
		commitDraw();
	}

	@Override
	public void keyPress(KeyPressEvent event) {
		//TODO: Unicode
		currentString = String.valueOf(event.getCharCode());
	}

	@Override
	public ImageResource getImageResource() {
		return clientBundle.freeformToolImage();
	}
}
