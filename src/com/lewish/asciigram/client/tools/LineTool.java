package com.lewish.asciigram.client.tools;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.KeyCodes;
import com.lewish.asciigram.client.Canvas;
import com.lewish.asciigram.client.Drag;

public class LineTool extends DragTool implements Tool {

	private boolean isArrowed;
	private boolean isClockwise;

	@Inject
	public LineTool(Canvas canvas, boolean isArrowed) {
		super(canvas);
		this.isArrowed = isArrowed;
	}

	@Override
	public void keyDown(int keyCode) {
		if (keyCode == KeyCodes.KEY_SHIFT) {
			isClockwise = !isClockwise;
			draw();
		}
	}

	@Override
	public String getLabel() {
		return isArrowed ? "Arrow" : "Line";
	}

	@Override
	public void draw(Drag box, Canvas canvas) {
		int x1 = box.getStart().getX();
		int y1 = box.getStart().getY();
		int x2 = box.getFinish().getX();
		int y2 = box.getFinish().getY();

		int hX1 = x1 < x2 ? x1 : x2;
		int hX2 = x2 < x1 ? x1 : x2;
		int hY = isClockwise ? y1 : y2;

		int vY1 = y1 < y2 ? y1 : y2;
		int vY2 = y2 < y1 ? y1 : y2;
		int vX = isClockwise ? x2 : x1;

		while (hX1++ < hX2) {
			canvas.getCell(hX1, hY).setDrawValue("-");
		}
		while (vY1++ < vY2) {
			canvas.getCell(vX, vY1).setDrawValue("|");
		}

		canvas.getCell(x1, y1).setDrawValue("+");
		canvas.getCell(vX, hY).setDrawValue("+");
		String endChar = "+";
		if (isArrowed) {
			endChar = vX < x2 ? ">" : vX > x2 ? "<" : y1 < y2 ? "v"
					: y1 > y2 ? "^" : x1 < x2 ? ">" : x1 > x2 ? "<" : "+";
		}
		canvas.getCell(x2, y2).setDrawValue(endChar);
		canvas.refreshDraw();
	}

	@Override
	public String getDescription() {
		return "Click and drag to draw " + (isArrowed ? "an arrow" : "a line") + ", press shift to toggle position";
	}

	@Override
	public String getImageUrl() {
		return "images/" + (isArrowed ? "arrow" : "line") + "tool.png";
	}
}
