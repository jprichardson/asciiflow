package com.lewish.asciigram.client;

public class EraseTool extends DragTool implements Tool {

	public EraseTool(Canvas canvas) {
		super(canvas);
	}

	@Override
	public String getLabel() {
		return "Erase";
	}

	@Override
	public void draw(Drag box, Canvas canvas) {
		int x1 = box.getStart().getX();
		int y1 = box.getStart().getY();
		int x2 = box.getFinish().getX();
		int y2 = box.getFinish().getY();
		// change to top left and bottom right
		if (x1 > x2) {
			x1 = x1 ^ x2;
			x2 = x1 ^ x2;
			x1 = x1 ^ x2;
		}
		if (y1 > y2) {
			y1 = y1 ^ y2;
			y2 = y1 ^ y2;
			y1 = y1 ^ y2;
		}
		for (; x1 <= x2; x1++) {
			for (int y = y1; y <= y2; y++) {
				canvas.getCell(x1, y).setDrawValue(" ");
			}
		}
		canvas.refreshDraw();
	}

	@Override
	public String getDescription() {
		return "Click and drag to erase cells";
	}

	@Override
	public String getImageUrl() {
		return "images/erasetool.png";
	}
}
