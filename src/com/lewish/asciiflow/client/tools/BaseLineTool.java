//Copyright Lewis Hemens 2011
package com.lewish.asciiflow.client.tools;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.resources.client.ImageResource;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.Drag;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.resources.AsciiflowClientBundle;

public class BaseLineTool extends DragTool {

	protected boolean isClockwise;

	public BaseLineTool(Canvas canvas, HistoryManager historyManager,
			AsciiflowClientBundle clientBundle) {
		super(canvas, historyManager, clientBundle);
	}

	@Override
	public void specialKeyPress(int keyCode) {
		if (keyCode == KeyCodes.KEY_SHIFT) {
			isClockwise = !isClockwise;
			if (currentBox != null) {
				draw(currentBox);
				refreshDraw();
			}
		}
	}

	@Override
	public String getLabel() {
		return "Line";
	}

	public static void draw(Drag box, Canvas canvas, boolean isClockwise, boolean isArrowed, String hLineString, String vLineString, String cornerLineString) {
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
			canvas.draw(hX1, hY, hLineString);
		}
		while (vY1++ < vY2) {
			canvas.draw(vX, vY1, vLineString);
		}

		canvas.draw(x1, y1, cornerLineString);
		canvas.draw(vX, hY, cornerLineString);
		String endChar = cornerLineString;
		if (isArrowed) {
			endChar = vX < x2 ? ">" : vX > x2 ? "<" : y1 < y2 ? "v" : y1 > y2 ? "^" : x1 < x2 ? ">"
					: x1 > x2 ? "<" : cornerLineString;
		}
		canvas.draw(x2, y2, endChar);
	}

	@Override
	public String getDescription() {
		return "Click and drag to draw a line"
				+ ", press shift to toggle position";
	}

	@Override
	public ImageResource getImageResource() {
		return clientBundle.lineToolImage();
	}

	@Override
	protected void draw(Drag box) {
		draw(box, canvas, isClockwise, false, "-", "|", "+");
	}
}
