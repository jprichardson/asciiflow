//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client.tools;

import com.google.gwt.event.dom.client.KeyCodes;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.Drag;
import com.lewish.asciiflow.client.HistoryManager;

public class BaseLineTool extends DragTool {

	private boolean isArrowed;
	private boolean isClockwise;

	public BaseLineTool(Canvas canvas, HistoryManager historyManager, boolean isArrowed) {
		super(canvas, historyManager);
		this.isArrowed = isArrowed;
	}

	@Override
	public void specialKeyPress(int keyCode) {
		if (keyCode == KeyCodes.KEY_SHIFT) {
			isClockwise = !isClockwise;
			if (currentBox != null) {
				draw(currentBox, canvas);
				refreshDraw();
			}
		}
	}

	@Override
	public String getLabel() {
		return isArrowed ? "Arrow" : "Line";
	}

	@Override
	public void draw(Drag box, Canvas canvas) {
		int x1 = box.getStart().x;
		int y1 = box.getStart().y;
		int x2 = box.getFinish().x;
		int y2 = box.getFinish().y;

		int hX1 = box.topLeftX();
		int hX2 = box.bottomRightX();
		int hY = isClockwise ? y1 : y2;

		int vY1 = box.topLeftY();
		int vY2 = box.bottomRightY();
		int vX = isClockwise ? x2 : x1;

		while (hX1++ < hX2) {
			canvas.draw(hX1, hY, "-");
		}
		while (vY1++ < vY2) {
			canvas.draw(vX, vY1, "|");
		}

		canvas.draw(x1, y1, "+");
		canvas.draw(vX, hY, "+");
		String endChar = "+";
		if (isArrowed) {
			endChar = vX < x2 ? ">" : vX > x2 ? "<" : y1 < y2 ? "v" : y1 > y2 ? "^" : x1 < x2 ? ">"
					: x1 > x2 ? "<" : "+";
		}
		canvas.draw(x2, y2, endChar);
	}

	@Override
	public String getDescription() {
		return "Click and drag to draw " + (isArrowed ? "an arrow" : "a line")
				+ ", press shift to toggle position";
	}

	@Override
	public String getImageResource() {
		return "images/" + (isArrowed ? "arrow" : "line") + "tool.png";
	}
}
