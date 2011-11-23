package com.lewish.asciiflow.client.tools;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.resources.client.ImageResource;
import com.lewish.asciiflow.client.Canvas;
import com.lewish.asciiflow.client.HistoryManager;
import com.lewish.asciiflow.client.common.Box;
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

	public static void draw(Box box, Canvas canvas, boolean isClockwise, boolean isArrowed,
			String hLineString, String vLineString, String cornerLineString) {

		int hX1 = box.topLeftX();
		int hX2 = box.bottomRightX();
		int hY = isClockwise ? box.startY : box.finishY;

		int vY1 = box.topLeftY();
		int vY2 = box.bottomRightY();
		int vX = isClockwise ? box.finishX : box.startX;

		while (hX1++ < hX2) {
			canvas.draw(hX1, hY, hLineString);
		}
		while (vY1++ < vY2) {
			canvas.draw(vX, vY1, vLineString);
		}

		canvas.draw(box.startX, box.startY, cornerLineString);
		canvas.draw(vX, hY, cornerLineString);
		String endChar = cornerLineString;
		if (isArrowed) {
			endChar = vX < box.finishX ? ">" : vX > box.finishX ? "<"
					: box.startY < box.finishY ? "v" : box.startY > box.finishY ? "^"
							: box.startX < box.finishX ? ">" : box.startX > box.finishX ? "<"
									: cornerLineString;
		}
		canvas.draw(box.finishX, box.finishY, endChar);
	}

	@Override
	public String getDescription() {
		return "Click and drag to draw a line" + ", press shift to toggle position";
	}

	@Override
	public ImageResource getImageResource() {
		return clientBundle.lineToolImage();
	}

	@Override
	protected void draw(Box box) {
		draw(box, canvas, isClockwise, false, "-", "|", "+");
	}
}
